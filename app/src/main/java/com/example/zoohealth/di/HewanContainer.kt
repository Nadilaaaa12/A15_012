package com.example.zoohealth.di

import com.example.zoohealth.repository.HewanRepository
import com.example.zoohealth.repository.KandangRepository
import com.example.zoohealth.repository.MonitoringRepository
import com.example.zoohealth.repository.NetworkHewanRepository
import com.example.zoohealth.repository.NetworkKandangRepository
import com.example.zoohealth.repository.NetworkMonitoringRepository
import com.example.zoohealth.repository.NetworkPetugasRepository
import com.example.zoohealth.repository.PetugasRepository
import com.example.zoohealth.service_api.HewanService
import com.example.zoohealth.service_api.KandangService
import com.example.zoohealth.service_api.MonitoringService
import com.example.zoohealth.service_api.PetugasService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainerHewan{
    val hewanRepository: HewanRepository

}

class HewanContainer: AppContainerHewan {


    private val baseUrl = "http://10.0.2.2:3000/api/hewan/"


    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val hewanService: HewanService by lazy {
        retrofit.create(HewanService::class.java)
    }

    override val hewanRepository: HewanRepository by lazy {
        NetworkHewanRepository(hewanService)
    }

    }

