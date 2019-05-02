package com.github.colinjeremie.cheerz.framework.di

import com.github.colinjeremie.data.MediaRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module by lazy {
    module {
        single { MediaRepository(networkSource = get(), storageSource = get()) }
    }
}