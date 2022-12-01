package com.davilav.wigilabstest.di

import com.davilav.wigilabstest.data.repository.movie.MovieRepository
import com.davilav.wigilabstest.data.repository.movie.MovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
}
