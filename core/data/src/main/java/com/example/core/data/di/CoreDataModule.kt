package com.example.core.data.di

import com.example.core.data.auth.EncryptedSessionStorage
import com.example.core.data.networking.HttpClientFactory
import com.example.core.domain.SessionStorage
import io.ktor.client.HttpClient
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.TypeQualifier
import org.koin.dsl.module


object AuthInfoStoreQualifier :
    Qualifier by TypeQualifier(AuthInfoStoreQualifier::class)
val coreDataModule = module {
    single<HttpClient> {
        HttpClientFactory().build()
    }

    single<SessionStorage> {
        EncryptedSessionStorage(
            syncDataStore = get(AuthInfoStoreQualifier),
        )
    }
}