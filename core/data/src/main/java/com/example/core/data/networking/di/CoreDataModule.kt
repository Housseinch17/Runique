package com.example.core.data.networking.di

import com.example.core.data.networking.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.dsl.module

val coreDataModule = module{
    single<HttpClient>{
        HttpClientFactory().build()
    }
}