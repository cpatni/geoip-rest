##Introduction

GeoIP RESTful APIs are powered by Maxmind GeoIP City Database and provide Geo Location by IP address.
The URL scheme is:

http://localhost/cities/{ip}.{format}
http://localhost/countries/{ip}.{format}

XML, JSON, Ruby and PHP are supported formats. See object notation for explanation.


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
See http://rubyorchard.wordpress.com/2010/08/17/object-notations-beyond-javascript/ for more details.

The GeoIP API provides XML, JSON, PHP and Ruby serialization format. Typically RESTful services are
limited to JavaScript Object Notation (JSON). We are extending object notations to include PHP and
Ruby languages now to better target PHP and Ruby applications. Targeting Object Notations for dynamic
languages seems like a very good fit for REST style services. Sample outputs of various Object Notations are:


### XML

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <city>
      <name>Daly City</name>
      <region_code>CA</region_code>
      <region_name>California</region_name>
      <country_code>US</country_code>
      <country_name>United States</country_name>
      <postal_code>94015</postal_code>
      <metro_code>807</metro_code>
      <metro_name>San Francisco-Oakland-San Jose</metro_name>
      <time_zone>America/Los_Angeles</time_zone>
      <area_code>650</area_code>
      <longitude>-122.476395</longitude>
      <latitude>37.678604</latitude>
    </city>
      
### JSON

    {
    name: Daly City
    region_code: CA
    region_name: California
    country_code: US
    country_name: United States
    postal_code: 94015
    metro_code: 807
    metro_name: San Francisco-Oakland-San Jose
    time_zone: America/Los_Angeles
    area_code: 650
    longitude: -122.476395
    latitude: 37.678604
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
