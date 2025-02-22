package com.example.zoohealth.service_api

import com.example.zoohealth.model.Monitoring
import com.example.zoohealth.model.MonitoringResponse
import com.example.zoohealth.model.MonitoringDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MonitoringService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("store")
    suspend fun insertMonitoring(@Body monitoring: Monitoring)

    @GET(".")
    suspend fun getAllMonitoring(): MonitoringResponse

    @GET("{id_monitoring}")
    suspend fun getMonitoringById(@Path("id_monitoring") idMonitoring: String): MonitoringDetailResponse

    @PUT("{id_monitoring}")
    suspend fun updateMonitoring(@Path("id_monitoring") idMonitoring: String, @Body monitoring: Monitoring)

    @DELETE("{id_monitoring}")
    suspend fun deleteMonitoring(@Path("id_monitoring") idMonitoring: String): Response<Void>
}
