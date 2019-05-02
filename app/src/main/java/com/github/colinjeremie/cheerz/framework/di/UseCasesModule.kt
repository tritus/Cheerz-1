package com.github.colinjeremie.cheerz.framework.di

import com.github.colinjeremie.usecases.GetPicturesUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val useCasesModule: Module by lazy {
    module {
        factory { GetPicturesUseCase(repository = get()) }
    }
}