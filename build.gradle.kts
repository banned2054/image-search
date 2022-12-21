plugins {
    kotlin("jvm") version "1.7.21"
    application
    `maven-publish`
}

group = "top.banned"


repositories {
    maven { setUrl("https://maven.aliyun.com/repository/central") }
    maven { setUrl("https://maven.aliyun.com/repository/jcenter") }
    maven { setUrl("https://maven.aliyun.com/repository/public") }
    maven { setUrl("https://maven.aliyun.com/repository/google") }
    maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
    maven { setUrl("https://maven.aliyun.com/repository/spring/") }
    maven { setUrl("https://maven.aliyun.com/repository/spring-plugin") }
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.squareup.retrofit2:adapter-rxjava:2.9.0")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "top.banned"
            artifactId = "image-search"
            version = "0.1.0"
            
            from(components["kotlin"])
        }
    }
}
