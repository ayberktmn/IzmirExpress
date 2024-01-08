package com.ayberk.IzmirExpress.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayberk.IzmirExpress.PharmacyItem
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
class DataViewModel @Inject constructor(
    private val repository: RetrofitRepository
) : ViewModel() {
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var museumsList = mutableStateOf<List<Onemliyer>>(listOf())
    var pharmacyList = mutableStateOf<List<PharmacyItem>>(listOf())
    var waterProblemList = mutableStateOf<List<WaterProblemItem>>(listOf())
    var emergencyCollectList = mutableStateOf<List<OnemliyerX>>(listOf())

    init {
        loadMuseums()
        loadPharmacy()
        loadWaterProblem()
        loadEmergencyCollect()
    }

    fun loadMuseums() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getMuseums()
            when (result) {
                is Resource.Success -> {
                    val museumItems = result.data?.onemliyer?.map { item ->
                        Onemliyer(
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
                    } ?: emptyList()

                    errorMessage.value = ""
                    isLoading.value = false
                    museumsList.value = museumItems
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

    fun loadPharmacy() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPharmacy()
            when (result) {
                is Resource.Success -> {
                    val pharmacyItems = result.data?.map { item ->
                        PharmacyItem(
                            item.Adi,
                            item.Adres,
                            item.Bolge,
                            item.BolgeAciklama,
                            item.BolgeId,
                            item.EczaneId,
                            item.IlceId,
                            item.LokasyonX,
                            item.LokasyonY,
                            item.Tarih,
                            item.Telefon,
                        )
                    } ?: emptyList()

                    errorMessage.value = ""
                    isLoading.value = false
                    pharmacyList.value = pharmacyItems
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

    fun loadWaterProblem(){
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getWaterProblem()
            when(result){
                is Resource.Success -> {
                    val WaterProblemsitems = result.data?.map { item ->
                        WaterProblemItem(
                    item.Aciklama,
                    item.ArizaDurumu,
                    item.ArizaGiderilmeTarihi,
                    item.ArizaID,
                    item.ArizaTipID,
                    item.Birim,
                    item.GuncellemeTarihi,
                    item.IlceAdi,
                    item.IlceID,
                    item.KayitTarihi,
                    item.KesintiSuresi,
                    item.KesintiTarihi,
                    item.MahalleID,
                    item.Mahalleler,
                    item.Ongoru,
                    item.Tip,
                        )
                    }?: emptyList()
                    errorMessage.value = ""
                    isLoading.value = false
                    waterProblemList.value = WaterProblemsitems
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

