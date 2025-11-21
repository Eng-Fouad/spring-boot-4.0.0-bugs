plugins {
    java
    alias(libs.plugins.hibernate)
    alias(libs.plugins.springboot)
}

group = "io.fouad"
version = "1.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.json)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.mssql.jdbc)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.starter.testcontainers)
    testImplementation(libs.spring.boot.starter.devtools)
    testImplementation(libs.hibernate.annotations)
    testImplementation(libs.testcontainers.mssql)
}

// See https://stackoverflow.com/a/58921787/597657
hibernate {
    enhancement {
        enableLazyInitialization = true     // Whether to incorporate lazy loading support into the enhanced bytecode.
        enableDirtyTracking = true          // Whether to incorporate dirty tracking into the enhanced bytecode.
        enableAssociationManagement = false // Whether to add bidirectional association management into the enhanced bytecode
        enableExtendedEnhancement = false   // Bytecode enhancement can work even beyond calling getters and setters on entities
    }
}

springBoot {
    mainClass.set("io.fouad.backend.AppLauncher")
}

tasks.getByName<org.springframework.boot.gradle.plugin.ResolveMainClassName>("resolveTestMainClassName") {
    configuredMainClassName.set("io.fouad.backend.tests.TestAppLauncher")
}

tasks.compileJava {
    options.encoding = "UTF-8"
    options.compilerArgs = listOf("-parameters", "-Xdoclint:none", "-Xlint:all", "-Xlint:-exports",
        "-Xlint:-serial", "-Xlint:-try", "-Xlint:-requires-transitive-automatic",
        "-Xlint:-requires-automatic", "-Xlint:-missing-explicit-ctor",
        "-Xlint:-processing", "-Xlint:-this-escape")
}

tasks.compileTestJava {
    options.encoding = "UTF-8"
    options.compilerArgs = listOf("-parameters", "-Xdoclint:none", "-Xlint:all", "-Xlint:-exports",
        "-Xlint:-serial", "-Xlint:-try", "-Xlint:-requires-transitive-automatic",
        "-Xlint:-requires-automatic", "-Xlint:-missing-explicit-ctor",
        "-Xlint:-processing", "-Xlint:-this-escape")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

val devEnvironmentVariables = emptyMap<String, String>()
val devSystemProperties = mapOf("azureSqlEdgeImage" to libs.versions.azureSqlEdgeImage.get())

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootTestRun") {
    environment(devEnvironmentVariables)
    systemProperties(devSystemProperties)
}

tasks.test {
    useJUnitPlatform()
    environment(devEnvironmentVariables)
    systemProperties(devSystemProperties)
}