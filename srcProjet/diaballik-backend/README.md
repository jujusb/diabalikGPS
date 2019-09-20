
The back-end of the app.

DO NOT MODIFY THE CONFIGURATION FILES (eg, pom.xml, checkstyle.xml).

Build it using maven:
`mvn clean package`
`mvn clean compile`
`mvn clean test`
etc.

The output war file of the app is built in the `target` folder.

`mvn compile` launches the compilation of the source code (if required), the code analysis powered by Erroprone, Spotbugs, and Checkstyle.

`mvn test` launches `mvn compile` (if required), the compilation of the source test code (if required), the execution of the test suite using JUnit5, the code coverage using Jaccoco, the mutation analysis using Pitest+Descarte.

