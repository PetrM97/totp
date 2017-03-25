# TOTP
[![Build Status](https://travis-ci.org/PetrM97/totp.svg?branch=master)](https://travis-ci.org/PetrM97/totp)

Implementation of [RFC 6238](https://tools.ietf.org/html/rfc6238) in Java.

Contains Client and Server app written in Java and Demo server in PHP.

Project was made at [Gymnázium Nad Alejí](http://www.alej.cz/) as a Maturita exam work.

## How to use

1. Download it here at GitHub at [Release page](https://github.com/PetrM97/totp/releases)
2. Compile it yourself

## Dependencies

*All of them are downloaded automatically by Apache Ant through Apache Ivy*

* [jUnit](https://github.com/junit-team/junit4)
* [Hamcrest](https://github.com/hamcrest/JavaHamcrest)
* [Apache Commons Codec](https://github.com/apache/commons-codec)
* [Restlet](https://github.com/restlet/restlet-framework-java)
* [JSON-Simple](https://github.com/fangyidong/json-simple)

## Compilation

You need to have 
[**Apache Ant**](https://ant.apache.org/) and 
[**Java JDK**](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 
installed to compile sources and create executable JAR files

To compile everything run:
```
ant jar
```

JAR files are then created in `/build/jar` folder

## Wiki

[Github Wiki](wiki) has more details, but is written in Czech language only.
