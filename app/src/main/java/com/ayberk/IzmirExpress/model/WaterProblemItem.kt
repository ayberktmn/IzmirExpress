package com.ayberk.IzmirExpress.model

data class WaterProblemItem(
    val Aciklama: String,
    val ArizaDurumu: String,
    val ArizaGiderilmeTarihi: String,
    val ArizaID: Int,
    val ArizaTipID: Int,
    val Birim: String,
    val GuncellemeTarihi: String,
    val IlceAdi: String,
    val IlceID: Int,
    val KayitTarihi: String,
    val KesintiSuresi: String,
    val KesintiTarihi: String,
    val MahalleID: List<Int>,
    val Mahalleler: String,
    val Ongoru: String,
    val Tip: String
)