package com.davilav.wigilabstest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.davilav.wigilabstest.data.local.db.movie.Movie
import com.davilav.wigilabstest.data.local.db.movie.MovieDao
import com.davilav.wigilabstest.data.local.db.languages.Language
import com.davilav.wigilabstest.data.local.db.languages.LanguageDao

@Database(
    entities = [Movie::class,Language::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract  fun  getLanguageDao(): LanguageDao
}