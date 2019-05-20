import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.hpe.kraal.gradle.KraalTask
import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val vMicronaut="1.1.2.BUILD-SNAPSHOT"
val vKotlin="1.3.31"
val vMicronautKotlin="1.0.0.M1"
val vCoroutines="1.2.1"

plugins {
    application
    groovy
    java
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    id("com.github.johnrengelman.shadow") version "4.0.2"
    id("net.ltgt.apt-eclipse") version "0.18"
    id("net.ltgt.apt-idea") version "0.18"
    kotlin("jvm") version "1.3.31"
    kotlin("kapt") version "1.3.21"
    kotlin("plugin.allopen") version "1.3.21"
    id("com.hpe.kraal") version "0.0.15"
}

version = "0.1"
group = "com.allangomes.micronaut"

repositories {
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
    maven { url = uri("https://jcenter.bintray.com") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
}

dependencyManagement {
    imports {
        mavenBom("io.micronaut:micronaut-bom:$vMicronaut")
    }
}

dependencies {

    /* [Micronaut] */
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")
    runtime("io.micronaut:micronaut-core")
    implementation("io.micronaut:micronaut-inject")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-http-client")
    compileOnly("io.micronaut:micronaut-inject-java")
    testImplementation("io.micronaut:micronaut-inject-groovy")
    testImplementation("io.micronaut:micronaut-inject-java")
    /* [Micronaut] */


    /* [Graau] */
    kapt("io.micronaut:micronaut-graal")
    compileOnly("com.oracle.substratevm:svm")
    /* [Graau] */


    /* [Ktor] */
    implementation("io.ktor:ktor-server-netty:1.2.0")
    implementation("io.ktor:ktor-gson:1.2.0")
    /* [Ktor] */


    /* [Kotlin] */
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation(kotlin("reflect", KotlinCompilerVersion.VERSION))
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime:$vMicronautKotlin")
    implementation("io.micronaut.kotlin:micronaut-ktor:$vMicronautKotlin")
    /* [Kotlin] */


    /* [Coroutines] */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$vCoroutines")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$vCoroutines")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$vCoroutines")
    /* [Coroutines] */


    /* [Others] */
    runtime("ch.qos.logback:logback-classic:1.2.3")
    testImplementation("org.spockframework:spock-core") {
        exclude(group="org.codehaus.groovy", module="groovy-all")
    }
    testImplementation("junit:junit:4.12")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    /* [Others] */
}

//run.jvmArgs("-noverify", "-XX:TieredStopAtLevel=1")

application {
    mainClassName = "com.allangomes.micronaut.Application"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
        // need use-experimental for Ktor CIO
        freeCompilerArgs += listOf("-Xuse-experimental=kotlin.Experimental", "-progressive")
        // disable -Werror with: ./gradlew -PwarningsAsErrors=false
        allWarningsAsErrors = project.findProperty("warningsAsErrors") != "false"
    }
}

tasks.withType<ShadowJar> {
    from(tasks.withType<KraalTask>().get("kraal").outputZipTree) {
        exclude("META-INF/*.SF")
        exclude("META-INF/*.DSA")
        exclude("META-INF/*.RSA")
    }
    mergeServiceFiles()
}

allOpen {
    annotation("io.micronaut.aop.Around")
}
