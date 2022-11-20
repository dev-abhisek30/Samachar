package com.dev.abhisek30.samachar.core.data

import com.dev.abhisek30.samachar.core.utils.*
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

open class BaseRemoteDataSource @Inject constructor(
    private val gson: Gson
) {
    suspend fun <S, T> safeApiCall(domainType: Class<S>, apiCall: suspend () -> T): Resource<S> {
        try {
            val apiResponse = apiCall.invoke()
            return if(apiResponse is BaseResponse<*> ){
                if (apiResponse.error == null) {
                    val parsedResponse = gson.fromJson(gson.toJson(apiResponse.data), domainType)
                    Resource.Success(parsedResponse)
                } else {
                    Resource.Failure(
                        failureStatus = FailureStatus.API_FAIL,
                        message = apiResponse.error
                    )
                }
            } else {
                val parsedResponse = gson.fromJson(gson.toJson(apiResponse), domainType)
                Resource.Success(parsedResponse)
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    when {
                        throwable.code() == HTTP_ERROR_UNABLE_TO_PROCESS_REQUEST -> {
                            val jsonErrorObject =
                                JSONObject(throwable.response()?.errorBody()?.string() ?: "")
                            val apiResponse = jsonErrorObject.toString()

                            return Resource.Failure(
                                FailureStatus.API_FAIL,
                                throwable.code(),
                                apiResponse
                            )
                        }
                        throwable.code() == HTTP_ERROR_UNAUTHORIZED_REQUEST -> {
                            val jsonErrorObject =
                                JSONObject(throwable.response()?.errorBody()?.string() ?: "")
                            val apiResponse = jsonErrorObject.toString()

                            return Resource.Failure(
                                FailureStatus.API_FAIL,
                                throwable.code(),
                                apiResponse
                            )
                        }
                        throwable.code() == HTTP_ERROR_BAD_REQUEST -> {
                            val jsonErrorObject =
                                JSONObject(throwable.response()?.errorBody()?.string() ?: "")
                            val message = jsonErrorObject.getString("message") ?: ""
                            return Resource.Failure(
                                FailureStatus.API_FAIL,
                                throwable.code(),
                                message
                            )
                        }

                        else -> {
                            return if (throwable.response()?.errorBody()!!.charStream().readText()
                                    .isEmpty()
                            ) {
                                Resource.Failure(FailureStatus.API_FAIL, throwable.code())
                            } else {
                                try {
                                    val jsonErrorObject =
                                        JSONObject(
                                            throwable.response()?.errorBody()?.string()
                                                ?: ""
                                        )
                                    val apiResponse = jsonErrorObject.toString()

                                    Resource.Failure(
                                        FailureStatus.API_FAIL,
                                        throwable.code(),
                                        apiResponse
                                    )
                                } catch (ex: Throwable) {
                                    Resource.Failure(FailureStatus.API_FAIL, throwable.code())
                                }
                            }
                        }
                    }
                }

                is UnknownHostException -> {
                    return Resource.Failure(FailureStatus.NO_INTERNET)
                }

                is ConnectException -> {
                    return Resource.Failure(FailureStatus.NO_INTERNET)
                }

                is IOException -> {
                    return Resource.Failure(
                        FailureStatus.API_FAIL,
                        HTTP_ERROR_UNABLE_TO_PROCESS_REQUEST,
                        throwable.message
                    )
                }

                else -> {
                    return Resource.Failure(FailureStatus.OTHER)
                }
            }
        }
    }
}