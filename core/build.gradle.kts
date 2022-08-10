plugins {
    id("java")
    kotlin("jvm")
    `maven-publish`
}

group = "net.gloryx"
version = rootProject.version

java.toolchain.languageVersion.set(JavaLanguageVersion.of(8))

dependencies {
    api("net.kyori:adventure-api:4.11.0")
    api("net.kyori:adventure-extra-kotlin:4.11.0")
    api("de.themoep:minedown-adventure:1.7.1-SNAPSHOT")
}
val javadoc: Javadoc by tasks

val javadocJar by tasks.creating(Jar::class) {
    from(javadoc)
    archiveClassifier.set("javadoc")
}

val sourcesJar by tasks.creating(Jar::class) {
    from(sourceSets["main"].allSource.include("**/*.kt"))
    archiveClassifier.set("sources")
}

publishing {
    repositories {
        maven("https://dev.gloryx.net/main") {
            credentials(PasswordCredentials::class) {
                username = System.getenv("GLORYX_USERNAME")
                password = System.getenv("GLORYX_PASSWORD")
            }
        }
    }
    publications {
        create("maven", MavenPublication::class) {
            artifactId = "commons"
            from(components["java"])
            artifact(javadocJar)
            artifact(sourcesJar)
        }
    }
}