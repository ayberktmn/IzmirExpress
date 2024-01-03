package com.ayberk.IzmirExpress.retrofit

import com.ayberk.IzmirExpress.model.Museums
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
}