package com.ayberk.IzmirExpress.model

data class ActivitysItem(
    val Adi: String,
    val BiletSatisLinki: String,
    val EtkinlikBaslamaTarihi: String,
    val EtkinlikBitisTarihi: String,
    val EtkinlikMerkezi: String,
    val EtkinlikUrl: String,
    val Id: Int,
    val KisaAciklama: String,
    val KucukAfis: String,
    val Resim: String,
    val Tur: String,
    val UcretsizMi: Boolean
)