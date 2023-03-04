# Running one application from inside another
## Outer App running a jar programmatically
It does seem possible that we can configure a spring boot app to run a jar programatically.
I was able to have my Outer application running on port 8080 start and stop my Inner application by using the Process class in java to run `java -jar inner-0.0.1-SNAPSHOT.jar`, with my inner application running on port 8081.
However, it seems that the jar has to live outside of the running application.
Java does not seem to allow you to run jars that are in your resources folder.
## Outer App running Inner app as imported dependency
The closest I have come to getting this to work is importing the compiled Inner app as a maven dependency, and calling the Inner application's main method directly.
The error that kept preventing the inner app from starting was `Application already listening on port 8080`.
I believe that running the application this way meant that there was only one spring context shared between the two applications.
I have not figured out if there is a way to have separate spring contexts/profiles for nested applications.
There's not a lot of information about this that I have seen, as this is never a recommended scenario.
## Outer App running Inner app as nested code.
This does not really seem possible after my research. I could not find a way to have an Inner application's code be nested inside the Outer application and have them both compile.
## Outcome
In my opinion the most likely scenario to succeed is having a Spring Boot application import the nested application as a compiled dependency, and try calling the inner application's main method directly.
I believe that this still may create a multitude of problems having one app run inside of another, but that can be tested by developers who understand Hyperledger more than I do.
For this to work, we would have to figure out a way for the Hyperledger Besu jar to be deployed as an artifact in Artifactory, and have our spring application be able to pull it down reliably.

I do believe that having a spring boot application run the hyperledger jar may be an option, but we would have to figure out how to include the hyperledger jar in a way that the spring application could access it.
From my research, if the inner jar is placed inside the outer applications resource folder, java will not let the outer application run the inner application through a `java -jar` command.