/*
 * Copyright 2013-2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Further developed & adapted by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 *
 * Licensed under the Amazon Software License (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.alexgrace.finalyearproject.kinesisclient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alexgrace.finalyearproject.kinesisclient.FoursquareEntities.*;
import com.alexgrace.finalyearproject.kinesisclient.OtherEntities.FoursquareClient;
import com.alexgrace.finalyearproject.kinesisclient.OtherEntities.LocationFilter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang3.StringUtils;

import com.amazonaws.services.kinesis.clientlibrary.exceptions.InvalidStateException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ShutdownException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ThrottlingException;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
*
*/
public class KinesisRecordProcessor implements IRecordProcessor {

   private static final Log LOG = LogFactory.getLog(KinesisRecordProcessor.class);
   private String kinesisShardId;

   // Backoff and retry settings
   private static final long BACKOFF_TIME_IN_MILLIS = 3000L;
   private static final int NUM_RETRIES = 10;

   // Checkpoint about once a minute
   private static final long CHECKPOINT_INTERVAL_MILLIS = 60000L;
   private long nextCheckpointTimeInMillis;

   // Jackson
   private ObjectMapper mapper;

   // Jersey
   private Client client;
   private WebResource resource;
   private ClientResponse response;

   // Regex
   private Pattern pattern;
   private Matcher matcher;

   // Database
   private Connection connect;
   private Statement statement;
   private ResultSet resultSet;

   private String dbHost;
   private int dbPort;
   private String dbUser;
   private String dbPass;

   //Foursquare
   private List<FoursquareClient> foursquareClient;
   private int clientIndex;
   private int fsqVersion;

   //Regex
   private String regex = "[^\\/]+$";
   private String regexTester = "^[a-zA-Z0-9]{2,}$";

   //Coordinates
   private List<LocationFilter> locationFilter;

   private final CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

   /**
    * Constructor.
    */
   public KinesisRecordProcessor(String dbHost, int dbPort, String dbUser, String dbPass, List<FoursquareClient> foursquareClient, int fsqVersion, List<LocationFilter> locationFilter) {
       super();

       this.dbHost = dbHost;
       this.dbPort = dbPort;
       this.dbUser = dbUser;
       this.dbPass = dbPass;
       this.foursquareClient = foursquareClient;
       this.fsqVersion = fsqVersion;
       this.locationFilter = locationFilter;

   }

   /**
    * {@inheritDoc}
    */
   public void initialize(String shardId) {
       LOG.info("Initializing record processor for shard: " + shardId);
       this.kinesisShardId = shardId;

       mapper = new ObjectMapper();
       client = Client.create();

       clientIndex = 0;

       LOG.info("Connecting to Database");
       try {
           Class.forName("com.mysql.jdbc.Driver");
           connect = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort , dbUser, dbPass);
           statement = connect.createStatement();
           System.out.println("Connected to database");
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

   /**
    * {@inheritDoc}
    */
   public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
       LOG.info("Processing " + records.size() + " records from " + kinesisShardId);

       // Process records and perform all exception handling.
       processRecordsWithRetries(records);

       // Checkpoint once every checkpoint interval.
       if (System.currentTimeMillis() > nextCheckpointTimeInMillis) {
           checkpoint(checkpointer);
           nextCheckpointTimeInMillis = System.currentTimeMillis() + CHECKPOINT_INTERVAL_MILLIS;
       }

   }

