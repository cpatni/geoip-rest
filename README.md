##Introduction

GeoIP RESTful APIs are powered by Maxmind GeoIP City Database and provide Geo Location by IP address. The URL scheme is:


* http://localhost/cities/{ip}.{format}
* http://localhost/countries/{ip}.{format}

XML, JSON, Ruby and PHP are supported formats. See object notation for explanation. The API logic has 100% code coverage.


## Country Service

Returns the originating country's name and code for the specified IP address.

* http://localhost/countries/24.6.249.189.xml
* http://localhost/countries/24.6.249.189.json
* http://localhost/countries/24.6.249.189.rb
* http://localhost/countries/24.6.249.189.php

Special keyword `self` returns location of caller's IP Address.

* http://localhost/countries/self.xml
* http://localhost/countries/self.json
* http://localhost/countries/self.rb
* http://localhost/countries/self.php

## City Service
Returns the country, state/region, city, US postal code, US area code, metro code, latitude, and longitude
information for the specified IP.


* http://localhost/cities/24.6.249.189.xml
* http://localhost/cities/24.6.249.189.json
* http://localhost/cities/24.6.249.189.rb
* http://localhost/cities/24.6.249.189.php

Special keyword self returns location of caller's IP Address.

* http://localhost/cities/self.xml
* http://localhost/cities/self.json
* http://localhost/cities/self.rb
* http://localhost/cities/self.php


## Object Notation
See [Object Notations Beyond Javascript](http://rubyorchard.wordpress.com/2010/08/17/object-notations-beyond-javascript/) for more details.

The GeoIP API provides XML, JSON, PHP and Ruby serialization format. Typically RESTful services are
limited to JavaScript Object Notation (JSON). We are extending object notations to include PHP and
Ruby languages now to better target PHP and Ruby applications. Targeting Object Notations for dynamic
languages seems like a very good fit for REST style services. Sample outputs of various Object Notations are:

## Object Notation

The GeoIP API provides XML, JSON, PHP and Ruby serialization format. Typically RESTful services are limited to JavaScript Object Notation (JSON). We are extending object notations to include PHP and Ruby languages now to better target PHP and Ruby applications. Targetting Object Notations for dynamic languages seems like a very good fit for REST style services. Sample outputs of various Object Notations are:


### XML

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <city>
      <name>Daly City</name>
      <region-code>CA</region-code>
      <region-name>California</region-name>
      <country-code>US</country-code>
      <country-name>United States</country-name>
      <postal-code>94015</postal-code>
      <metro-code>807</metro-code>
      <metro-name>San Francisco-Oakland-San Jose</metro-name>
      <time-zone>America/Los_Angeles</time-zone>
      <area-code>650</area-code>
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

http://localhost/about

##License
geoip-rest is licensed user GNU LESSER GENERAL PUBLIC LICENSE.

geoip-rest requires the usage of a local MaxMind database. Currently,
their GeoLite databases may be used for free, provided that MaxMind is
credited as the source of the data and the other terms of the Open Data
License below are followed.

http://geolite.maxmind.com/download/geoip/database/LICENSE.txt

In order to waive the attribution requirement or, if you are planning on
using one of the paid, GeoIP databases, please contact MaxMind at
info@maxmind.com.
