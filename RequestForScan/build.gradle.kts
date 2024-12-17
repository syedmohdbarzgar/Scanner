plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
//    id("com.jfrog.bintray")

}

//bintray {
//    user = project.findProperty("bintrayUser") ?: System.getenv("BINTRAY_USER")
//    key = project.findProperty("bintrayApiKey") ?: System.getenv("BINTRAY_API_KEY")
//    publications = listOf("mavenJava")
//    pkg {
//        repo = "maven"
//        name = "scanner-library"
//        userOrg = "your-bintray-org"  // سازمان خود را وارد کنید
//        licenses = listOf("MIT")
//        vcsUrl = "https://github.com/yourusername/Scanner.git"
//        version {
//            name = "1.0.0"
//            desc = "First release"
//            released = java.util.Date().toString()
//        }
//    }
//}
//
//publishing {
//    publications {
//        create<MavenPublication>("mavenJava") {
//            from(components["release"])
//            groupId = "com.example"
//            artifactId = "scanner-library"
//            version = "1.0.0"
//        }
//    }
//}

android {
    namespace = "github.syedmohdbarzgar.requestforscan"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}