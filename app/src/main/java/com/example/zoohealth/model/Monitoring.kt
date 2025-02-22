package com.example.zoohealth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Monitoring(
    @SerialName("id_monitoring")
    val idMonitoring: Int,

    @SerialName("id_petugas")
    val idPetugas: Int,

    @SerialName("id_kandang")
    val idKandang: Int,

    @SerialName("tanggal_monitoring")
    val tanggalMonitoring: String,

    @SerialName("hewan_sakit")
    val hewanSakit: Int,

    @SerialName("hewan_sehat")
    val hewanSehat: Int,

    val status: String
)

@Serializable
data class MonitoringResponse(
    val status: Boolean,
    val message: String,
    val data: List<Monitoring>
)

@Serializable
data class MonitoringDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Monitoring
)
