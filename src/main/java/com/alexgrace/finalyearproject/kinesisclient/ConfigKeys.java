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


/**
*  Keys for configuration overrides (via properties file).
*/
public class ConfigKeys {
   
   /**
    * Name of the application.
    */
   public static final String APPLICATION_NAME_KEY = "appName";

   /**
    * Name of the Kinesis stream.
    */
   public static final String STREAM_NAME_KEY = "kinesisStreamName";

   /**
    * Kinesis endpoint.
    */
   public static final String KINESIS_ENDPOINT_KEY = "kinesisEndpoint";
   
   /**
    * Initial position in the stream when an application starts up for the first time.
    * Value is one of LATEST (most recent data) or TRIM_HORIZON (oldest available data).
    */
   public static final String INITIAL_POSITION_IN_STREAM_KEY = "initialPositionInStream";
   
   
   /**
    * The url of the database
    */
   public static final String DB_HOST = "dbHost";
   
   /**
    * The port of the database
    */
   public static final String DB_PORT = "dbPort";

   /**
    * Database Username
    */
   public static final String DB_USERNAME = "dbUser";

   /**
    * Database Password
    */
   public static final String DB_PASSWORD = "dbPass";

   /**
    * Foursquare ClientId
    */
   public static final String FSQ_CLIENTID = "fsqClientId";

   /**
    * Foursquare ClientSecret
    */
   public static final String FSQ_CLIENTSECRET = "fsqClientSecret";

   /**
    * Foursquare Version
    */
   public static final String FSQ_VERSION = "fsqVersion";
   
   private ConfigKeys() {        
   }

}