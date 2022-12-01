package com.davilav.wigilabstest.data.model

data class Page(
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results: List<MovieModel>?
)