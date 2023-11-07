plugins {
    kotlin("multiplatform") version "1.9.0"
}

group = "dev.isti"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    js("client") {
        binaries.executable()
        browser()
    }
    sourceSets {
        val commonMain by getting
        val clientMain by getting
    }
}
