# optimizer-osgi-boilerplate

Provides an example OSGi/Eclipse setup for using the [Zazl Optimizer](https://github.com/zazl/optimizer)

## Usage

Clone from github (use --recursive to obtain all the submodules)

git clone --recursive git@github.com:zazl/optimizer-osgi-boilerplate.git

Open Eclipse and use the optimizer-osgi-boilerplate directory as the workspace

Import all the projects via File->Import->General->Existing Projects into workspace

## Try the Dojo Sample

The dojo sample uses a variety of dijit widgits, loading them declaratively.

Run the "Zazl Dojo OSGi Sample" launch and point a browser at http://localhost:8080/declarative.html

## Try the Backbone Sample

The backbone sample is a Music Collection webpage loading its data from JSON files. It demonstrates the Zazl Optimizer loading 

[jquery](http://jquery.org)
[backbone](http://backbonejs.org) 
[underscore](http://underscorejs.org) 
[bootstrap](http://twitter.github.com/bootstrap) 
[jquery.tablescroll](http://www.farinspace.com/jquery-scrollable-table-plugin)


Run the "Zazl Backbone OSGi Sample" launch and point a browser at http://localhost:8080/bbsample.html

## Build the Dojo Sample WAR containing the Optimizer

Select the "/org.zazl.optimizer.sample.dojo/scripts/export.xml" Ant script and use right-mouse-click->Run As->Ant Build (2nd Ant Build option) to open the Ant Build Dialog. 
Click the JRE tab and select "Run in same JRE as workspace"

Run Ant Build on "/org.zazl.optimizer.sample.dojo/scripts/export.xml" via right-mouse-click->Run As->Ant Build (1st Ant Build option)
Run Ant Build on "/org.zazl.optimizer.sample.dojo/scripts/build.xml"

The result should be "/org.zazl.optimizer.sample.dojo/build/package/dojosample.war"

Install dojosample.war in a Webcontainer and point browser at http://localhost:8080/dojosample/declarative.html

## Build the Backbone Sample WAR containing the Optimizer

Select the "/org.zazl.optimizer.sample.backbone/scripts/export.xml" Ant script and use right-mouse-click->Run As->Ant Build (2nd Ant Build option) to open the Ant Build Dialog. 
Click the JRE tab and select "Run in same JRE as workspace"

Select the "/org.zazl.optimizer.sample.backbone/scripts/exportserver.xml" Ant script and use right-mouse-click->Run As->Ant Build (2nd Ant Build option) to open the Ant Build Dialog. 
Click the JRE tab and select "Run in same JRE as workspace"

Run Ant Build on "/org.zazl.optimizer.sample.backbone/scripts/export.xml" via right-mouse-click->Run As->Ant Build (1st Ant Build option)
Run Ant Build on "/org.zazl.optimizer.sample.backbone/scripts/exportserver.xml" via right-mouse-click->Run As->Ant Build (1st Ant Build option)
Run Ant Build on "/org.zazl.optimizer.sample.backbone/scripts/build.xml"


The result should be "/org.zazl.optimizer.sample.backbone/build/package/bbsample.war"

Install bbsample.war in a Webcontainer and point browser at http://localhost:8080/bbsample/music.html

### Javascript Compression

Both WARs have Javascript compression via Google Closure turned on by default. The OSGi launches by default have compression turned off but it can be enabled in either of the
provided launches by changing the "zazl.jscompress" VM argument to true.
