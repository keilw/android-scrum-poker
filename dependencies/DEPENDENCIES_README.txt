Get the latest dependencies:

http://docs.codehaus.org/display/JETTY/Embedding+Jetty

The easiest way is to build the i-jetty project using Maven. The build might fail but what it does for you
is getting the latest versions of its dependencies which you can reuse here.

http://code.google.com/p/i-jetty/
To build i-jetty: mvn clean install

Will get all deps into your $MAVEN_HOME (e.g. ~/.m2/repo...).
Copy those deps to the dependencies folder.
