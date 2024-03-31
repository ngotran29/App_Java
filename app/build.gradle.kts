import java.util.regex.Pattern.compile

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.nhp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nhp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation ("com.hbb20:ccp:2.5.3")
    implementation ("com.googlecode.libphonenumber:libphonenumber:8.12.36")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(fileTree(mapOf("dir" to "D:\\zalo", "include" to listOf("*.aar", "*.jar"))))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.navigation:navigation-fragment:2.5.1")
    implementation ("androidx.navigation:navigation-ui:2.5.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation ("com.google.android.material:material:1.3.0")


    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.20")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-database")

    //load img
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    //circle img
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    //zalopay
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
    implementation("commons-codec:commons-codec:1.14")
    implementation(fileTree(mapOf("dir" to "D:\\Zalopay", "include" to listOf("*.aar", "*.jar"))))
    implementation ("com.squareup.picasso:picasso:2.71828")

    compile ("com.google.firebase:firebase-storage:9.4.0")


}

