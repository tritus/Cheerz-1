package com.github.colinjeremie.cheerz.framework.di

import com.github.colinjeremie.data.repositories.MediaRepositoryImpl
import com.github.colinjeremie.domain.repositories.MediaRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module by lazy {
    module {
        single<MediaRepository> {
            MediaRepositoryImpl(
                networkRemoteDataSource = get(),
                localDataSource = get()
            )
        }
    }
}