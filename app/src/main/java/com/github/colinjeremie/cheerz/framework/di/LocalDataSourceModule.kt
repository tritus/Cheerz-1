package com.github.colinjeremie.cheerz.framework.di

import com.github.colinjeremie.cheerz.framework.datasources.MediaLocalDataSourceInMemoryImpl
import com.github.colinjeremie.data.datasources.MediaLocalDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

val localDataSourceModule: Module by lazy {
    module {
        single<MediaLocalDataSource> { MediaLocalDataSourceInMemoryImpl() }
    }
}