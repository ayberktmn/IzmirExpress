package com.ayberk.IzmirExpress.retrofit

import com.ayberk.IzmirExpress.model.Activitys
import com.ayberk.IzmirExpress.model.EmergencyCollect
import com.ayberk.IzmirExpress.model.Museums
import com.ayberk.IzmirExpress.model.Pharmacy
import com.ayberk.IzmirExpress.model.WaterProblem
import retrofit2.http.GET

interface RetrofitInstance {

    @GET("api/ibb/cbs/muzeler")
    suspend fun getMuseums(): Museums

    @GET("api/ibb/nobetcieczaneler")
    suspend fun getPharmacy(): Pharmacy

    @GET("api/izsu/arizakaynaklisukesintileri")
    suspend fun getWaterProblem(): WaterProblem

    @GET("api/ibb/cbs/afetaciltoplanmaalani")
    suspend fun getEmergencyCollect(): EmergencyCollect

    @GET("api/ibb/kultursanat/etkinlikler")
    suspend fun getActivitys(): Activitys
}