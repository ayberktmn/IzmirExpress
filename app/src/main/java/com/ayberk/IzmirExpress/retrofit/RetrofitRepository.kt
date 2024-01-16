package com.ayberk.IzmirExpress.retrofit

import com.ayberk.IzmirExpress.model.ActivityDetails
import com.ayberk.IzmirExpress.model.Activitys
import com.ayberk.IzmirExpress.model.EmergencyCollect
import com.ayberk.IzmirExpress.model.Museums
import com.ayberk.IzmirExpress.model.Pharmacy
import com.ayberk.IzmirExpress.model.WaterProblem
import com.ayberk.IzmirExpress.util.Resource
import javax.inject.Inject

class RetrofitRepository @Inject constructor(
    private val api: RetrofitInstance
) {
    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(apiCall.invoke())
        } catch (e: Exception) {
            Resource.Error("Error occurred")
        }
    }

    suspend fun getMuseums(): Resource<Museums> =
        safeApiCall { api.getMuseums() }

    suspend fun getPharmacy(): Resource<Pharmacy> =
        safeApiCall { api.getPharmacy() }

    suspend fun getWaterProblem(): Resource<WaterProblem> =
        safeApiCall { api.getWaterProblem() }

    suspend fun getEmergencyCollect(): Resource<EmergencyCollect> =
        safeApiCall { api.getEmergencyCollect() }

    suspend fun getActivitys(): Resource<Activitys> =
        safeApiCall { api.getActivitys() }

    suspend fun getActivityDetails(Id: Int): Resource<ActivityDetails> =
        safeApiCall { api.getActivitysDetails(Id) }
}