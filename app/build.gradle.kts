plugins {
    alias(libs.plugins.android.application)
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.mustafa.dishdash"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mustafa.dishdash"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.lottie)

    ///Glide
    implementation(libs.glide)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)


    implementation(libs.swiperefreshlayout)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    //youtube player
    implementation(libs.core)


    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.com.google.firebase.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.google.services)
    implementation(libs.google.firebase.firestore)


    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    
    implementation(libs.carouselrecyclerview)

    //rxjava
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("io.reactivex.rxjava3:rxjava:3.1.5")


    //retrofit rxjava
    implementation ("com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0")

    //room rxjava
    implementation ("androidx.room:room-rxjava3:2.6.1")
}