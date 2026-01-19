package com.example.runique.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.core.data.auth.AuthInfoSerializable
import com.example.core.data.auth.AuthInfoSerializer
import com.example.core.data.di.AuthInfoStoreQualifier
import com.example.runique.MainViewModel
import com.example.runique.RuniqueApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val Context.authInfoDataStore: DataStore<AuthInfoSerializable?> by dataStore(
    fileName = "auth_info.pb",
    serializer = AuthInfoSerializer
)

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as RuniqueApp).applicationScope
    }

    single<DataStore<AuthInfoSerializable?>>(AuthInfoStoreQualifier) {
        //get applicationContext from koin
        get<Context>().authInfoDataStore
    }

    viewModelOf(::MainViewModel)
}