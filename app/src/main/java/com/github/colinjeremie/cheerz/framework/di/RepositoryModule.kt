package com.github.colinjeremie.cheerz.framework.di

import com.github.colinjeremie.data.repositories.MediaRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module by lazy {
    module {
        single {
            MediaRepository(
                networkRemoteDataSource = get(),
                localDataSource = get()
            )
        }
    }
}