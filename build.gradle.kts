plugins {
    application
    kotlin("jvm") version "2.0.0"
}

group = "ir.hajkarami"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

val ktorVersion = "2.3.10"
val logbackVersion = "1.4.14"

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:2.3.7")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:2.3.7")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}