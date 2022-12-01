package com.davilav.wigilabstest.ui.movie

import com.davilav.wigilabstest.data.model.MovieModel

sealed class MovieState {
    data class Loading(var loading : Boolean) : MovieState()
    data class MovieSuccess(var movies: List<MovieModel>?): MovieState()
    object MovieFailure: MovieState()
}