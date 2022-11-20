package com.dev.abhisek30.samachar.core.data

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data") val data: T?,
    @SerializedName("error") val error: String?
)
