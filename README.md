jee_reference_project
=====================

A JEE6 reference project

Overview
--------

The purpose of this project is to "steal" code from it.
Basically tries to accumulate concepts, ideas and corresponding solutions inspired mostly by real life situations.

There is an emphasis on pointing out important parts ([POI](#poi)): non-trivial/hard-to-remember solutions to problems, unintuitive or strange behaviour, some useful features which are likely to be unknown to most - or really anything that can be considered important when working on an application.

The project itself has no purpose regarding functionality. It is buildable and deployable however. There's really no point in listing the features, what's interesting is the [technologies](#technologies) that are being used in this project.

Technologies
------------

- CDI (Contexts and Dependency Injection)
  - Interceptor
      - Logging
      - Retrying on optimistic lock exception
  - Decorator
  - CDI lifecycle observer (javax.enterprise.inject.spi.Extension)
- EJB
  - Singleton
      - Initializing database
  - Stateless (For using CMT)
  - MDB (JMS consumer)
- RESTful web services (JAX-RS)
  - Exception handling
- REST client (Not JAX-RS 2.1 yet!)
- Persistence
  - JPA
      - Entity
          - OneToOne
          - OneToMany, ManyToOne
          - ManyToMany
          - IdClass
      - Metamodel
      - CRUD
      - Criteria API
      - NamedQuery
      - Explicit optimistic lock
  - Hibernate
      - Initialize database (for tests)
- Drools
- Guvnor (untested)
- JMS producer
- Distributed transaction
- Spring integration (Spring beans as CDI beans)
  - Reading properties from property files and exposing them through a Spring bean (which is ultimately exposed as a CDI bean)
- Json serializer/deserializer
- Arquillian integration test
- Instance builder (for tests)

TODO technologies
-----------------
- UI
- Security
- Entity inheritence
- Timer EJB
- SOAP
- RMI

POI
---
The ```jee.reference.meta``` package contains various structures to help point out important/interesting parts of the project.

A prime example is the ```@POI``` annotation and the ```POITag``` enum. The ```@POI``` annotation is used to annotate points of interest, and the ```POITag``` enum is simply a list of keywords a subset of which can be used inside ```@POI```.

There are other similar structures as well. (```@NOTE```, ```@TODO``` etc.)

The concept here is to add typesafe meta information. The main advantage is (compared to using simple comments instead) is that they can be refactored and also the project can be queried for certain keywords easily with the help of any IDE.

Required to deploy
------------------
- JMS Queue
  - queue/admin
- Datasource
  - java:jboss/datasources/AdminDS (Referenced from persistence.xml)
- JDBC driver
  - org.h2.jdbcx.JdbcDataSource

  When deployed from IDE
  ----------------------
  Make sure that the ```target/generated-sources/annotations``` (contains the JPA metamodels) is on the source path.

Tested on
---------
JBoss AS 7.1.1
