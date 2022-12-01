package com.davilav.wigilabstest.data.local.db.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieDao {

    @Insert
    suspend fun insertMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Query("SELECT  * FROM movie")
    suspend fun getMovie(): List<Movie>


}