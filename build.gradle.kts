plugins {
    kotlin("jvm") version "1.7.21"
    application
    `maven-publish`
    signing
}

group = "top.banned.library"
version = "0.1.0"

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
    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "top.banned.library"
            artifactId = "image-search"
            version = "1.0"
            
            from(components["kotlin"])
    
            pom {
                name.set("image-search")
                description.set("a tool you can search image by saucenao or other search engine")
                url.set("https://github.com/banned2054/image-search")
                licenses {
                    license {
                        name.set("GNU Affero General Public License v3.0")
                        url.set("https://www.gnu.org/licenses/agpl-3.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("banned")
                        name.set("banned")
                        email.set("banned2054@163.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/banned2054/image-search.git")
                    developerConnection.set("scm:git:ssh://github.com/banned2054/image-search.git")
                    url.set("https://github.com/banned2054/image-search")
                }
            }
        }
    }
    signing {
        var keyId = System.getenv("SIGNING_KEY_ID")
    }
}
