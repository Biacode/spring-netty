# Install

# Stable
There is no stable release yet.

# Snapshots

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
  <groupId>org.biacode.spring.netty</groupId>
  <artifactId>starter</artifactId>
  <version>${spring.netty.version}</version>
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

compile "org.biacode.spring.netty:starter:${springNettyVersion}"
```