package com.github.colinjeremie.cheerz.framework.di

import com.github.colinjeremie.cheerz.presentation.details.DetailsPresenter
import com.github.colinjeremie.cheerz.presentation.main.MainPresenter
import org.koin.core.module.Module
import org.koin.dsl.module

val presentersModule: Module by lazy {
    module {
        factory { MainPresenter(useCase = get()) }
        factory { DetailsPresenter() }
    }
}