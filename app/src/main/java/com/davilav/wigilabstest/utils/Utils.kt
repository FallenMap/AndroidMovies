package com.davilav.wigilabstest.utils
import com.davilav.wigilabstest.data.local.db.Converters
import com.davilav.wigilabstest.data.local.db.movie.Movie
import com.davilav.wigilabstest.data.model.MovieModel

class Utils {
    fun toMovie(movie: MovieModel): Movie {
        return Movie(
            0,
            movie.id,
            movie.original_title,
            movie.overview,
            movie.popularity,
            movie.poster_path,
            movie.release_date,
            movie.title,
            movie.vote_average,
            movie.vote_count,
            movie.adult,
            movie.backdrop_path.toString(),
            movie.original_language,
            movie.video,
            movie.poster_path,
        )
    }

    fun toMovieModel(movie: Movie): MovieModel {
        return MovieModel(
            movie.adult,
            movie.backdrop_path,
            movie.movie_id,
            movie.original_language,
            movie.original_title,
            movie.overview,
            movie.popularity,
            movie.poster_path,
            Converters().stringToBitMap(movie.poster_img),
            movie.release_date,
            false,
            movie.title,
            movie.video,
            movie.vote_average,
            movie.vote_count,
            listOf(1,2)
        )
    }
}