   /** Process records performing retries as needed. Skip "poison pill" records.
    * @param records
    */
   private void processRecordsWithRetries(List<Record> records) {
       for (Record record : records) {
           boolean processedSuccessfully = false;
           String data = null;
           try {
               data = decoder.decode(record.getData()).toString();
           } catch (Throwable t) {
               LOG.warn("Cannot read data " + record, t);
           }


           for (int i = 0; i < NUM_RETRIES; i++) {
               try {
                   String tempId, foursquaredata, url = null, shortId = null;
                   double venueLat, venueLng;
                   JsonNode node, entities, urls;
                   FoursquareCheckinEntity fsqdata = null;
                   List<Boolean> locations = new ArrayList<Boolean>();

                   if (data == null) {
                       break;
                   }

                   // Find URL Array in Twitter JSON
                   try {
                       node = mapper.readTree(data);
                       entities = node.findValue("entities");
                       urls = entities.findValue("urls");
                       if (urls.isArray()) {
                           for (JsonNode objNode : urls) {
                               url = objNode.get("expanded_url").asText();
                           }
                       }
                   } catch (Exception e) {
                       LOG.error("Error reading node tree");
                       break;
                   }

                   // Get checkinID
            	   if (url == null) {
                       LOG.error("URL is null");
                       break;
                   }

                   pattern = Pattern.compile(regex);
                   matcher = pattern.matcher(url);
                   if (matcher.find()) {
                       tempId = matcher.group(0);
                       if (tempId.matches(regexTester)){
                           shortId = tempId;
                       }
                   } else {
                       LOG.error("Regex error: " + url);
                       break;
                   }

                   if (shortId == null) {
                       LOG.error("ShortID is null, url is: " + url);
                       break;
                   }

                   resource = client.resource("https://api.foursquare.com/v2/checkins/resolve?client_id=" + foursquareClient.get(clientIndex).getClient_id() + "&client_secret=" + foursquareClient.get(clientIndex).getClient_secret() + "&v=" + fsqVersion + "&shortId=" + shortId);
                   response = resource.accept("application/json").get(ClientResponse.class);

                   while (response.getStatus() == 403) {
                       LOG.info("Response 403: Switching to next Foursquare Client Details");
                       if ((foursquareClient.size() - 1) == clientIndex) {
                           clientIndex = 0;
                       } else {
                           clientIndex = clientIndex + 1;
                       }
                       resource = client.resource("https://api.foursquare.com/v2/checkins/resolve?client_id=" + foursquareClient.get(clientIndex).getClient_id() + "&client_secret=" + foursquareClient.get(clientIndex).getClient_secret() + "&v=" + fsqVersion + "&shortId=" + shortId);
                       response = resource.accept("application/json").get(ClientResponse.class);
                   }

                   if (response.getStatus() != 200) {
                       LOG.error("ShortID: " + shortId + ", Failed with HTTP Error code: " + response.getStatus());
                       break;
                   }

                   foursquaredata = response.getEntity(String.class);
                   try {
                       fsqdata = mapper.readValue(foursquaredata, FoursquareCheckinEntity.class);
                   } catch (JsonGenerationException e) {
                       e.printStackTrace();
                       LOG.error("Error: Foursquare Entity Mapper Catch 1");
                   } catch (JsonMappingException e) {
                       e.printStackTrace();
                       LOG.error("Error: Foursquare Entity Mapper Catch 2");
                   } catch (IOException e) {
                       e.printStackTrace();
                       LOG.error("Error: Foursquare Entity Mapper Catch 3");
                   }

                   venueLat = fsqdata.getResponse().getCheckin().getVenue().getLocation().getLat();
                   venueLng = fsqdata.getResponse().getCheckin().getVenue().getLocation().getLng();

                   for (LocationFilter item : locationFilter) {
                       locations.add(insideRadius(venueLat, venueLng, item.getLat(), item.getLng(), item.getRadius()));
                   }

                   if (locations.contains(true)) {
                       try {
                           //---------------------------------------------------
                           // Users
                           //---------------------------------------------------
                           resultSet = statement.executeQuery("select id from finalyearproject.users where id = '" + fsqdata.getResponse().getCheckin().getUser().getId() + "';");
                           if (!resultSet.next()) {
                               statement.executeUpdate("INSERT INTO finalyearproject.users " +
                                       "VALUES ('" + fsqdata.getResponse().getCheckin().getUser().getId() + "', " +               // id
                                       "null, '" +                                                                                // twitterhandle
                                       characterChecker(fsqdata.getResponse().getCheckin().getUser().getFirstName()) + "', '" +   // firstname
                                       characterChecker(fsqdata.getResponse().getCheckin().getUser().getLastName()) + "', '" +    // lastname
                                       fsqdata.getResponse().getCheckin().getUser().getGender() + "', '" +                        // gender
                                       fsqdata.getResponse().getCheckin().getUser().getPhoto().getPrefix() + "', '" +             // photo_prefix
                                       fsqdata.getResponse().getCheckin().getUser().getPhoto().getSuffix() + "');");              // photo_suffix
                           }

                           //---------------------------------------------------
                           // Venues
                           //---------------------------------------------------
                           resultSet = statement.executeQuery("select id from finalyearproject.venues where id = '" + fsqdata.getResponse().getCheckin().getVenue().getId() + "';");
                           if (!resultSet.next()) {
                               String joinedAddress = StringUtils.join(fsqdata.getResponse().getCheckin().getVenue().getLocation().getFormattedAddress(), "\n");
                               statement.executeUpdate("INSERT INTO finalyearproject.venues " +
                                       "VALUES ('" + fsqdata.getResponse().getCheckin().getVenue().getId() + "', '" +                                     // id
                                       characterChecker(fsqdata.getResponse().getCheckin().getVenue().getName()) + "', '" +                               // name
                                       fsqdata.getResponse().getCheckin().getVenue().getLocation().getLat() + "', '" +                                    // lat
                                       fsqdata.getResponse().getCheckin().getVenue().getLocation().getLng() + "', '" +                                    // lng
                                       fsqdata.getResponse().getCheckin().getVenue().getContact().getPhone() + "', '" +                                   // contact_phone
                                       fsqdata.getResponse().getCheckin().getVenue().getContact().getFormattedPhone() + "', '" +                          // contact_formattedPhone
                                       fsqdata.getResponse().getCheckin().getVenue().getContact().getTwitter() + "', '" +                                 // contact_twitter
                                       fsqdata.getResponse().getCheckin().getVenue().getContact().getInstagram() + "', '" +                               // contact_instagram
                                       characterChecker(fsqdata.getResponse().getCheckin().getVenue().getContact().getFacebook()) + "', '" +              // contact_facebook
                                       characterChecker(fsqdata.getResponse().getCheckin().getVenue().getContact().getFacebookUsername()) + "', '" +      // contact_facebookusername
                                       characterChecker(fsqdata.getResponse().getCheckin().getVenue().getContact().getFacebookName()) + "', '" +          // contact_facebookname
                                       characterChecker(fsqdata.getResponse().getCheckin().getVenue().getLocation().getAddress()) + "', '" +              // location_address
                                       characterChecker(joinedAddress) + "', '" +                                                                         // location_formattedaddress
                                       characterChecker(fsqdata.getResponse().getCheckin().getVenue().getLocation().getCrossStreet()) + "', '" +          // location_crossstreet
                                       characterChecker(fsqdata.getResponse().getCheckin().getVenue().getLocation().getPostalCode()) + "', '" +           // location_postcode
                                       characterChecker(fsqdata.getResponse().getCheckin().getVenue().getLocation().getCc()) + "', '" +                   // location_cc
                                       characterChecker(fsqdata.getResponse().getCheckin().getVenue().getLocation().getNeighborhood()) + "', '" +         // location_neighbourhood
                                       characterChecker(fsqdata.getResponse().getCheckin().getVenue().getLocation().getCity()) + "', '" +                 // location_city
                                       characterChecker(fsqdata.getResponse().getCheckin().getVenue().getLocation().getState()) + "', '" +                // location_state
                                       characterChecker(fsqdata.getResponse().getCheckin().getVenue().getLocation().getCountry()) + "', " +               // location_country
                                       fsqdata.getResponse().getCheckin().getVenue().getVerified() + ", " +                                               // verified
                                       fsqdata.getResponse().getCheckin().getVenue().getStats().getCheckinsCount() + ", " +                               // stats_checkincount
                                       fsqdata.getResponse().getCheckin().getVenue().getStats().getUsersCount() + ", " +                                  // stats_usercount
                                       fsqdata.getResponse().getCheckin().getVenue().getStats().getTipCount() + ", '" +                                   // stats_tipcount
                                       fsqdata.getResponse().getCheckin().getVenue().getUrl() + "');");                                                   // url
                           }

                           //---------------------------------------------------
                           // Checkins
                           //---------------------------------------------------
                           resultSet = statement.executeQuery("select id from finalyearproject.checkins where id = '" + fsqdata.getResponse().getCheckin().getId() + "';");
                           if (!resultSet.next()) {
                               statement.executeUpdate("INSERT INTO finalyearproject.checkins " +
                                       "VALUES ('" + fsqdata.getResponse().getCheckin().getId() + "', '" +        // id
                                       shortId + "', '" +                                                         // shortid
                                       fsqdata.getResponse().getCheckin().getCreatedAt() + "', '" +               // createdat
                                       fsqdata.getResponse().getCheckin().getTimeZoneOffset() + "', '" +          // timezoneoffset
                                       characterChecker(fsqdata.getResponse().getCheckin().getShout()) + "', '" + // message
                                       fsqdata.getResponse().getCheckin().getUser().getId() + "', '" +            // userid
                                       fsqdata.getResponse().getCheckin().getVenue().getId() + "');");            // venueid
                           }

                           //---------------------------------------------------
                           // Venue Categories
                           //---------------------------------------------------
                           for (Iterator<Categories> a = fsqdata.getResponse().getCheckin().getVenue().getCategories().iterator(); a.hasNext(); ) {
                               Categories item = a.next();
                               resultSet = statement.executeQuery("select id from finalyearproject.categories where id = '" + item.getId() + "';");
                               if (!resultSet.next()) {
                                   statement.executeUpdate("INSERT INTO finalyearproject.categories " +
                                           "VALUES ('" + item.getId() + "', '" +       // id
                                           item.getName() + "', '" +                   // name
                                           item.getPluralName() + "', '" +             // pluralname
                                           item.getShortName() + "', '" +              // shortname
                                           item.getIcon().getPrefix() + "', '" +       // icon_prefix
                                           item.getIcon().getSuffix() + "');");        // icon_suffix
                               }
                               statement.executeUpdate("INSERT INTO finalyearproject.venuecategories " +
                                       "VALUES ('" + fsqdata.getResponse().getCheckin().getVenue().getId() + "', '" +     // venue
                                       item.getId() + "', " +                                                          // category
                                       item.getPrimary() + ");");                                                      // primary
                           }

                           //---------------------------------------------------
                           // Checkin With
                           //---------------------------------------------------
                           if (fsqdata.getResponse().getCheckin().getWith() != null) {
                               for (Iterator<User> b = fsqdata.getResponse().getCheckin().getWith().iterator(); b.hasNext(); ) {
                                   User item = b.next();
                                   resultSet = statement.executeQuery("select id from finalyearproject.users where id = '" + item.getId() + "';");
                                   if (!resultSet.next()) {
                                       statement.executeUpdate("INSERT INTO finalyearproject.users " +
                                               "VALUES ('" + item.getId() + "', " +       // id
                                               "null, '" +                                // twitterhandle
                                               characterChecker(item.getFirstName()) + "', '" +             // firstname
                                               characterChecker(item.getLastName()) + "', '" +              // lastname
                                               item.getGender() + "', '" +                // gender
                                               item.getPhoto().getPrefix() + "', '" +     // photo_prefix
                                               item.getPhoto().getSuffix() + "');");      // photo_suffix
                                   }
                                   statement.executeUpdate("INSERT INTO finalyearproject.with " +
                                           "VALUES ('" + fsqdata.getResponse().getCheckin().getId() + "', '" + // checkin
                                           item.getId() + "');");                                                       // user
                               }
                           }

                           //---------------------------------------------------
                           // Checkin Photos
                           //---------------------------------------------------
                           if (fsqdata.getResponse().getCheckin().getPhotos() != null) {
                               for (Iterator<Items> c = fsqdata.getResponse().getCheckin().getPhotos().getItems().iterator(); c.hasNext(); ) {
                                   Items item = c.next();
                                   resultSet = statement.executeQuery("select id from finalyearproject.photos where id = '" + item.getId() + "';");
                                   if (!resultSet.next()) {
                                       statement.executeUpdate("INSERT INTO finalyearproject.photos " +
                                               "VALUES ('" + item.getId() + "', '" +      // id
                                               item.getPrefix() + "', '" +                // prefix
                                               item.getSuffix() + "');");                 // suffix
                                   }
                                   statement.executeUpdate("INSERT INTO finalyearproject.checkinphotos " +
                                           "VALUES ('" + fsqdata.getResponse().getCheckin().getId() + "', '" +    // checkin
                                           item.getId() + "');");                                              // photo
                               }
                           }
                           LOG.info("Success: ShortID: " + shortId + " is in London or New York, added to database");

                       } catch (Exception e) {
                           e.printStackTrace();
                           LOG.error("Error: Entering Data Into Database");
                       }
                   } else {
                       LOG.info("Success: ShortID: " + shortId + " is not within location filters, not added to database");
                   }

                   /*
                    try {
                        String prettyJSon = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
                        //System.out.println(prettyJSon);
                    } catch (JsonGenerationException e) {
                        e.printStackTrace();
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    */
                   processedSuccessfully = true;
                   break;
               } catch (Throwable t) {
                   LOG.warn("Caught throwable while processing record " + record, t);
               }

               // backoff if we encounter an exception.
               try {
                   Thread.sleep(BACKOFF_TIME_IN_MILLIS);
               } catch (InterruptedException e) {
                   LOG.debug("Interrupted sleep", e);
               }
           }

           if (!processedSuccessfully) {
               LOG.error("Couldn't process record " + record + ". Skipping the record.");
           }
       }
   }

