package com.davilav.wigilabstest.data.repository.movie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.davilav.wigilabstest.data.Result
import com.davilav.wigilabstest.data.local.db.LocalDataBase
import com.davilav.wigilabstest.data.local.db.languages.Language
import com.davilav.wigilabstest.data.local.db.movie.Movie
import com.davilav.wigilabstest.data.model.ErrorResponse
import com.davilav.wigilabstest.data.model.MovieModel
import com.davilav.wigilabstest.data.model.Page
import com.davilav.wigilabstest.data.remote.ApiInterface
import com.davilav.wigilabstest.data.remote.calladapter.NetworkResponse
import com.davilav.wigilabstest.utils.Constants
import com.squareup.picasso.Picasso
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class MovieRepositoryImpl(
    private val apiClient: ApiInterface,
    private val database: LocalDataBase
) : MovieRepository {
    override suspend fun getMovie(api_key: String, language: String): NetworkResponse<Page, ErrorResponse> =
        apiClient.getMovies(api_key, language)

    override suspend fun getMoviesDB(): Result<Any> {
        val data = database.getMovieDao().getMovie()
        return if(data.isNotEmpty()){
            Result.success(data)
        } else {
            Result.error("Error")
        }
    }

    override suspend fun getLanguages(): Result<Any> {
        val data = database.getLanguageDao().getLanguage()
        return if(data.isNotEmpty()){
            Result.success(data)
        } else {
            Result.error("Data empty")
        }
    }

    override suspend fun insertLanguages(language: Language){
        database.getLanguageDao().insertLanguage(language)
    }

    override suspend fun deleteLanguages(language: String) {
        database.getLanguageDao().deleteLanguage(language)
    }



    override suspend fun compareMovies(movie: Movie) {
        val data = database.getMovieDao().getMovie()
        if (data.isEmpty() || !data.contains(movie)) {
            insertMovies(movie)
        }
    }

    override suspend fun insertMovies(movie: Movie) {
        database.getMovieDao().insertMovie(movie)
    }

    override suspend fun nukeLanguages() {
        database.getLanguageDao().nuke()
    }

    override suspend fun getMoviesPhotos(movies: List<MovieModel>?): Result<Any> {
        val list: MutableList<Bitmap> = ArrayList()
        movies?.forEach { movie ->
            try {
            val url = URL(Constants.BASE_IMAGE_URL+movie.poster_path)
            val connection =
                url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
           list.add(BitmapFactory.decodeStream(input))
        } catch (e: IOException) {
            null
        }
      }
        return if (list.isNotEmpty()){
            for (i in list.indices) {
                movies?.get(i)!!.poster_img  = list[i]
            }
            Result.success(movies)
        }
        else{
            Result.error("Error")
        }

    }

}
