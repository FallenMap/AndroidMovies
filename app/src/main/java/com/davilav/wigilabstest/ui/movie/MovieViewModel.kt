package com.davilav.wigilabstest.ui.movie

import com.davilav.wigilabstest.data.Result
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davilav.wigilabstest.data.remote.calladapter.NetworkResponse
import com.davilav.wigilabstest.data.repository.movie.MovieRepository
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import android.os.AsyncTask
import com.davilav.wigilabstest.data.local.db.languages.Language
import com.davilav.wigilabstest.data.local.db.movie.Movie
import kotlinx.coroutines.Dispatchers
import com.davilav.wigilabstest.data.model.MovieModel
import com.davilav.wigilabstest.utils.*


class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _dataResponseOnline: MutableLiveData<Pair<Boolean, Any?>> = MutableLiveData()
    val dataResponseOnline: LiveData<Pair<Boolean, Any?>> get() = _dataResponseOnline
    private val _dataResponseLanguages: MutableLiveData<Pair<Boolean, Any?>> = MutableLiveData()
    val dataResponseLanguages: LiveData<Pair<Boolean, Any?>> get() = _dataResponseLanguages
    private val _dataResponseOffline: MutableLiveData<Pair<Boolean, Any?>> = MutableLiveData()
    val dataResponseOffline: LiveData<Pair<Boolean, Any?>> get() = _dataResponseOffline
    private val _dataResponse: SingleLiveEvent<MovieState> = SingleLiveEvent()
    val dataResponse: SingleLiveEvent<MovieState> get() = _dataResponse

    private fun getApiKey(): String = Constants.API_KEY

    fun getMovie(language: String, context: Context) {
        viewModelScope.launch {
            if (hasNetworkAvailable(context)) {
                when (val result = repository.getMovie(getApiKey(), language)) {
                    is NetworkResponse.Success -> {
                        getPhotos(result.body.results)
                    }

                    else -> _dataResponse.value = MovieState.MovieFailure
                }
            } else {
                _dataResponse.value = MovieState.MovieFailure
            }
        }
    }

    fun downloadMovie(movie: Movie, context: Context) {
        viewModelScope.launch {
            if (hasNetworkAvailable(context)) {
                repository.insertMovies(movie)
            } else {
                getMovieOffline()
            }
        }
    }


    private fun getPhotos(movies: List<MovieModel>?) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMoviesPhotos(movies)
            when (response.status) {
                Result.Status.SUCCESS -> {
                    val success = response.data as List<MovieModel>
                    success.forEach { movies ->
                        movies.poster_img = RoundCornersBitmap(
                            movies.poster_img!!,
                            50
                        ).roundedCornerBitmap()
                    }
                    _dataResponse.postValue(MovieState.MovieSuccess(success))

                }
                Result.Status.ERROR -> {
                    _dataResponse.value = MovieState.MovieFailure
                }
            }
        }
    }

    fun getMovieOffline() {
        viewModelScope.launch {
            val response = repository.getMoviesDB()
            when (response.status) {
                Result.Status.SUCCESS -> {
                    _dataResponseOffline.value = Pair(true, response.data)
                }
                Result.Status.ERROR -> {
                    _dataResponseOffline.value = Pair(false, "Error")
                }
            }
        }
    }

    fun insertLanguages(id:Int,language: String) {
        viewModelScope.launch {
            val lan = Language(0,language=language)
            val response = repository.insertLanguages(lan)
        }
    }

    fun deleteLanguages(language: String) {
        viewModelScope.launch {
            val response = repository.deleteLanguages(language)
        }
    }

    fun nukeLanguages() {
        viewModelScope.launch {
            val response = repository.nukeLanguages()
        }
    }

    fun getLanguages() {
        viewModelScope.launch {
            val response = repository.getLanguages()
            when (response.status) {
                Result.Status.SUCCESS -> {
                    var queue = QueueImpl()
                    val x = response.data as List<Language>
                    for (i in x.indices){
                        queue.enqueue(x[x.size-i-1])
                    }
                    _dataResponseLanguages.value = Pair(true, queue)
                }
                Result.Status.ERROR -> {
                    _dataResponseLanguages.value = Pair(false, response.data)
                }
            }
        }
    }

    private fun hasNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        return (network?.isConnected) ?: false
    }

    fun isOnline(context: Context,server: String) {
        AsyncTask.execute {
            try {
                if (hasNetworkAvailable(context)) {
                    try {
                        val connection =
                            URL(server).openConnection() as HttpURLConnection
                        connection.setRequestProperty("User-Agent", "Test")
                        connection.setRequestProperty("Connection", "close")
                        connection.connectTimeout = 1500
                        connection.connect()
                        val myResponse = (connection.responseCode in 200..299)
                        Singleton.isOnline = myResponse
                        _dataResponseOnline.postValue(Pair(true, "Connection is successfully"))

                    } catch (e: IOException) {

                    }
                } else {
                    _dataResponseOnline.postValue(Pair(false, "No network available"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
