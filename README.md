# README #

Project 2: Starting Fabflix Business

Google Cloud Instance:
http://104.154.44.100/fabflix_webapp/login

Amazon Web Service:
http://ec2-54-241-141-83.us-west-1.compute.amazonaws.com/fabflix_webapp/login



Task 3.1:
Average TS: 18118584
Average TJ: 16785153


**Files changed to do connection pooling:**

\META-INF\context.xml.
\src\modules\JDBC.java
\WEB-INF\web.xml (see the resource-ref tag).

**Files changed for prepared statements:**

Pretty much everything that uses database search.


**Connection Pooling Report**

Connection pooling is used instead of creating a new connection every time one needs to access the database. It recycles the database connection in order to handle heavier load. We use connection pooling in our code every single time there is a request to create a connection, which is a method handled in our JDBC class (under "modules").