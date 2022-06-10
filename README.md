# 🐋 SPDocker (Simple Docker API)

<p align="center">
  <img src="./docs/assets/docker-logo.webp" alt="logo" width="120px" height="120px"/>
  <br>
Simple Docker API is a library that allows you to manage a docker host through code in a simple and efficient way. The best SDK for Docker in Java is here ! Manage your Docker instance by sending HTTP requests to DockerEngineAPI.

</p>

## 🏷️ Features

- 📦 Complete container Management
- 🕸️ Complete network management
- 🗃️ Cluster Management
- 📊 JSON Data support

## 📂 Installation

### Maven

https://github.com/rootxls/SPDocker/packages/1474189

```xml
<dependency>
    <groupId>dev.fls</groupId>
    <artifactId>spdocker</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 📐 Usage

### 🔒 Register SPDocker

First you will need to connect to docker by creating a new session:
```java
Docker docker = new Docker.Builder()
        .withHost("http://localhost:2375")
        .withVersion("1.41")
        .build();
```

### 🏗️ Use modules

Once your docker host is created, you will be able to access the modules using it:


```java
docker.containers(); // The container manager
docker.containers().list(); // Example usage: List all containers
        
docker.networks(); // The network manager
```

🎉 There you go, now you manage every single docker modules.