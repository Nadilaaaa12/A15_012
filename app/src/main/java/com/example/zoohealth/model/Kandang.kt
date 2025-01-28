package com.example.zoohealth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllKandangResponse(
    val status: Boolean,
    val message: String,
    val data: List<Kandang>
)

@Serializable
data class KandangDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Kandang
)

@Serializable
data class Kandang(
    @SerialName("id_kandang")
    val idKandang: Int,

    @SerialName("id_hewan")
    val idHewan: Int,

    val kapasitas: Int,
    val lokasi: String,
)
