plugins {
    id("org.jetbrains.kotlin.js") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.10"
}

group = "org.stvad"
version = "0.1"

repositories {
    maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
    mavenCentral()
    jcenter()
}


dependencies {
    val muirwikComponentVersion = "0.6.3"
    val wrapperVersion = "16.14.0-pre.125-kotlin-1.4.10"

    implementation(kotlin("stdlib-js"))

    //React, React DOM + Wrappers (chapter 3)
    implementation("org.jetbrains:kotlin-react:$wrapperVersion")
    implementation("org.jetbrains:kotlin-react-dom:$wrapperVersion")
    implementation(npm("react", "16.13.1"))
    implementation(npm("react-is", "16.13.1"))
    implementation(npm("react-dom", "16.13.1"))

    //Kotlin Styled (chapter 3)
    implementation("org.jetbrains:kotlin-styled:1.0.0-pre.115-kotlin-1.4.10")
    implementation(npm("styled-components", "4.4.0")) // https://github.com/JetBrains/kotlin-wrappers/issues/147
    // implementation(npm("styled-components", "~5.1.1"))
    implementation(npm("inline-style-prefixer", "~6.0.0"))

    //Video Player (chapter 7)
    implementation(npm("react-player", "~2.6.0"))

    //Share Buttons (chapter 7)
    implementation(npm("react-share", "~4.2.1"))
    // implementation(npm(File("/Users/sitalov/Dropbox/SoftwareEngineering/Projects/Roam/goodreads2roam")))
    implementation(npm("goodreads2roam", "~0.1.0"))

    //Coroutines (chapter 8)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")


    implementation("com.ccfraser.muirwik:muirwik-components:$muirwikComponentVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
}

kotlin {
    js {
        browser {
            webpackTask {
                cssSupport.enabled = true
            }

            runTask {
                cssSupport.enabled = true
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                    webpackConfig.cssSupport.enabled = true
                }
            }
        }
        binaries.executable()
    }
}
