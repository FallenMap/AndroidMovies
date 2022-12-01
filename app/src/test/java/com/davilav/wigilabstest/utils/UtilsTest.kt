package com.davilav.wigilabstest.utils

import com.davilav.wigilabstest.data.local.db.movie.Movie
import com.davilav.wigilabstest.data.model.MovieModel
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class UtilsTest {

    @Test
    fun toMovie() {
        val mockMovie = Movie(
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
        val mockMovieModel = MovieModel(
            false,
            "/cinER0ESG0eJ49kXlExM0MEWGxW.jpg",
            1,
            "russian",
            "La vaca lola,con cabeza, cachos y cola",
            "esta vaca se llama lola, tiene cabeza, cachos y alta cola",
            69.5,
            "/vclShucpUmPhdAOmKgf3B3Z4POD.jpg",
            null,
            "2021-07-21",
            "Lola The Cow 1: The ascension",
            false,
            200.5,
            5,
            null
        )
        assertEquals(Utils().toMovie(mockMovieModel),mockMovie)
    }

    @Test
    fun toMovieModel() {
        val mockMovie = Movie(
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
        val mockMovieModel = MovieModel(
            false,
            "/cinER0ESG0eJ49kXlExM0MEWGxW.jpg",
            1,
            "russian",
            "La vaca lola,con cabeza, cachos y cola",
            "esta vaca se llama lola, tiene cabeza, cachos y alta cola",
            69.5,
            "/vclShucpUmPhdAOmKgf3B3Z4POD.jpg",
            null,
            "2021-07-21",
            "Lola The Cow 1: The ascension",
            false,
            200.5,
            5,
            listOf(1,2)
        )
        assertEquals(Utils().toMovieModel(mockMovie),mockMovieModel)
    }
}