package ign.geoip.models;


import java.util.HashMap;
import java.util.Map;

/**
 * User: cpatni
 * Date: Aug 7, 2010
 * Time: 3:23:00 PM
 */
public class MetroCode {



   public static final String ALL = "Anchorage AK\t743\n" +
           "Fairbanks AK\t745\n" +
           "Juneau AK\t747\n" +
           "Birmingham AL\t630\n" +
           "Dothan AL\t606\n" +
           "Huntsville-Decatur (Florence) AL\t691\n" +
           "Mobile AL-Pensacola (Ft. Walton Beach) FL\t686\n" +
           "Montgomery (Selma) AL\t698\n" +
           "Ft. Smith-Fayetteville-Springdale-Rogers AR\t670\n" +
           "Jonesboro AR\t734\n" +
           "Little Rock-Pine Bluff AR\t693\n" +
           "Monroe LA-El Dorado AR\t628\n" +
           "Phoenix AZ\t753\n" +
           "Tucson (Sierra Vista) AZ\t789\n" +
           "Yuma AZ-El Centro CA\t771\n" +
           "Bakersfield CA\t800\n" +
           "Chico-Redding CA\t868\n" +
           "Eureka CA\t802\n" +
           "Fresno-Visalia CA\t866\n" +
           "Los Angeles CA\t803\n" +
           "Monterey-Salinas CA\t828\n" +
           "Palm Springs CA\t804\n" +
           "Sacramento-Stockton-Modesto CA\t862\n" +
           "San Diego CA\t825\n" +
           "San Francisco-Oakland-San Jose CA\t807\n" +
           "Santa Barbara-Santa Maria-San Luis Obispo CA\t855\n" +
           "Yuma AZ-El Centro CA\t771\n" +
           "Colorado Springs-Pueblo CO\t752\n" +
           "Denver CO\t751\n" +
           "Grand Junction-Montrose CO\t773\n" +
           "Hartford & New Haven CT\t533\n" +
           "Washington DC (Hagerstown MD)\t511\n" +
           "Ft. Myers-Naples FL\t571\n" +
           "Gainesville FL\t592\n" +
           "Jacksonville FL\t561\n" +
           "Miami-Ft. Lauderdale FL\t528\n" +
           "Mobile AL-Pensacola (Ft. Walton Beach) FL\t686\n" +
           "Orlando-Daytona Beach-Melbourne FL\t534\n" +
           "Panama City FL\t656\n" +
           "Tallahassee FL-Thomasville GA\t530\n" +
           "Tampa-St. Petersburg (Sarasota) FL\t539\n" +
           "West Palm Beach-Ft. Pierce FL\t548\n" +
           "Albany GA\t525\n" +
           "Atlanta GA\t524\n" +
           "Augusta GA\t520\n" +
           "Columbus GA\t522\n" +
           "Macon GA\t503\n" +
           "Savannah GA\t507\n" +
           "Tallahassee FL-Thomasville GA\t530\n" +
           "Honolulu HI\t744\n" +
           "Cedar Rapids-Waterloo-Iowa City & Dubuque IA\t637\n" +
           "Davenport IA-Rock Island-Moline IL\t682\n" +
           "Des Moines-Ames IA\t679\n" +
           "Ottumwa IA-Kirksville MO\t631\n" +
           "Quincy IL-Hannibal MO-Keokuk IA\t717\n" +
           "Rochester MN-Mason City IA-Austin MN\t611\n" +
           "Sioux City IA\t624\n" +
           "Boise ID\t757\n" +
           "Idaho Falls-Pocatello ID\t758\n" +
           "Twin Falls ID\t760\n" +
           "Champaign & Springfield-Decatur,IL\t648\n" +
           "Chicago IL\t602\n" +
           "Davenport IA-Rock Island-Moline IL\t682\n" +
           "Paducah KY-Cape Girardeau MO-Harrisburg-Mount Vernon IL\t632\n" +
           "Peoria-Bloomington IL\t675\n" +
           "Quincy IL-Hannibal MO-Keokuk IA\t717\n" +
           "Rockford IL\t610\n" +
           "Evansville IN\t649\n" +
           "Ft. Wayne IN\t509\n" +
           "Indianapolis IN\t527\n" +
           "Lafayette IN\t582\n" +
           "South Bend-Elkhart IN\t588\n" +
           "Terre Haute IN\t581\n" +
           "Joplin MO-Pittsburg KS\t603\n" +
           "Topeka KS\t605\n" +
           "Wichita-Hutchinson KS\t678\n" +
           "Bowling Green KY\t736\n" +
           "Lexington KY\t541\n" +
           "Louisville KY\t529\n" +
           "Paducah KY-Cape Girardeau MO-Harrisburg-Mount Vernon IL\t632\n" +
           "Alexandria LA\t644\n" +
           "Baton Rouge LA\t716\n" +
           "Lafayette LA\t642\n" +
           "Lake Charles LA\t643\n" +
           "Monroe LA-El Dorado AR\t628\n" +
           "New Orleans LA\t622\n" +
           "Shreveport LA\t612\n" +
           "Boston MA-Manchester NH\t506\n" +
           "Providence RI-New Bedford MA\t521\n" +
           "Springfield-Holyoke MA\t543\n" +
           "Baltimore MD\t512\n" +
           "Salisbury MD\t576\n" +
           "Washington DC (Hagerstown MD)\t511\n" +
           "Bangor ME\t537\n" +
           "Portland-Auburn ME\t500\n" +
           "Presque Isle ME\t552\n" +
           "Alpena MI\t583\n" +
           "Detroit MI\t505\n" +
           "Flint-Saginaw-Bay City MI\t513\n" +
           "Grand Rapids-Kalamazoo-Battle Creek MI\t563\n" +
           "Lansing MI\t551\n" +
           "Marquette MI\t553\n" +
           "Traverse City-Cadillac MI\t540\n" +
           "Duluth MN-Superior WI\t676\n" +
           "Mankato MN\t737\n" +
           "Minneapolis-St. Paul MN\t613\n" +
           "Rochester MN-Mason City IA-Austin MN\t611\n" +
           "Columbia-Jefferson City MO\t604\n" +
           "Joplin MO-Pittsburg KS\t603\n" +
           "Kansas City MO\t616\n" +
           "Ottumwa IA-Kirksville MO\t631\n" +
           "Paducah KY-Cape Girardeau MO-Harrisburg-Mount Vernon IL\t632\n" +
           "Quincy IL-Hannibal MO-Keokuk IA\t717\n" +
           "Springfield MO\t619\n" +
           "St. Joseph MO\t638\n" +
           "St. Louis MO\t609\n" +
           "Biloxi-Gulfport MS\t746\n" +
           "Columbus-Tupelo-West Point MS\t673\n" +
           "Greenwood-Greenville MS\t647\n" +
           "Hattiesburg-Laurel MS\t710\n" +
           "Jackson MS\t718\n" +
           "Meridian MS\t711\n" +
           "Billings MT\t756\n" +
           "Butte-Bozeman MT\t754\n" +
           "Glendive MT\t798\n" +
           "Great Falls MT\t755\n" +
           "Helena MT\t766\n" +
           "Missoula MT\t762\n" +
           "Charlotte NC\t517\n" +
           "Greensboro-High Point-Winston Salem NC\t518\n" +
           "Greenville-New Bern-Washington NC\t545\n" +
           "Greenville-Spartanburg SC-Asheville NC-Anderson SC\t567\n" +
           "Raleigh-Durham (Fayetteville) NC\t560\n" +
           "Wilmington NC\t550\n" +
           "Fargo-Valley City ND\t724\n" +
           "Minot-Bismarck-Dickinson(Williston) ND\t687\n" +
           "Cheyenne WY-Scottsbluff NE\t759\n" +
           "Lincoln & Hastings-Kearney NE\t722\n" +
           "North Platte NE\t740\n" +
           "Omaha NE\t652\n" +
           "Boston MA-Manchester NH\t506\n" +
           "Albuquerque-Santa Fe NM\t790\n" +
           "Las Vegas NV\t839\n" +
           "Reno NV\t811\n" +
           "Albany-Schenectady-Troy NY\t532\n" +
           "Binghamton NY\t502\n" +
           "Buffalo NY\t514\n" +
           "Burlington VT-Plattsburgh NY\t523\n" +
           "Elmira NY\t565\n" +
           "New York NY\t501\n" +
           "Rochester NY\t538\n" +
           "Syracuse NY\t555\n" +
           "Utica NY\t526\n" +
           "Watertown NY\t549\n" +
           "Cincinnati OH\t515\n" +
           "Cleveland-Akron (Canton) OH\t510\n" +
           "Columbus OH\t535\n" +
           "Dayton OH\t542\n" +
           "Lima OH\t558\n" +
           "Toledo OH\t547\n" +
           "Wheeling WV-Steubenville OH\t554\n" +
           "Youngstown OH\t536\n" +
           "Zanesville OH\t596\n" +
           "Oklahoma City OK\t650\n" +
           "Sherman TX-Ada OK\t657\n" +
           "Tulsa OK\t671\n" +
           "Wichita Falls TX & Lawton OK\t627\n" +
           "Bend OR\t821\n" +
           "Eugene OR\t801\n" +
           "Medford-Klamath Falls OR\t813\n" +
           "Portland OR\t820\n" +
           "Erie PA\t516\n" +
           "Harrisburg-Lancaster-Lebanon-York PA\t566\n" +
           "Johnstown-Altoona PA\t574\n" +
           "Philadelphia PA\t504\n" +
           "Pittsburgh PA\t508\n" +
           "Wilkes Barre-Scranton PA\t577\n" +
           "Providence RI-New Bedford MA\t521\n" +
           "Charleston SC\t519\n" +
           "Columbia SC\t546\n" +
           "Florence-Myrtle Beach SC\t570\n" +
           "Greenville-Spartanburg SC-Asheville NC-Anderson SC\t567\n" +
           "Rapid City SD\t764\n" +
           "Sioux Falls(Mitchell) SD\t725\n" +
           "Chattanooga TN\t575\n" +
           "Jackson TN\t639\n" +
           "Knoxville TN\t557\n" +
           "Memphis TN\t640\n" +
           "Nashville TN\t659\n" +
           "Tri-Cities TN-VA\t531\n" +
           "Abilene-Sweetwater TX\t662\n" +
           "Amarillo TX\t634\n" +
           "Austin TX\t635\n" +
           "Beaumont-Port Arthur TX\t692\n" +
           "Corpus Christi TX\t600\n" +
           "Dallas-Ft. Worth TX\t623\n" +
           "El Paso TX\t765\n" +
           "Harlingen-Weslaco-Brownsville-McAllen TX\t636\n" +
           "Houston TX\t618\n" +
           "Laredo TX\t749\n" +
           "Lubbock TX\t651\n" +
           "Odessa-Midland TX\t633\n" +
           "San Angelo TX\t661\n" +
           "San Antonio TX\t641\n" +
           "Sherman TX-Ada OK\t657\n" +
           "Tyler-Longview(Lufkin & Nacogdoches) TX\t709\n" +
           "Victoria TX\t626\n" +
           "Waco-Temple-Bryan TX\t625\n" +
           "Wichita Falls TX & Lawton OK\t627\n" +
           "Salt Lake City UT\t770\n" +
           "Charlottesville VA\t584\n" +
           "Harrisonburg VA\t569\n" +
           "Norfolk-Portsmouth-Newport News VA\t544\n" +
           "Richmond-Petersburg VA\t556\n" +
           "Roanoke-Lynchburg VA\t573\n" +
           "Tri-Cities TN-VA\t531\n" +
           "Burlington VT-Plattsburgh NY\t523\n" +
           "Seattle-Tacoma WA\t819\n" +
           "Spokane WA\t881\n" +
           "Yakima-Pasco-Richland-Kennewick WA\t810\n" +
           "Duluth MN-Superior WI\t676\n" +
           "Green Bay-Appleton WI\t658\n" +
           "La Crosse-Eau Claire WI\t702\n" +
           "Madison WI\t669\n" +
           "Milwaukee WI\t617\n" +
           "Wausau-Rhinelander WI\t705\n" +
           "Bluefield-Beckley-Oak Hill WV\t559\n" +
           "Charleston-Huntington WV\t564\n" +
           "Clarksburg-Weston WV\t598\n" +
           "Parkersburg WV\t597\n" +
           "Wheeling WV-Steubenville OH\t554\n" +
           "Casper-Riverton WY\t767\n" +
           "Cheyenne WY-Scottsbluff NE\t759";


    static {
        build();
    }

    private static Map<Integer, String> allMappings;

    private static void build() {
        allMappings = new HashMap<Integer, String>(250);

        String[] lines = ALL.split("\n");
        for (String line : lines) {
            String[] parts = line.split("\t");
            allMappings.put(Integer.valueOf(parts[1]), parts[0].substring(0, parts[0].length() - 3));


        }
    }

    public static Map<Integer, String> all() {
        return allMappings;
    }

}