   /**
    * {@inheritDoc}
    */
   public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason reason) {
       LOG.info("Shutting down record processor for shard: " + kinesisShardId);
       //Disconnect from database
       try {
           if (resultSet != null) {
               resultSet.close();
           }

           if (statement != null) {
               statement.close();
           }

           if (connect != null) {
               connect.close();
               System.out.println("Disconnected from database");
           }
       } catch (Exception e) {

       }

       // Important to checkpoint after reaching end of shard, so we can start processing data from child shards.
       if (reason == ShutdownReason.TERMINATE) {
           checkpoint(checkpointer);
       }
   }



   /** Checkpoint with retries.
    * @param checkpointer
    */
   private void checkpoint(IRecordProcessorCheckpointer checkpointer) {
       LOG.info("Checkpointing shard " + kinesisShardId);
       for (int i = 0; i < NUM_RETRIES; i++) {
           try {
               checkpointer.checkpoint();
               break;
           } catch (ShutdownException se) {
               // Ignore checkpoint if the processor instance has been shutdown (fail over).
               LOG.info("Caught shutdown exception, skipping checkpoint.", se);
               break;
           } catch (ThrottlingException e) {
               // Backoff and re-attempt checkpoint upon transient failures
               if (i >= (NUM_RETRIES - 1)) {
                   LOG.error("Checkpoint failed after " + (i + 1) + "attempts.", e);
                   break;
               } else {
                   LOG.info("Transient issue when checkpointing - attempt " + (i + 1) + " of "
                           + NUM_RETRIES, e);
               }
           } catch (InvalidStateException e) {
               // This indicates an issue with the DynamoDB table (check for table, provisioned IOPS).
               LOG.error("Cannot save checkpoint to the DynamoDB table used by the Amazon Kinesis Client Library.", e);
               break;
           }
           try {
               Thread.sleep(BACKOFF_TIME_IN_MILLIS);
           } catch (InterruptedException e) {
               LOG.debug("Interrupted sleep", e);
           }
       }
   }

    private String characterChecker(String str) {
        if (str != null) {
            return str.replace("'","''");
        } else { return null;}
    }

    private boolean insideRadius(double lat1, double lng1, double lat2, double lng2, int radius) {
        double theta = lng1 - lng2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        if (dist < radius) {
            return true;
        }
        return false;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
