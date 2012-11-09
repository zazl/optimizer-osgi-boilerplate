# optimizer-osgi-boilerplate

Provides an example OSGi/Eclipse setup for using the [Zazl Optimizer](https://github.com/zazl/optimizer)

## Usage

Clone from github (use --recursive to obtain all the submodules)
git clone --recursive git@github.com:zazl/optimizer-osgi-boilerplate.git

Open Eclipse and use the optimizer-osgi-boilerplate directory as the workspace

Import all the projects via File->Import->General->Existing Projects into workspace

Run the the "Zazl OSGi Sample" launch and point a browser at http://localhost:8080/declarative.html

## Build a WAR containing the Optimizer

Select the "/org.zazl.optimizer.sample.feature/scripts/export.xml" ant script and use right-click->Run As->Ant Build (2nd Ant Build option) to open the Ant Build Dialog. 
Click the JRE tab and select "Run in same JRE as workspace"

Run Ant Build on "/org.zazl.optimizer.sample.feature/scripts/export.xml" via right-click->Run As->Ant Build (1st Ant Build option)
Run Ant Build on "/org.zazl.optimizer.sample.feature/scripts/build.xml"

The result should be "/org.zazl.optimizer.sample.feature/build/package/sample.war"

Install sample.war in a Webcontainer and point browser at http://localhost:8080/sample/declarative.html
