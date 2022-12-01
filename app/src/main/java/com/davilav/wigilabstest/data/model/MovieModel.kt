package com.davilav.wigilabstest.data.model

import android.graphics.Bitmap
import com.davilav.wigilabstest.data.local.db.movie.Movie
import java.io.Serializable

data class MovieModel(
    val adult: Boolean?,
    val backdrop_path: String?,
    val id: Int,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    var poster_path: String?,
    var poster_img: Bitmap?,
    val release_date: String?,
    val favorite: Boolean,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?,
    val genre_ids: List<Int>?,
) : Serializable

