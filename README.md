**This coding-challenge was part of the interview process with ThoughtWorks for a software developer role. Time frame was about 3 days.**


**Trains**

Commuter railroad service for the towns in Kiwiland. 

**Problem Statement**

The local commuter railroad services a number of towns in Kiwiland.  Because of monetary concerns, all of the tracks are 'one-way.'  That is, a route from Kaitaia to Invercargill does not imply the existence of a route from Invercargill to Kaitaia.  In fact, even if both of these routes do happen to exist, they are distinct and are not necessarily the same distance!

The purpose of this problem is to help the railroad provide its customers with information about the routes.  In particular, you will compute the distance along a certain route, the number of different routes between two towns, and the shortest route between two towns.

**Input**  
> A directed graph where a node represents a town and an edge represents a route between two towns.  The weighting of the edge represents the distance between the two towns.  A given route will never appear more than once, and for a given route, the starting and ending town will not be the same town. 

> The towns are named using the first few letters of the alphabet from `A` to `E`.  A route between two towns (`A` to `B`) with a distance of `5` is represented as `AB5`. A directed graph is represented by a list of routes, with each route as a separate line.

**Available Actions**
-  Compute the distance along a certain route. If no such route exists, output 'NO SUCH ROUTE'. Otherwise, follow the route as given; do not make any extra stops!
- Compute the number of different routes between two towns.
-  Compute the shortest route between two towns.

**Example Input & Output**

Below follows an example with input data, actions performed and expected output result.

**Sample Input:**
```
AB5
BC4
CD8
DC8
DE6
AD5
CE2
EB3
AE7
```

**Actions:**
```
The distance of the route A-B-C.
The distance of the route A-D.
The distance of the route A-D-C.
The distance of the route A-E-B-C-D.
The distance of the route A-E-D.
The number of trips starting at C and ending at C with a maximum of 3 stops.  In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
The number of trips starting at A and ending at C with exactly 4 stops.  In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
The length of the shortest route (in terms of distance to travel) from A to C.
The length of the shortest route (in terms of distance to travel) from B to B.
The number of different routes from C to C with a distance of less than 30.  In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
```

**Expected Output:**
```
Output #1: 9
Output #2: 5
Output #3: 13
Output #4: 22
Output #5: NO SUCH ROUTE
Output #6: 2
Output #7: 3
Output #8: 9
Output #9: 9
Output #10: 7
```

**Implementation**

**Requirenments**

- JDK 1.8
- Gradle

**Run JUnit Tests**

To run the existing JUnit tests using Gradle, execute the following commands
```shell
$ cd path/to/Trains
$ gradle test
```
`JUnit` tests are located under `/src/test/java`.

**Properties**

Output messages are both availabe in English and German, depending on the system's default locale. The messages are available in the `trains.properties` and `trains_de.properties` file, respectively, and can be found under `/src/main/resources/com/lucaslouca/`.

**Build**

The project is a Gradle project. To build, open up your Terminal and fire up the following commands:
```shell
$ cd path/to/Trains
$ gradle build
```
You should see a 'BUILD SUCCESSFUL' message when everything went well. When the build completed succesfully, the program will be named `Trains.jar` and can be found under `/build/libs/` in the `Trains` project directory.

**Usage**

Run the program as follows:
```shell
$ java -jar Trains.jar path/to/graph.txt path/to/commands.txt
```

**Example**
```shell
MacBook-Pro:Trains lucas$ java -jar build/libs/Trains.jar ../../graph.txt ../../commands.txt 
9
5
13
22
2
3
9
9
7
7
```

**Import Project with an IDE**

**Import Project with IntelliJ IDEA**

To import the project using Eclipse, do the following:
- ` File -> New -> Project from Existing Sources` from the main menu.
- Browse to the project directory and click `OK`.
- Select `Gradle` as build tool and click `Next`.
- Specify `Gradle home` and make sure the `Gradle JVM` is set to version `1.8.x`.
- Click `Finish`.

**Import Project with Eclipse**

To import the project using Eclipse, do the following:
- `File -> Import... -> Gradle -> Gradle Project` from the main menu.
- Click `Next`.
- Click `Browse...` for the `Root Directory`.
- Select and open the `Trains` project.
- Click `Build Model`.
- Select all projects.
- Click `Finish`.

Notes:
- You may need <a href="http://marketplace.eclipse.org/content/gradle-integration-eclipse-44" target="_blank">Gradle Integration for Eclipse</a>
- Make sure to replace ``apply plugin: 'idea'`` with ``apply plugin: 'eclipse'`` in the `build.gradle` file located in project's root directory.

