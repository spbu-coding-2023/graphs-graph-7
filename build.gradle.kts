import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("com.ncorti.ktfmt.gradle") version "0.18.0"
}

ktfmt {
    // KotlinLang style - 4 space indentation - From kotlinlang.org/docs/coding-conventions.html
    kotlinLangStyle()
}

group = "com.graph"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}
val exposedVersion: String by project
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "graphhw"
            packageVersion = "1.0.0"
        }
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
tasks.withType<Test> {
    testLogging {
        events("PASSED", "SKIPPED", "FAILED")
    }

    tasks.register<Copy>("copyPreCommitHook") {
        description = "Copy pre-commit git hook from the scripts to the .git/hooks folder."
        group = "git hooks"
        outputs.upToDateWhen { false }
        from("$rootDir/scripts/pre-commit")
        into("$rootDir/.git/hooks/")
    }
    tasks.build {
        dependsOn("copyPreCommitHook")
    }
}
