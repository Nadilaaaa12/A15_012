package com.example.zoohealth.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zoohealth.model.Kandang
import com.example.zoohealth.repository.KandangRepository
import com.example.zoohealth.ui.view.kandang.DestinasiUpdateKandang
import com.example.zoohealth.ui.viewmodelkandang.InsertKandangEvent
import com.example.zoohealth.ui.viewmodelkandang.InsertKandangUiState
import com.example.zoohealth.ui.viewmodelkandang.toKandang
import com.example.zoohealth.ui.viewmodelkandang.toUiStateKandang
import kotlinx.coroutines.launch

class UpdateKandangViewModel(
    savedStateHandle: SavedStateHandle,
    private val kandangRepository: KandangRepository
) : ViewModel() {

    var updateUIState by mutableStateOf(InsertKandangUiState())
        private set

    private val id_kandang: String = checkNotNull(savedStateHandle[DestinasiUpdateKandang.IDKANDANG])

    init {
        viewModelScope.launch {
            try {
                val kandang = kandangRepository.getKandangById(id_kandang).data
                updateUIState = kandang.toUiStateKandang()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertKandangState(insertKandangEvent: InsertKandangEvent) {
        updateUIState = InsertKandangUiState(insertKandangEvent = insertKandangEvent)
    }

     fun updateData() {
        viewModelScope.launch {
            try {
                val kandang = updateUIState.insertKandangEvent.toKandang()
                kandangRepository.updateKandang(id_kandang, kandang)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


fun Kandang.toUiStateKandangForUpdate(): InsertKandangUiState {
    return InsertKandangUiState(
        insertKandangEvent = InsertKandangEvent(
            // Map properties from domain model to UI state fields
            idKandang= this.idKandang,
            idHewan = this.idHewan,
            kapasitas = this.kapasitas,
            lokasi = this.lokasi,

        )
    )
}

fun InsertKandangEvent.toUpdateKandang(): Kandang {
    return Kandang(
        idKandang= idKandang,
        idHewan = idHewan,
        kapasitas = kapasitas,
        lokasi = lokasi,
    )
}