# Book Rental POS
## Overview
A fairly simple POS system to handle day-to-day transactions of a Book Rental Store

## Requirements

- Java 8 JRE provided by Oracle
- JavaFX 8 runtime (Should be included in Oracle's JRE 8)
- Windows 7 and above (May add linux compatibility in future)

## External Libraries used

- sqlite jdbc driver 3.32.3 (https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.32.3)
- JFoeniX 8.0.10 (https://mvnrepository.com/artifact/com.jfoenix/jfoenix/8.0.10)

## How to build (Using Apache Maven)
### JDK 1.8
First make sure your JAVA_HOME is pointing to JDK 1.8

Then in the working directory, do `mvn clean package`

The built jar will then be located in `target/BookRentalPOS-JRE-8.jar`

### JDK 11+
Make sure your JAVA_HOME is pointing to your JDK installation directory

Copy the src folder into `jdk11-pom` directory

In the copied src folder, uncomment the code in `src/main/java/module-info.java` 

Then in the working directory, do `mvn -f jdk11-pom/pom-jdk11.xml clean package`

The built jar will then be located in `jdk11-pom/target/BookRentalPOS-JRE11+.jar`

## To run

In the `target` directory, do `java -jar bookrentalpos-1.0-jar-with-dependencies.jar`
