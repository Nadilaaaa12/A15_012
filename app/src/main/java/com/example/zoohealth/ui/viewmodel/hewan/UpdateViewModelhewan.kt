package com.example.zoohealth.ui.viewmodelhewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zoohealth.model.Hewan
import com.example.zoohealth.repository.HewanRepository
import com.example.zoohealth.ui.view.hewan.DestinasiUpdate
import com.example.zoohealth.ui.viewmodelhewan.InsertUiEvent
import com.example.zoohealth.ui.viewmodelhewan.InsertUiState
import kotlinx.coroutines.launch

class UpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val hewanRepository: HewanRepository
) : ViewModel() {

    var updateUIState by mutableStateOf(InsertUiState())
        private set

    private val id_hewan: String = checkNotNull(savedStateHandle[DestinasiUpdate.IDHEWAN])

    init {
        viewModelScope.launch {
            try {
                val hewan = hewanRepository.getHewanById(id_hewan).data
                updateUIState = hewan.toUiStateHewan()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertHewanState(insertUiEvent: InsertUiEvent) {
        updateUIState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun updateData() {
        viewModelScope.launch {
            try {
                val hewan = updateUIState.insertUiEvent.toHewan()
                hewanRepository.updateHewan(id_hewan, hewan)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

// Extension functions to map between domain and UI state
fun Hewan.toUiStateHewan(): InsertUiState {
    return InsertUiState(
        insertUiEvent = InsertUiEvent(
            // Map properties from domain model to UI state fields
            idHewan = this.idHewan,
            namaHewan = this.namaHewan,
            tipePakan = this.tipePakan,
            populasi = this.populasi,
            zonaWilayah = this.zonaWilayah

        )
    )
}

fun InsertUiEvent.toHewan(): Hewan {
    return Hewan(
        idHewan = this.idHewan,
        namaHewan = this.namaHewan,
        tipePakan = this.tipePakan,
        populasi = this.populasi,
        zonaWilayah = this.zonaWilayah
    )
}
