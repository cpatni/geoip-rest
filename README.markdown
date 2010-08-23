##Introduction

GeoIP RESTful service provides a user's location by IP address. The URL scheme is:

http://geoip.ign.com/cities/{ip}.{format}  
http://geoip.ign.com/countries/{ip}.{format}

XML, JSON, Ruby and PHP are supported formats. See object notation for explanation.


## Country Service

Returns the originating country's name and code for the specified IP address.

* http://geoip.ign.com/countries/24.6.249.189.xml
* http://geoip.ign.com/countries/24.6.249.189.json
* http://geoip.ign.com/countries/24.6.249.189.rb
* http://geoip.ign.com/countries/24.6.249.189.php

Special keyword `self` returns location of caller's IP Address.

* http://geoip.ign.com/countries/self.xml
* http://geoip.ign.com/countries/self.json
* http://geoip.ign.com/countries/self.rb
* http://geoip.ign.com/countries/self.php

## City Service
Returns the country, state/region, city, US postal code, US area code, metro code, latitude, and longitude                information for the specified IP.


* http://geoip.ign.com/cities/24.6.249.189.xml
* http://geoip.ign.com/cities/24.6.249.189.json
* http://geoip.ign.com/cities/24.6.249.189.rb
* http://geoip.ign.com/cities/24.6.249.189.php

Special keyword self returns location of caller's IP Address.

* http://geoip.ign.com/cities/self.xml
* http://geoip.ign.com/cities/self.json
* http://geoip.ign.com/cities/self.rb
* http://geoip.ign.com/cities/self.php

## Legacy GeoIP Service (depreacated)

The existing service http://geo.ign.com/g/?c=1&fmt=xml&ip=212.101.97.206 has been deprecated as it didn't comply with other IGN services. There is no variant of city service for this.

## Restrictions

GeoIP service is restricted to be used for server to server communication and may not be used for jsonp like transport for external applications.

## Object Notation

The GeoIP API provides XML, JSON, PHP and Ruby serialization format. Typically RESTful services are limited to JavaScript Object Notation (JSON). We are extending object notations to include PHP and Ruby languages now to better target PHP and Ruby applications. Targetting Object Notations for dynamic languages seems like a very good fit for REST style services. Sample outputs of various Object Notations are:


### XML

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <city>
      <name>Daly City</name>
      <regionCode>CA</regionCode>
      <regionName>California</regionName>
      <countryCode>US</countryCode>
      <countryName>United States</countryName>
      <postalCode>94015</postalCode>
      <metroCode>807</metroCode>
      <metroName>San Francisco-Oakland-San Jose</metroName>
      <timeZone>America/Los_Angeles</timeZone>
      <areaCode>650</areaCode>
      <longitude>-122.476395</longitude>
      <latitude>37.678604</latitude>
    </city>
      
### JSON

    {
      "name": "Daly City",
      "regionCode": "CA",
      "regionName": "California",
      "countryCode": "US",
      "countryName": "United States",
      "postalCode: 94015,
      "metroCode: 807,
      "metroName": "San Francisco-Oakland-San Jose",
      "timeZone": "America/Los_Angeles",
      "areaCode: 650,
      "longitude: -122.476395,
      "latitude: 37.678604"
    }
            
### Ruby Object Notation
    {
      :name => "Daly City",
      :region_code => "CA",
      :region_name => "California",
      :country_code => "US",
      :country_name => "United States",
      :postal_code => "94015",
      :metro_code => 807,
      :metro_name => "San Francisco-Oakland-San Jose",
      :time_zone => "America/Los_Angeles",
      :area_code => 650,
      :longitude => -122.476395,
      :latitude => 37.678604
    }
      

### PHP Object Notation
    array(
      "name" => "Daly City",
      "region_code" => "CA",
      "region_name" => "California",
      "country_code" => "US",
      "country_name" => "United States",
      "postal_code" => "94015",
      "metro_code" => 807,
      "metro_name" => "San Francisco-Oakland-San Jose",
      "time_zone" => "America/Los_Angeles",
      "area_code" => 650,
      "longitude" => -122.476395,
      "latitude" => 37.678604
    )
    
## Automatic Updates

Updates to GeoIPCity database are checked daily.


## About Service

http://geoip.ign.com/about  

## FAQs
<dl>
	<dt>What will be the production URL?</dt>
	<dd>The production url will be at geoip.ign.com.</dd>
	<dt>Does it support IPv6?</dt>
	<dd>No. There are limited GeoIP databases for IPV6 yet. It will supported in future.</dd>
</dl>
