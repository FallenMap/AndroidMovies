package com.davilav.wigilabstest.ui.movie

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.davilav.wigilabstest.data.Result
import com.davilav.wigilabstest.data.local.db.LocalDataBase
import com.davilav.wigilabstest.data.local.db.movie.Movie
import com.davilav.wigilabstest.data.local.db.movie.MovieDao
import com.davilav.wigilabstest.data.repository.movie.MovieRepositoryImpl
import com.davilav.wigilabstest.utils.TestCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MovieViewModelTest : TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var db: LocalDataBase
    private lateinit var dao: MovieDao
    private val context: Context = ApplicationProvider.getApplicationContext<Context>()

    @Mock
    var movieRepositoryImpl: MovieRepositoryImpl = Mockito.mock(MovieRepositoryImpl::class.java)
    @Mock
    var database : LocalDataBase = Mockito.mock(LocalDataBase::class.java)

    @Before
    fun onSetup() {
        MockitoAnnotations.initMocks(this)
        movieViewModel = MovieViewModel(movieRepositoryImpl)
        db = Room.inMemoryDatabaseBuilder(context, LocalDataBase::class.java).build()
        dao = db.getMovieDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testGetMovie() {
        testCoroutineRule.runBlockingTest {
            val invalidMockLanguage = "asasa"
            val invalidExpected = MovieState.MovieFailure

            //when
            movieViewModel.isOnline(context,invalidMockLanguage)

            //then
            assertEquals(movieViewModel.dataResponse.value,invalidExpected)
        }
    }

    @Test
    fun testDownloadMovie() {
        testCoroutineRule.runBlockingTest {
            val movie = Movie(
                1,
                1,
                "La vaca lola,con cabeza, cachos y cola",
                "esta vaca se llama lola, tiene cabeza, cachos y alta cola",
                69.5,
                "/vclShucpUmPhdAOmKgf3B3Z4POD.jpg",
                "2021-07-21",
                "Lola The Cow 1: The ascension",
                200.5,
                5,
                false,
                "/cinER0ESG0eJ49kXlExM0MEWGxW.jpg",
                "russian",
                false,
                null
            )
            dao.insertMovie(movie)
            val movies = dao.getMovie()
            assertEquals(movies.contains(movie),true)
        }
    }

    @Test
    fun testGetMovieOffline() {
        testCoroutineRule.runBlockingTest {
            val data = database.getMovieDao().getMovie()
            Mockito.`when`(movieRepositoryImpl.getMoviesDB()).thenReturn(Result.success(data))
            val validExpected = Pair(true, null)
            val invalidExpected = Pair(false, "Error")

            //when
            movieViewModel.getMovieOffline()

            //then
            assertEquals(movieViewModel.getMovieOffline(),validExpected)

        }


    }

    @Test
    fun testIsOnline() {
        testCoroutineRule.runBlockingTest {
            val validMockUrl = "https://meet.google.com/"
            val invalidMockUrl = "www.laplsap.com"
            val validExpected = Pair(true, "Connection is successfully")
            val invalidExpected = Pair(false, "No network available")

            //when
            movieViewModel.isOnline(context,validMockUrl)

            //then
            assertEquals(movieViewModel.dataResponseOnline.value,validExpected)

        }
    }

}