# Java Reactive Programming Guide
The project demonstrates the features of Reactive programming using Project Reactor in Java

## reactive programming is a programming paradigm where the focus is on developing asynchronous and non-blocking components. The core of reactive programming is a data stream that we can observe and react to, and even apply back pressure to as well. This leads to non-blocking execution, and better scalability with fewer threads of execution.

### Traditional way
Let's look at a traditional web application backend that was developed without using reactive programming.

![image](https://github.com/girichippada/java-Reactive-Programming/assets/57904222/4aa3cd82-ee12-4e9b-8fb3-bb23bf23b961)


As shown in the image above, the backend is hosted in a webserver. When a request comes in, it will be assigned to a particular thread by the webserver, and this thread will be occupied with that request until it finishes the process. This process may be calling a database or a 3rd party API which takes time to complete.

If another request comes in, that request will be assigned to another available thread and that thread will also be busy until the process is completed.

Remember, there is a maximum thread count for a particular web server and that count may be based on the web server. If we use spring-boot, the webserver container will be tomcat and that container will have 200 maximum threads.

### Drawbacks in the traditional way
Once a thread is assigned to a request that thread won’t be available until that request finishes its process.
If all the threads are occupied, the next requests that come into the server will have to wait until at least one thread frees up.
When all the threads are busy, performance will be degraded because memory is being used by all the threads.
As a solution to the above drawbacks, a team of developers, led by Jonas Boner came together and introduced a new programming paradigm. Well, actually, it was a set of core principals.

### Reactive Programming
It’s important to understand the meaning of reactive in order to understand the reactive programming paradigm. React means a form of response, but for what do we react? We react to events, this means reactive is a response to an event. In this way, we can define reactive programming as an event-driven method of programming.

Reactive programming is a programming paradigm where the focus is on developing asynchronous and non-blocking applications in an event-driven form

#### Asynchronous and non-blocking
Asynchronous execution is a way of executing code without the top-down flow of the code. In a synchronous way, if there is a blocking call, like calling a database or calling a 3rd party API, the execution flow will be blocked. But in a non-blocking and asynchronous way, the execution flow will not be blocked. Rather than that, futures and callbacks will be used in asynchronous code execution.
Event/Message Driven stream data flow
In reactive programming, data will flow like a stream and because it is reactive, there will be an event and a response message to that event. In Java, it is similar to java streams which was introduced in Java 1.8. In the traditional way, when we get data from a data source (Eg: database, API), all the data will be fetched at once. In an event-driven stream, the data will be fetched one by one and it will be fetched as an event to the consumer.
![image](https://github.com/girichippada/java-Reactive-Programming/assets/57904222/ccc36a07-add5-4fd7-9240-74ee3e1e591a)

#### Functional style code
In Java, we write lambda expressions for Functional Programming. Lambda expressions are functional style codes. In reactive programming, we mostly use this lambda expression style.
#### Back Pressure
In reactive streams when a reactive application (Consumer) is consuming data from the Producer, the producer will publish data to the application continuously as a stream. Sometimes the application cannot process the data at the speed of the producer. In this case, the consumer can notify the producer to slow down the data publishing.

![image](https://github.com/girichippada/java-Reactive-Programming/assets/57904222/fca1754e-2eac-4c75-8e0e-2d9320604fd2)


#### Reactive Streams Specification
Reactive Streams Specification is a set of rules or set of specifications that you need to follow when designing a reactive stream. Software engineers from well-reputed companies such as Netflix, Twitter, etc, got together and introduced these specifications.

These specifications introduces four interfaces that should be used and overridden when creating a reactive stream.

#### Publisher
This is a single method interface that will be used to register the subscriber to the publisher. The subscribe method of this interface accepts the subscriber object and registers it.

#### Subscriber
This is an interface that has four methods
onSubscribe method will be called by the publisher when subscribing to the Subscribe object.
onNext method will be called when the next data will be published to the subscriber
onError method will be called when exceptions arise while publishing data to the subscriber
onComplete method will be called after the successful completion of data publishing to the subscriber

#### Subscription
This is an interface with two methods. The subscription object will be created when the user subscribes to the publisher in the publisher object as discussed earlier. The subscription object will be passed to the subscriber object via onSubscribe method
request method will be called when the subscriber needs to request data from the publisher
cancel method will be called when the subscriber needs to cancel and close the subscription

#### Processor
This is an interface that is extended by both publisher and subscriber interfaces. This interface is not very common but will be used to process the logic of the subscribing and publishing workflow

Reactive Libraries
A reactive library is nothing but the implementation of reactive specification interfaces which we discussed above. Here are some reactive libraries that are available to us:

#### RxJava
Project Reactor
Flow class in JDK 9
Reactive streams in spring-boot have been developed based on the project reactor.
Because this is Java reactive programming, I will discuss a little bit about project reactor main concepts

#### Project Reactor
Project reactor is one of the main popular reactive libraries in Java. Because this is a reactive library, this is a fully non-blocking reactive stream with backpressure supported. This integrates directly with the Java 8 functional APIs. The main artifact of that project reactor is the reactor-core, and there are some concepts to be discussed in that reactor core.

#### Flux

Flux represents an Asynchronous Sequence of 0-N Items. This is like a stream of 0 to N items, and we can do various transformations to this stream, including transforming it to an entirely different type of 0-N item stream.

![image](https://github.com/girichippada/java-Reactive-Programming/assets/57904222/8cdd9e3a-fefb-432b-96b3-9e334726f501)


As shown in the image, the top side numbers are the N items which was published to the subscriber. It is a type of Flux item stream and the last line represents the completion of that flux stream. The operator box represents the transform operation on that flux stream.

That transformation operation will be applied to the items of flux stream one by one and that is the onNext() method that we discussed earlier. The bottom set of numbers represents the flux stream that was emitted after applying the transformation.

The red X icon represents some error that happened when applying the transformation to that particular item in that flux stream and that is the onError() method that we discussed earlier in section

If there were no errors, the transformation will be applied to all the elements with the onComplete() method that we discussed earlier.

#### Mono

Mono represents only one value stream of items. We can do various transformations to this, including transforming it entirely.

![image](https://github.com/girichippada/java-Reactive-Programming/assets/57904222/57a488da-4a1f-4fe7-a87e-e03f1c82f8f3)


As shown in the image only one item will be published by to the subscriber. The last line represents the completion of the stream. The operator box represents the transform operation for this Mono stream.

That transformation operation will be applied to the item of Mono stream using the onNext() method that we discussed earlier.

The red X icon represents an error that happened when applying the transformation to the item and this is the onError() method that we discussed earlier.

If no error happened, the transformation will be applied to the item successfully and using the onComplete() method.

# Reactive programming using project reactor

## Features
1. Project Reactor (https://github.com/reactor/reactor) is the implementation of Reactive programming in Java. Jdk 9 provided interfaces with no implementation
2. Flux: Use when more than one object needs to be returned
3. Mono: Use when one or none objects need to be returned
4. Supports streaming so can apply intermediate operations: map, filter, flatMap, etc.
5. log method is available for all Publisher classes(Flux, Mono) for logging the events occurring in processing the non-blocking requests
6. use flatMap for the Publisher classes for transforming the data asynchronously and return a publisher object(Flux or Mono)
7. Can handle empty publisher data using defaultIfEMpty method to return a default response
8. Can handle empty publisher data using switchIfEmpty method to return a different publisher object when empty data is received
9. Can concat two reactive streams into one which is useful when need to combine resultset from two external services or DBs using concat and concatWith methods
10. Can merge two reactive streams into one using merge and mergeWith methods
11. Can sequentially merge two publishers' result(Flux) using mergeSequential method
12. Can use zip and zipWith methods where zip method allows to merge 2 to 8 publishers(Flux or Mono), waits for all publishers sends an onEvent event, and continues till any one publisher sends an onComplete event. Whereas zipWIth is an instance method and allows zip up to two publishers(Flux or Mono) and the remaining behavior is the same as zip() method

**References**: https://medium.com/sysco-labs/reactive-programming-in-java-8d1f5c648012
