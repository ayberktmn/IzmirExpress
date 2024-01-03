package com.ayberk.IzmirExpress.model

data class Museums(
    val kayit_sayisi: Int,
    val onemliyer: List<Onemliyer>,
    val sayfa_numarasi: Int,
    val sayfadaki_kayitsayisi: Int,
    val toplam_sayfa_sayisi: Int
)