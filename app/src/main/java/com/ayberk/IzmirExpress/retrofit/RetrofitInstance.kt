package com.ayberk.IzmirExpress.retrofit

import com.ayberk.IzmirExpress.model.Museums
import com.ayberk.IzmirExpress.model.Pharmacy
import retrofit2.http.GET

interface RetrofitInstance {

    @GET("api/ibb/cbs/muzeler")
    suspend fun getMuseums(): Museums

    @GET("api/ibb/nobetcieczaneler")
    suspend fun getPharmacy(): Pharmacy

}