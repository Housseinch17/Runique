package com.example.run.location.di

import com.example.run.location.AndroidLocationObserver
import com.example.run.domain.LocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module{
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}