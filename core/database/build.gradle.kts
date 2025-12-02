plugins {
    alias(libs.plugins.runique.android.library)
}

android {
    namespace = "com.example.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)

    implementation(projects.core.domain)
}