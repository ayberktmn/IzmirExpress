package com.ayberk.IzmirExpress.model

data class EmergencyCollect(
    val kayit_sayisi: Int,
    val onemliyer: List<OnemliyerX>,
    val sayfa_numarasi: Int,
    val sayfadaki_kayitsayisi: Int,
    val toplam_sayfa_sayisi: Int
)