package com.example.zoohealth.ui.viewmodelmonitoring

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.zoohealth.model.Monitoring
import com.example.zoohealth.repository.MonitoringRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class MonitoringUiState {
    data class Success(val monitoring: List<Monitoring>) : MonitoringUiState()
    object Error : MonitoringUiState()
    object Loading : MonitoringUiState()
}

class MonitoringViewModel(private val monitoringRepository: MonitoringRepository) : ViewModel() {
    var monitoringUiState: MonitoringUiState by mutableStateOf(MonitoringUiState.Loading)
        private set

    init {
        getMonitoring()
    }

    fun getMonitoring() {
        viewModelScope.launch {
            monitoringUiState = MonitoringUiState.Loading
            monitoringUiState = try {
                MonitoringUiState.Success(monitoringRepository.getMonitoring().data)
            } catch (e: IOException) {
                MonitoringUiState.Error
            } catch (e: HttpException) {
                MonitoringUiState.Error
            }
        }
    }
}
