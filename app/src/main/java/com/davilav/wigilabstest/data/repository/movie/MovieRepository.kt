package com.davilav.wigilabstest.data.repository.movie

import android.graphics.Bitmap
import com.davilav.wigilabstest.data.Result
import com.davilav.wigilabstest.data.local.db.languages.Language
import com.davilav.wigilabstest.data.local.db.movie.Movie
import com.davilav.wigilabstest.data.model.ErrorResponse
import com.davilav.wigilabstest.data.model.MovieModel
import com.davilav.wigilabstest.data.model.Page
import com.davilav.wigilabstest.data.remote.calladapter.NetworkResponse

interface MovieRepository {
    suspend fun getMovie(
        api_key: String,
        language: String
    ): NetworkResponse<Page, ErrorResponse>

    suspend fun getMoviesDB(): Result<Any>
    suspend fun getLanguages(): Result<Any>
    suspend fun insertLanguages(language: Language)
    suspend fun deleteLanguages(language: String)
    suspend fun compareMovies(movie: Movie)
    suspend fun insertMovies(movie: Movie)
    suspend fun nukeLanguages()
    suspend fun getMoviesPhotos(movies: List<MovieModel>?): Result<Any>
}