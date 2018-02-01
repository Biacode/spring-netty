# spring-netty
Netty spring integration.

[![Build Status](https://travis-ci.org/hermes-chat/spring-netty.svg?branch=master)](https://travis-ci.org/hermes-chat/spring-netty)

# Install

### Maven
```xml
<repositories>
  <repository>
    <id>snapshots-repo</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    <releases><enabled>false</enabled></releases>
    <snapshots><enabled>true</enabled></snapshots>
  </repository>
  <repository>
      <id>jcenter</id>
      <url>http://jcenter.bintray.com </url>
      <snapshots>
          <enabled>true</enabled>
          <updatePolicy>never</updatePolicy>
          <checksumPolicy>warn</checksumPolicy>
      </snapshots>
      <releases>
          <enabled>true</enabled>
          <checksumPolicy>warn</checksumPolicy>
      </releases>
  </repository>
</repositories>

<dependency>
  <groupId>org.biacode.hermes.spring.netty</groupId>
  <artifactId>starter</artifactId>
  <version>0.5.0-SNAPSHOT</version>
</dependency>
```

### Gradle
```groovy

repositories {
    mavenCentral()
    maven {
      url "https://oss.sonatype.org/content/repositories/snapshots"
    }
    jcenter()
}

compile "org.biacode.hermes.spring.netty:starter:0.5.0-SNAPSHOT"
```