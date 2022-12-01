package com.davilav.wigilabstest.data.local.db.movie

import android.graphics.Bitmap
import android.graphics.ColorSpace
import android.hardware.HardwareBuffer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.davilav.wigilabstest.data.model.MovieModel

@Entity
data class Movie (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id") val movie_id: Int,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "original_title") val original_title: String?,
    @ColumnInfo(name = "overview") val overview: String?,
    @ColumnInfo(name = "popularity") val popularity: Double?,
    @ColumnInfo(name = "poster_path") val poster_path: String?,
    @ColumnInfo(name = "release_date") val release_date: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "vote_average") val vote_average: Double?,
    @ColumnInfo(name = "vote_count") val vote_count: Int?,
    @ColumnInfo(name = "adult") val adult: Boolean?,
    @ColumnInfo(name = "backdrop_path") val backdrop_path: String?,
    @ColumnInfo(name = "original_language") val original_language: String?,
    @ColumnInfo(name = "video") val video: Boolean?,
    @ColumnInfo(name = "poster_img") val poster_img: String?,
)
