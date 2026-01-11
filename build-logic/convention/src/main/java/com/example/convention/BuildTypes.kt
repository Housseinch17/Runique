package com.example.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        val apiKey = gradleLocalProperties(rootDir, rootProject.providers).getProperty("API_KEY")
        val baseUrl = gradleLocalProperties(rootDir, rootProject.providers).getProperty("BASE_URL")
        val registerRoute =
            gradleLocalProperties(rootDir, rootProject.providers).getProperty("REGISTER_ROUTE")
        val loginRoute =
            gradleLocalProperties(rootDir, rootProject.providers).getProperty("LOGIN_ROUTE")
        val accessTokenRoute =
            gradleLocalProperties(rootDir, rootProject.providers).getProperty("ACCESS_TOKEN_ROUTE")
        val refreshTokenRoute =
            gradleLocalProperties(rootDir, rootProject.providers).getProperty("REFRESH_TOKEN_ROUTE")
        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(
                                apiKey = apiKey,
                                baseUrl = baseUrl,
                                registerRoute = registerRoute,
                                loginRoute = loginRoute,
                                accessTokenRoute = accessTokenRoute,
                                refreshTokenRoute = refreshTokenRoute,
                            )
                        }
                        release {
                            configureReleaseBuildType(
                                commonExtension = commonExtension,
                                apiKey = apiKey,
                                baseUrl = baseUrl,
                                registerRoute = registerRoute,
                                loginRoute = loginRoute,
                                accessTokenRoute = accessTokenRoute,
                                refreshTokenRoute = refreshTokenRoute
                            )
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(
                                apiKey = apiKey, baseUrl = baseUrl,
                                registerRoute = registerRoute,
                                loginRoute = loginRoute,
                                accessTokenRoute = accessTokenRoute,
                                refreshTokenRoute = refreshTokenRoute
                            )
                        }
                        release {
                            configureReleaseBuildType(
                                commonExtension = commonExtension,
                                apiKey = apiKey,
                                baseUrl = baseUrl,
                                registerRoute = registerRoute,
                                loginRoute = loginRoute,
                                accessTokenRoute = accessTokenRoute,
                                refreshTokenRoute = refreshTokenRoute
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType(
    apiKey: String, baseUrl: String, registerRoute: String, loginRoute: String,
    accessTokenRoute: String, refreshTokenRoute: String
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
    buildConfigField("String", "REGISTER_ROUTE", "\"$registerRoute\"")
    buildConfigField("String", "LOGIN_ROUTE", "\"$loginRoute\"")
    buildConfigField("String", "ACCESS_TOKEN_ROUTE", "\"$accessTokenRoute\"")
    buildConfigField("String", "REFRESH_TOKEN_ROUTE", "\"$refreshTokenRoute\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String, baseUrl: String, registerRoute: String, loginRoute: String,
    accessTokenRoute: String, refreshTokenRoute: String
) {
    buildConfigField("String", "API_KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
    buildConfigField("String", "REGISTER_ROUTE", "\"$registerRoute\"")
    buildConfigField("String", "LOGIN_ROUTE", "\"$loginRoute\"")
    buildConfigField("String", "ACCESS_TOKEN_ROUTE", "\"$accessTokenRoute\"")
    buildConfigField("String", "REFRESH_TOKEN_ROUTE", "\"$refreshTokenRoute\"")

    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}