/*
 * Developed by Alex Grace for research purposes only. (ag00248@surrey.ac.uk)
 */

package com.alexgrace.finalyearproject.kinesisclient.FoursquareEntities;

public class Response {

    public Checkin getCheckin() {
        return checkin;
    }

    public String getSignature() {
        return signature;
    }

    private Checkin checkin;
    private String signature;
}

/* Sample JSON:

https://api.foursquare.com/v2/checkins/resolve?client_id=ZZOCB5JDK4G2FC15YV5G5MRU0YWXP0P1PIXLPGRRNLCOVE3R&client_secret=JLRKOYQ4VULZZZJPOA2EIZBRCNMLUIPMQVZTEDWF50X2WERV&v=20170101&shortId=indyIeRvmgH

{
  "meta": {
    "code": 200,
    "requestId": "58adadbef594df58b64ab4e2"
  },
  "response": {
    "checkin": {
      "id": "589decc4ce66aa41e297c7bd",
      "createdAt": 1486744772,
      "type": "checkin",
      "entities": [],
      "shout": "Thanks as always 4 the #Audition ‪@Melsky_Casting. I hope I win! #Actor #ActorsLife",
      "timeZoneOffset": -300,
      "user": {
        "id": "1413832",
        "firstName": "Pat",
        "lastName": "Dwyer",
        "gender": "male",
        "photo": {
          "prefix": "https://igx.4sqi.net/img/user/",
          "suffix": "/BDYNEK3OFBPD0HF4.jpg"
        }
      },
      "venue": {
        "id": "4be4319acf200f477f95113c",
        "name": "Beth Melsky Casting",
        "contact": {
          "phone": "2125055000",
          "formattedPhone": "(212) 505-5000"
        },
        "location": {
          "address": "60 Madison Ave",
          "crossStreet": "E 26th St",
          "lat": 40.743356661494566,
          "lng": -73.98636891409481,
          "labeledLatLngs": [
            {
              "label": "display",
              "lat": 40.743356661494566,
              "lng": -73.98636891409481
            }
          ],
          "postalCode": "10010",
          "cc": "US",
          "city": "New York",
          "state": "NY",
          "country": "United States",
          "formattedAddress": [
            "60 Madison Ave (E 26th St)",
            "New York, NY 10010",
            "United States"
          ]
        },
        "categories": [
          {
            "id": "4bf58dd8d48988d124941735",
            "name": "Office",
            "pluralName": "Offices",
            "shortName": "Office",
            "icon": {
              "prefix": "https://ss3.4sqi.net/img/categories_v2/building/default_",
              "suffix": ".png"
            },
            "primary": true
          }
        ],
        "verified": false,
        "stats": {
          "checkinsCount": 669,
          "usersCount": 227,
          "tipCount": 7
        },
        "venueRatingBlacklisted": true,
        "beenHere": {
          "lastCheckinExpiredAt": 0
        }
      },
      "source": {
        "name": "Swarm for iOS",
        "url": "https://www.swarmapp.com"
      },
      "photos": {
        "count": 1,
        "items": [
          {
            "id": "589decc634935555b0482d21",
            "createdAt": 1486744774,
            "prefix": "https://igx.4sqi.net/img/general/",
            "suffix": "/1413832_je-6Q8rwL_cEhcblg96RikesEtSiNGlHKZ-0JHJ_3-o.jpg",
            "width": 1440,
            "height": 1920,
            "user": {
              "id": "1413832",
              "firstName": "Pat",
              "lastName": "Dwyer",
              "gender": "male",
              "photo": {
                "prefix": "https://igx.4sqi.net/img/user/",
                "suffix": "/BDYNEK3OFBPD0HF4.jpg"
              }
            },
            "visibility": "public"
          }
        ],
        "layout": {
          "name": "single"
        }
      },
      "likes": {
        "count": 0,
        "groups": []
      },
      "sticker": {
        "id": "53fe77e54b9048855b3648b6",
        "name": "Lappy Toppy",
        "image": {
          "prefix": "https://irs2.4sqi.net/img/sticker/",
          "sizes": [
            60,
            94,
            150,
            300
          ],
          "name": "/laptop_4d7599.png"
        },
        "stickerType": "unlockable",
        "group": {
          "name": "collectible",
          "index": 42
        },
        "pickerPosition": {
          "page": 1,
          "index": 18
        },
        "teaseText": "You didn’t get the memo?",
        "unlockText": "You're totally not reading BuzzFeed all day. Here’s a sticker for being so productive. Now back to that “Which Sticker Are You?” quiz.",
        "bonusText": "Use at Offices for a bonus.",
        "points": 2,
        "bonusStatus": "Use once per week. Recharges Sunday at midnight."
      },
      "isMayor": false,
      "score": {
        "total": 21,
        "scores": [
          {
            "icon": "https://ss1.4sqi.net/img/points/coin_icon_photo.png",
            "message": "Great photo!",
            "points": 15
          },
          {
            "icon": "https://ss1.4sqi.net/img/points/coin_icon_sharing.png",
            "message": "Sharing is caring!",
            "points": 6
          }
        ]
      },
      "stickerPowerup": {
        "bonusType": "multiplier",
        "value": 3,
        "title": "3X Lappy Toppy sticker bonus!",
        "showUpgradeLink": true
      },
      "reasonCannotSeeComments": "loggedout",
      "reasonCannotAddComments": "loggedout"
    },
    "signature": "jtFNov2aJadMtoHO5BVZygpvnQQ"
  }
}

*/
