package com.davilav.wigilabstest.data.local.db.languages
import androidx.room.*
import com.davilav.wigilabstest.data.model.LanguageModel

@Dao
interface LanguageDao {

    @Insert
    suspend fun insertLanguage(language: Language)

    @Query("DELETE FROM language WHERE language = :language")
    suspend fun deleteLanguage(language: String)

    @Query("SELECT  * FROM language")
    suspend fun getLanguage(): List<LanguageModel>

    @Query("DELETE FROM language")
    suspend fun nuke()

}