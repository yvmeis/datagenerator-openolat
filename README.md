# Datagenerator for OpenOLAT

## License
Look at the LICENSE file regarding licensing

## SetUp Guide
### Preconditions
- Install git
- Install java 11 or newer
- Install maven 3.8 or newer

### 1. Clone this Repository

### 2. Open in IDE
This guide is for VSCode, as it supports both `Java` and `VueJS`. Use other IDEs at your own risk.
In VSCode click on `File->Open` Folder in the top left and select the folder into which you cloned the repository.
Then open a terminal by clicking `Terminal->New Terminal` at the top.

### 3. For Developement
For Dev-purposes it is recommended to split the Terminal. Click `Terminal->Split` Terminal.
Navigate the left Terminal to /datagenerator with `"cd datagenerator"`. Navigate the other to /datagenerator/src/frontend with `"cd datagenerator/src/frontend"`.
Run `"datagenerator/src/main/java/com/frentix/datagenerator/DatageneratorApplication.java"` to start the datagenerator server. In the right terminal run `"npm run serve"` to start the frontend. The datagenerator should now be running locally on `Port:3000`. The Port can be changed at `"datagenerator/src/frontend/vue.config.js"`.

### 4. Building
In the left Terminal run `"mvn clean package"`. This creates the following file: "datagenerator/target/datagenerator-0.0.1-SNAPSHOT.jar". Execute it with `"java -jar target/datagenerator-0.0.1-SNAPSHOT.jar"`. The datagenerator now runs locally on `Port:8006`. The Port can be changed at `"datagenerator/src/main/resources/application.properties"`.

## Using the Generator
### Login
You can technically enter any String into any of the login fields. Be aware, the generator only functions as intended with valid inputs, but false inputs are completely harmless. As soon as you have logged in you may access the application. Generally, if you are unsure about a feature click on the `Help` link at the bottom right of the page.

### Safe use
Always consult other users of your OO instance if you may generate data. As the generator, in Random mode, adds data to everything! Using the `Cleaning page` is generally safe, as it only deletes things that were added using your login details. If you share your login with someone else always ask before deleting anything as the generator can `literally empty an OO instance`.

### Possible mistakes
- Always double check that your login data is valid and that you have admin rights on the OO instance. If the tables on the Random page have content then you did everything correctly. 
- Ensure that the Inputs in the Random page make sense. Don't give "9" as a Taxonomy key if it doesn't exist.
- While using the Premades ensure that you have created a Taxonomy in your system with the correct name. If you don't know the name look it up in the Help page.
- When creating your own Custom template ensure you follow the conventions of the premades. The file structure may not be changed! All files, apart from taxonomies and curricula, must exist, even if you leave them empty. Look at the Help page for further information.