**JavaDoc**

`JavaDoc` can be found in the [here](https://github.com/lucaslouca/Trains/tree/master/JavaDoc).

**Important Classes & Interfaces**

**`Main`**

The `Main` class is the main entry point of the application. It contains a `main()` method whose signature looks like this
```java
public static void main(String args[])
```
which the runtime system calls when the program starts. The `main()` method then calls all the other methods required to run the application. It takes two arguments. The first argument is the path to the file containing the *Town Graph*, while the second argument points to the file containing the *commands* we want our application to execute.

**`LLDirectedGraph<T>`**

`LLDirectedGraph` represents a generic directed graph. It provides basic functionality for adding nodes and edges as well as methods for computing the shortest path (Dijkstra) and distance between nodes.

**`LLTownMap`**

The `LLTownMap` interface represents a map that stores towns using a `LLDirectedGraph` underneath. It wraps the functionality of `LLDirectedGraph` and provides methods for accessing it using the town names.

**`LLTown`**

Model representing a town.

**`LLRailRoadServiceImpl`**

`LLRailRoadServiceImpl` implements the `LLRailRoadService` interface. It makes use of `LLTownMap`. Although most of the  functionality in `LLRailRoadServiceImpl` is cascaded to `LLTownMap`, the idea of providing `LLRailRoadServiceImpl`, is to separate the functionality between a service system and a map. That is, `LLRailRoadServiceImpl` could be expanded to support further functionality such as `requestClosingHours()` or `nextTrainDepartureTime()` without the need to modify the `LLTownMap`.

**`LLCommand`**

Interface defining a command that can be executed by calling its `execute()` method. A typical command would be calculating the distance of a route.

**`LLCommandFactory`**
Interface that defines which functionality an `LLCommand` factory must provide.`LLCommandFactory` provides functionality for creating new commands that implement the `LLCommand` interface.

**`LLRailRoadServiceCommandFactory`**
This class implements the `LLCommandFactory` interface. It provides methods for creating concrete command implementations of the abstract type `LLAbstractRailRoadServiceCommand`. The `LLRailRoadServiceCommandFactory` gets initialised with an  `LLRailRoadService`, which it sets as *receiver* in `LLAbstractRailRoadServiceCommand` instances:
```java
LLCommandFactory commandFactory = new LLRailRoadServiceCommandFactory(service);
```
Classes inheriting from `LLAbstractRailRoadServiceCommand` are commands that implement the `LLCommand` interface but are specifically implemented for the `LLRailRoadService`. An example would be the `LLDistanceCommand`. 

**`LLCommandProccesor`**

`LLCommandProccesor` executes `LLCommand` commands. The `LLCommandProccesor` gets initialised with an `LLCommandFactory`.
```java
LLCommandProccesor processor = new LLCommandProccesor(commandFactory);
```
The `LLCommandFactory` will then be used by `LLCommandProccesor` to create `LLCommand` objects for a given input. `LLCommandProccesor` can take and execute a single command in the form of text:
```java
String result = processor.run("distance;A;D"); // compute distance between node 'A' and 'B'
```
or run all commands contained in a text file:
```java
String result = processor.runAll("/Users/lucas/commands.txt"); // execute all commands in commands.txt
```

**Available commands**

* **distance;[TOWN 1];[TOWN 2]; ... ;[TOWN N]** - Compute distance of route. Example: 
```
distance;A;B;C
```
* **count_routes_with_max_hops;[START TOWN];[DESTINATION TOWN];[MAX HOP COUNT]** - Count routes with maximum number of hops.  Example: 
```
count_routes_with_max_hops;C;C;3
```
* **count_routes_with_hops;[START TOWN];[DESTINATION TOWN];[HOP COUNT]** - Count routes with exact number of hops. Example: 
```
count_routes_with_hops;A;C;4
```
* **count_routes_with_max_distance;[START TOWN];[DESTINATION TOWN];[MAX DISTANCE]** - Count routes with maximum distance.  Example: 
```
count_routes_with_max_distance;C;C;29
```
* **length_of_shortest_path;[START TOWN];[DESTINATION TOWN]** - Compute length of shortest path between node. Example:
```
length_of_shortest_path;A;C
```
* **shortest_path;[START TOWN];[DESTINATION TOWN]** - Compute shortest path between nodes. Example: 
```
shortest_path;B;B
```

**`LLPropertyFactory`**

Provides `static` methods for global access to the application's properties.

**Sample Data**

Sample data for a *graph* and *commands* can be found [here](https://github.com/lucaslouca/Trains/tree/master/src/test/resources).
