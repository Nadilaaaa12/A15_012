package com.example.zoohealth.di


import com.example.zoohealth.repository.MonitoringRepository
import com.example.zoohealth.repository.NetworkMonitoringRepository
import com.example.zoohealth.service_api.MonitoringService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainerMonitoring{
    val monitoringRepository: MonitoringRepository
}

class MonitoringContainer: AppContainerMonitoring{
    private val baseUrl = "http://10.0.2.2:3000/api/monitoring/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val monitoringService: MonitoringService by lazy {
        retrofit.create(MonitoringService::class.java)
    }

    override val monitoringRepository: MonitoringRepository by lazy {
        NetworkMonitoringRepository(monitoringService)
    }
}
