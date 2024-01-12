package com.ayberk.IzmirExpress.model

data class ActivityDetails(
    val Aciklama: String,
    val AciklamaOzeti: String,
    val Adi: String,
    val EtkinlikMerkezi:EtkinlikMerkezi,
    val EtkinlikUrl: String,
    val KucukAfis: String,
    val Resim: String,
    val SeansListesi: List<SeansListesi>,
    val Tur: String
)