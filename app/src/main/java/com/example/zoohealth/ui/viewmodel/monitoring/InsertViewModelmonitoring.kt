package com.example.zoohealth.ui.viewmodelmonitoring

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zoohealth.model.Kandang
import com.example.zoohealth.model.Monitoring
import com.example.zoohealth.repository.MonitoringRepository
import com.example.zoohealth.ui.viewmodelkandang.InsertKandangUiState
import com.example.zoohealth.ui.viewmodelkandang.toInsertKandangEvent
import kotlinx.coroutines.launch

class InsertMonitoringViewModel(private val monitoringRepository: MonitoringRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertMonitoringUiState())
        private set

    fun updateInsertMonitoringState(insertMonitoringEvent: InsertMonitoringEvent) {
        uiState = InsertMonitoringUiState(insertMonitoringEvent = insertMonitoringEvent)
    }

    fun insertMonitoring() {
        viewModelScope.launch {
            try {
                monitoringRepository.insertMonitoring(uiState.insertMonitoringEvent.toMonitoring()) // Use function specific to insert
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertMonitoringUiState(
    val insertMonitoringEvent: InsertMonitoringEvent = InsertMonitoringEvent()
)

data class InsertMonitoringEvent(
    val idMonitoring: Int = 0,
    val idPetugas: Int = 0,
    val idKandang: Int = 0,
    val tanggalMonitoring: String = "",
    val hewanSakit: Int = 0,
    val hewanSehat: Int = 0,
    val status: String = ""
)


fun InsertMonitoringEvent.toMonitoring(): Monitoring {
    return Monitoring(
    idMonitoring = idMonitoring,
    idPetugas = idPetugas,
    idKandang = idKandang,
    tanggalMonitoring = tanggalMonitoring,
    hewanSakit = hewanSakit,
    hewanSehat = hewanSehat,
    status = status
)
}

// Fungsi untuk mengonversi KandangUiEvent kembali ke Kandang
fun InsertUiEventMonitoring.toMonitoring(): Monitoring {
    return Monitoring(
        idMonitoring = idMonitoring.toIntOrNull() ?: 0,
        idPetugas = idPetugas.toInt(),
        idKandang = idKandang.toInt(),
        tanggalMonitoring = tanggalMonitoring,
        hewanSakit = hewanSakit.toInt(),
        hewanSehat = hewanSehat.toInt(),
        status = status
    )
}

fun Monitoring.toUiStateMonitoring(): InsertMonitoringUiState = InsertMonitoringUiState(
    insertMonitoringEvent = this.toInsertMonitoringEvent()
)

// Fungsi ekstensi untuk mengonversi Monitoring ke InsertMonitoringUiEvent
fun Monitoring.toInsertMonitoringEvent(): InsertMonitoringEvent = InsertMonitoringEvent(
    idMonitoring = idMonitoring,
    idPetugas = idPetugas,
    idKandang = idKandang,
    tanggalMonitoring = tanggalMonitoring,
    hewanSakit = hewanSakit,
    hewanSehat = hewanSehat,
    status = status
)
