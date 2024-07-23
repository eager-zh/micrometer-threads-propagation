# micrometer-threads-propagation

This is a follow-up to a discussion on StackOverflow thread [Spring Boot 3 micrometer tracing in MDC](https://stackoverflow.com/questions/78746378/spring-boot-3-micrometer-tracing-in-mdc). Please refer to [an example project](https://github.com/HeorhiUtseuski/testTracing), provided by this thread's OP.

This project demonstrates a propagation of Observation context to threads, which get executed by `parallelStream` method, actually by `ForkJoinPool` class. 

The only class changed in this project in comparison to [original project](https://github.com/HeorhiUtseuski/testTracing) is `DummyJsonTracingService`. With these changes log now contains rows with correct `traceID` and `spanId` values for threads, managed by `ForkJoinPool`; see `log.log` file for details.  