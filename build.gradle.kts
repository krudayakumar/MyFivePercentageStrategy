import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.json:json:20220924")
    implementation("com.google.code.gson:gson:2.10")
    testImplementation(kotlin("test"))
    implementation ("com.google.code.gson:gson:2.8.5")
    implementation("com.github.holgerbrandl.krangl:krangl:0.15.7")
    implementation("com.yahoofinance-api:YahooFinanceAPI:3.17.0")
    implementation("org.jfree:jfreechart:1.5.4")
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}