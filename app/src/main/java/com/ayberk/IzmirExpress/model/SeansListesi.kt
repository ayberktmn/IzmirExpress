package com.ayberk.IzmirExpress.model

data class SeansListesi(
    val BiletSatisAciklama: String,
    val BiletSatisLinki: String,
    val DolulukOranı: Int,
    val SatisaSunusTarihi: String,
    val SeansBaslangicTarihi: String,
    val SeansBitisTarihi: String,
    val UcretsizMi: Boolean
)