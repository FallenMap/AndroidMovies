package com.davilav.wigilabstest.data.model

import com.google.gson.annotations.SerializedName


data class ErrorResponse(
    @SerializedName("Error") val error: Int? = null
)