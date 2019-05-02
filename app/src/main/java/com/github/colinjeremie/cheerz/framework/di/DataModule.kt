package com.github.colinjeremie.cheerz.framework.di

import com.google.gson.GsonBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

val dataModule: Module by lazy {
    module {
        single { GsonBuilder().setDateFormat("yyyy-MM-dd").create() }
    }
}