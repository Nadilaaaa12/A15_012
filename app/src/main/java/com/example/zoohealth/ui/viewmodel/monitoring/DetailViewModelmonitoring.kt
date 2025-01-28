package com.example.zoohealth.ui.viewmodelmonitoring

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zoohealth.model.Kandang
import com.example.zoohealth.model.Monitoring
import com.example.zoohealth.repository.MonitoringRepository
import com.example.zoohealth.ui.view.monitoring.DestinasiDetailMonitoring
import com.example.zoohealth.ui.viewmodelkandang.DetailKandangUiState
import com.example.zoohealth.ui.viewmodelkandang.InsertUiEventKandang
import kotlinx.coroutines.launch

class DetailMonitoringViewModel(
    savedStateHandle: SavedStateHandle,
    private val monitoringRepository: MonitoringRepository
) : ViewModel() {
    private val idMonitoring: String = checkNotNull(savedStateHandle[DestinasiDetailMonitoring.IDMONITORING])

    var detailUiState: DetailMonitoringUiState by mutableStateOf(DetailMonitoringUiState())
        private set

    init {
        getMonitoringById()
    }

    private fun getMonitoringById() {
        viewModelScope.launch {
            detailUiState = DetailMonitoringUiState(isLoading = true)
            try {
                val result = monitoringRepository.getMonitoringById(idMonitoring).data
                detailUiState = DetailMonitoringUiState(
                    detailUiEvent = result.toDetailUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailUiState = DetailMonitoringUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown Error"
                )
            }
        }
    }
    fun deleteMonitoring() {
        viewModelScope.launch {
            detailUiState = DetailMonitoringUiState(isLoading = true)
            try {
                monitoringRepository.deleteMonitoring(idMonitoring)
                detailUiState = DetailMonitoringUiState(isLoading = false)
            } catch (e: Exception) {
                detailUiState = DetailMonitoringUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown Error"
                )
            }
        }
    }
}

data class DetailMonitoringUiState(
    val detailUiEvent: InsertUiEventMonitoring = InsertUiEventMonitoring(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == InsertUiEventMonitoring()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEventMonitoring()
}



fun Monitoring.toDetailUiEvent(): InsertUiEventMonitoring {
    return InsertUiEventMonitoring(
        idMonitoring = idMonitoring.toString(),
        idPetugas = idPetugas.toString(),
        idKandang = idKandang.toString(),
        tanggalMonitoring = tanggalMonitoring,
        hewanSakit = hewanSakit.toString(),
        hewanSehat = hewanSehat.toString(),
        status = status
    )
}


data class InsertUiEventMonitoring(
    val idMonitoring: String = "",
    val idPetugas: String = "",
    val idKandang: String = "",
    val tanggalMonitoring: String = "",
    val hewanSakit: String = "",
    val hewanSehat: String = "",
    val status: String = ""
)
