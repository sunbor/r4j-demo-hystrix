# r4j-demo-hystrix
testing resiliency methods using hystrix

This is an application that uses Hystrix to access a pet clinic application. The application is made up of multiple parts:
The main Hystrix application, which is in this repository
The configuration server that allows the application to access configuration properties: https://github.com/sunbor/hystrix-config-properties
The configuration properties, which for this application are in the r4j-demo-hystrix-development.properties file: https://github.com/sunbor/hystrix-config-properties

The application connects to the spring pet clinic app: https://github.com/spring-projects/spring-petclinic

Our application requires 2 instances of the pet clinic app to be running on ports 8082 and 8083. To access the application itself, start the configuration server and the main application and go to localhost:8081.
