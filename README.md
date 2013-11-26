statserver
==========

Take Home Assignment from LDRLY

Instructions for local setup:

- Maven 2, MySql v5+ and JAVA 1.7 need to be installed
- create a new database schema named 'ldrly' in MySql
- extract the project and run the SQL scripts in /statserver/src/main/resources/sql:
createUser
createStats
insertUsers
insertStats
- adjust the sql connection parameters in /statserver/src/main/resources/hibernate.cfg.xml
- build the project with maven: 
$mvn clean install
- run the REST server with the jetty plugin: 
$mvn jetty:run
- test the server with curl or a browser plugin such as Chrome's 'Advanced REST Client'
- open the file /statserver/src/main/resources/html/statsserver.html in a browser and test the server through the front end

Note: Since Jetty serves the REST API on port 8080 and the frontend is a local file, there are cross domain issues when making AJAX calls from the file to the REST server. A proper setup would be one of the following options
 A) use a webserver such as tomcat to host both, the REST server as well as the static front end, on the same port
 B) use a proxy to redirect request to the REST server and http server respectively. A combination of tomcat7 and apache httpd with  mod_jk is a clean solution and easy to set up.


Useful links:
http://thetechnocratnotebook.blogspot.ca/2012/05/installing-tomcat-7-and-apache2-with.html
