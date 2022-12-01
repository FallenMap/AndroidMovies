package com.davilav.wigilabstest.di

import android.app.Application
import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.davilav.wigilabstest.data.local.db.LocalDataBase
import com.davilav.wigilabstest.utils.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    single { provideRoom(androidApplication()) }
}

private const val DATABASE_NAME = Constants.DATABASE_NAME

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS Movie(movie_id INTEGER PRIMARY KEY NOT NULL, original_title TEXT, overview TEXT, popularity REAL, poster_path TEXT, release_date TEXT, title TEXT, vote_average REAL, vote_count INTEGER, adult INTEGER, backdrop_path TEXT, original_language TEXT, video INTEGER, poster_img TEXT)"
        )
    }
}
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS Language(id INTEGER PRIMARY KEY NOT NULL, language TEXT)"
        )
    }
}

fun provideRoom(application: Application): LocalDataBase = Room.databaseBuilder(
    application.applicationContext,
    LocalDataBase::class.java,
    DATABASE_NAME
).addMigrations(
    MIGRATION_1_2,
    MIGRATION_2_3
).build()
