# Datagenerator for OpenOLAT

## License
Look at the LICENSE file regarding licensing

## SetUp Guide
### Preconditions
- Install git
- Install java 11 or newer
- Install Tomcat 9.0
- Install maven 3.8 or newer

### 1. Clone this Repository

### 2. Open in IDE
This guide is for VSCode, as it supports both `Java` and `VueJS`. Use other IDEs at your own risk.
In VSCode click on `File->Open` Folder in the top left and select the folder into which you cloned the repository.
Then open a terminal by clicking `Terminal->New Terminal` at the top.

### 3. For Developement
For Dev-purposes it is recommended to split the Terminal. Click `Terminal->Split` Terminal.
Navigate the left Terminal to /datagenerator with `"cd datagenerator"`. Navigate the other to /datagenerator/src/frontend with `"cd datagenerator/src/frontend"`.
Run `"datagenerator/src/main/java/com/frentix/datagenerator/DatageneratorApplication.java"` to start the datagenerator server. In the right terminal run `"npm run serve"` to start the frontend. The datagenerator should now be running locally on `Port:3000`

### 4. Building
In the left Terminal run `"mvn clean package"`. This creates the following file: "datagenerator/target/datagenerator-0.0.1-SNAPSHOT.jar". Execute it with `"java -jar target/datagenerator-0.0.1-SNAPSHOT.jar"`. The datagenerator now runs locally on `Port:8006`
