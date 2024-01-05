package com.ayberk.IzmirExpress.retrofit

import com.ayberk.IzmirExpress.model.Museums
import com.ayberk.IzmirExpress.model.Pharmacy
import com.ayberk.IzmirExpress.util.Resource
import javax.inject.Inject

class RetrofitRepository @Inject constructor(
    private val api:RetrofitInstance
) {
    suspend fun getMuseums():Resource<Museums>{
        val response = try {
            api.getMuseums()
        }catch (e:Exception){
            return Resource.Error("Museums Error")
        }
        return Resource.Success(response)
    }
    suspend fun getPharmacy():Resource<Pharmacy>{
        val response = try {
            api.getPharmacy()
        }catch (e:Exception){
            return Resource.Error("Pharmacy Error")
        }
        return Resource.Success(response)
    }
}