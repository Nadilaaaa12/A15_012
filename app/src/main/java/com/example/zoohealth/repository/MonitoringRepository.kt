package com.example.zoohealth.repository

import com.example.zoohealth.model.Monitoring
import com.example.zoohealth.model.MonitoringResponse
import com.example.zoohealth.model.MonitoringDetailResponse
import com.example.zoohealth.service_api.MonitoringService
import java.io.IOException

interface MonitoringRepository {
    suspend fun insertMonitoring(monitoring: Monitoring)

    suspend fun getMonitoring(): MonitoringResponse

    suspend fun updateMonitoring(idMonitoring: String, monitoring: Monitoring)

    suspend fun deleteMonitoring(idMonitoring: String)

    suspend fun getMonitoringById(idMonitoring: String): MonitoringDetailResponse
}

class NetworkMonitoringRepository(
    private val monitoringApiService: MonitoringService
): MonitoringRepository {
    override suspend fun insertMonitoring(monitoring: Monitoring) {
        monitoringApiService.insertMonitoring(monitoring)
    }

    override suspend fun updateMonitoring(idMonitoring: String, monitoring: Monitoring) {
        monitoringApiService.updateMonitoring(idMonitoring, monitoring)
    }
    override suspend fun deleteMonitoring(idMonitoring: String) {
        try {
            val response = monitoringApiService.deleteMonitoring(idMonitoring)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Kandang. HTTP Status code: ${response.code()}"
                )
            } else {
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMonitoring(): MonitoringResponse =
        monitoringApiService.getAllMonitoring()

    override suspend fun getMonitoringById(idMonitoring: String): MonitoringDetailResponse {
        return monitoringApiService.getMonitoringById(idMonitoring)
    }
}
