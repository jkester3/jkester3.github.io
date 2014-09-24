CS2340Servlet
=============

This project works with Tomcat.

To deploy and run this project,<br />
  1) Clone this project to a folder.<br />
  2a) Assuming you have apache ant installed, go to that folder and type <code>ant clean deploy</code>.<br />
  2b) Otherwise manually move all files into the Tomcat webapps directory, and place the<br />
      classes and web.xml files in the proper locations.<br />
  3) Go to your Tomcat directory, then the bin subdirectory, and type startup.<br />
  4) Open up a web browser and navigate to the following web address:<br />
      <code>localhost:8080/CS2340Servlet/</code>
      
To compile this project from the command line, type:<br />
  1) Using Windows:<br /><code>javac -cp .;lib/servlet-api.jar -d ./out/production src/model/[asterik].java src/controller/[asterik].java</code><br />
  2) Otherwise:<br /><code>javac -cp .:lib/servlet-api.jar -d ./out/production src/model/[asterik].java src/controller/[asterik].java</code><br />
  
  where [asterik] = *
