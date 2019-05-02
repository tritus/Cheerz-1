package com.github.colinjeremie.cheerz.framework.di

import com.github.colinjeremie.cheerz.presentation.details.DetailsActivity
import com.github.colinjeremie.cheerz.presentation.details.DetailsPresenter
import com.github.colinjeremie.cheerz.presentation.main.MainActivity
import com.github.colinjeremie.cheerz.presentation.main.MainPresenter
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentersModule: Module by lazy {
    module {
        scope(named<MainActivity>()) {
            scoped { MainPresenter(useCase = get()) }
        }
        scope(named<DetailsActivity>()) {
            scoped { DetailsPresenter() }
        }
    }
}