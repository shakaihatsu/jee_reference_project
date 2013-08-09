jee_reference_project
=====================

A JEE6 reference project

Overview
--------

The purpose of this project is to "steal" code from it.
Basically tries to accumulate concepts, ideas and corresponding solutions inspired mostly by real life situations.

There is an emphasis on pointing out important parts: non-trivial/hard-to-remember solutions to problems, unintuitive or strange behaviour, some useful features which are likely to be unknown to most - or really anything that can be considered important when working on an application.

The project itself has no purpose regarding functionality. It is buildable and deployable however. There's really no point in listing the features, what's interesting is the technologies that are being used in this project.

Technologies
------------

- CDI
  - Interceptor
    Logging, and retrying on Optimistic Lock
  - Decorator
- EJB
  - Singleton (not too sophisticated)
    Initializing database
  - Stateless (for using CMT)
  - MDB (JMS consumer)
- RESTful web services (JAX-RS)
- REST client (Not JAX-RS 2.1 yet!)
- Persistence
  - JPA
    - Entity
    - CRUD
    - NamedQuery
    - Explicit optimistic lock
- Drools
- Guvnor (untested)
- JMS producer
- Distributed transaction
- Spring integration (Spring beans as CDI beans)
  Reading properties from property files and exposing them through a Spring bean (which is ultimately exposed as a CDI bean)
