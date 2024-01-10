package com.ayberk.IzmirExpress.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.IzmirExpress.PharmacyItem
import com.ayberk.IzmirExpress.model.Activitys
import com.ayberk.IzmirExpress.model.ActivitysItem
import com.ayberk.IzmirExpress.model.EmergencyCollect
import com.ayberk.IzmirExpress.model.Onemliyer
import com.ayberk.IzmirExpress.model.OnemliyerX
import com.ayberk.IzmirExpress.model.PharmacyItem
import com.ayberk.IzmirExpress.model.WaterProblem
import com.ayberk.IzmirExpress.model.WaterProblemItem
import com.ayberk.IzmirExpress.retrofit.RetrofitRepository
import com.ayberk.IzmirExpress.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyViewModel @Inject constructor(
    private val repository: RetrofitRepository
) : ViewModel() {
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var emergencyCollectList = mutableStateOf<List<OnemliyerX>>(listOf())

    init {
       loadEmergencyCollect()
    }

    fun loadEmergencyCollect(){
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getEmergencyCollect()
            when(result){
                is Resource.Success -> {
                    val Emergencycollectsitems = result.data?.onemliyer?.map { item ->
                        OnemliyerX(
                            item.ACIKLAMA,
                            item.ADI,
                            item.BOYLAM,
                            item.ENLEM,
                            item.ILCE,
                            item.ILCEID,
                            item.KAPINO,
                            item.MAHALLE,
                            item.YOL
                        )
                    }?: emptyList()
                    errorMessage.value = ""
                    isLoading.value = false
                    emergencyCollectList.value = Emergencycollectsitems
                }
                is Resource.Error -> {
                    errorMessage.value = result.message ?: "Bir hata oluştu."
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    errorMessage.value = ""
                }
                else -> {}
            }
        }
    }
}

