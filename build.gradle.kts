import org.gradle.jvm.tasks.Jar

plugins {
    `build-scan`
    `maven-publish`
    kotlin("jvm") version "1.3.21"
    id("org.jetbrains.dokka") version "0.9.17"
    signing
}

group = "com.btcassessors"
version = "0.0.1"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("junit:junit:4.12")
}


buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"

    //publishAlways()
}

tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allJava)
    archiveClassifier.set("sources")
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    classifier = "javadoc"
    from(tasks.dokka)
}

val sonatypeUsername = project.findProperty("sonatypeUsername").toString()
val sonatypePassword = project.findProperty("sonatypePassword").toString()

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
            artifact(dokkaJar)
            artifact(tasks["sourcesJar"])
            pom {
                name.set("MerkleTree")
                description.set("Basic implementation of a balanced & binary MerkleTree in Kotlin.")
                url.set("https://github.com/BTCAssessors/MerkleTree")
                licenses {
                    license {
                        name.set("The GNU General Public License v3.0")
                        url.set("https://www.gnu.org/licenses/gpl-3.0.html")
                    }
                }

                developers {
                    developer {
                        id.set("ccebrecos")
                        name.set("Carlos GC")
                        email.set("carlos.cebrecos@btcassessors.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/btcassessors/merkletree.git")
                    developerConnection.set("scm:git:ssh://github.com/btcassessors/merkletree.git")
                    url.set("https://github.com/BTCAssessors/MerkleTree")
                }
            }
        }
    }

    repositories {
        maven {
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
            name = "mavenCentral"
            credentials {
                username = sonatypeUsername
                password = sonatypePassword
            }
        }
        maven {
            url = uri("$buildDir/repository")
            name = "local"
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["default"])
}
