package com.github.colinjeremie.cheerz.framework.di

import com.github.colinjeremie.cheerz.framework.InMemoryMediaStorageSource
import com.github.colinjeremie.data.MediaStorageSource
import org.koin.core.module.Module
import org.koin.dsl.module

val storageModule: Module by lazy {
    module {
        single { InMemoryMediaStorageSource() as MediaStorageSource  }
    }
}