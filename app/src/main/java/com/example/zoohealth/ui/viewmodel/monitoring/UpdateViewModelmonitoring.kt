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
import com.example.zoohealth.ui.view.monitoring.DestinasiUpdateMonitoring
import com.example.zoohealth.ui.viewmodelkandang.InsertKandangEvent
import kotlinx.coroutines.launch

class UpdateMonitoringViewModel(
    savedStateHandle: SavedStateHandle,
    private val monitoringRepository: MonitoringRepository
) : ViewModel() {

    var updateMonitoringUiState by mutableStateOf(InsertMonitoringUiState())
        private set

    private val id_monitoring: String = checkNotNull(savedStateHandle[DestinasiUpdateMonitoring.IDMONITORING])

    init {
        viewModelScope.launch {
            try {
                val monitoring = monitoringRepository.getMonitoringById(id_monitoring).data
                updateMonitoringUiState = monitoring.toUiStateMonitoring()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertMonitoringState(insertMonitoringEvent: InsertMonitoringEvent) {
        updateMonitoringUiState = updateMonitoringUiState.copy(insertMonitoringEvent = insertMonitoringEvent)
    }

    fun updateData() {
        viewModelScope.launch {
            try {
                val monitoring = updateMonitoringUiState.insertMonitoringEvent.toMonitoring()
                monitoringRepository.updateMonitoring(id_monitoring, monitoring)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


 fun Monitoring.toUiStateMonitoringForUpdate(): InsertMonitoringUiState {
    return InsertMonitoringUiState(
        insertMonitoringEvent = InsertMonitoringEvent(
            idMonitoring = this.idMonitoring,
            idPetugas = this.idPetugas,
            idKandang = this.idKandang,
            tanggalMonitoring = this.tanggalMonitoring,
            hewanSakit = this.hewanSakit,
            hewanSehat = this.hewanSehat,
            status = this.status
        )
    )
}

fun InsertMonitoringEvent.toUpdateMonitoring(): Monitoring {
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






