package com.wnet.artivatic.data

import com.wnet.artivatic.data.api_model.PlaceModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("/v3/c4ab4c1c-9a55-4174-9ed2-cbbe0738eedf")
    fun GetDetails(): Observable<PlaceModel>
}