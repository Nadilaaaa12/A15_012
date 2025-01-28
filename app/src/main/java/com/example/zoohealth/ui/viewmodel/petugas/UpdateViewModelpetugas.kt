package com.example.zoohealth.ui.viewmodel.petugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zoohealth.model.Petugas
import com.example.zoohealth.repository.PetugasRepository
import com.example.zoohealth.ui.view.petugas.DestinasiUpdatePetugas
import kotlinx.coroutines.launch

class UpdatePetugasViewModel(
    savedStateHandle: SavedStateHandle,
    private val petugasRepository: PetugasRepository
) : ViewModel() {

    var updateUIState by mutableStateOf(InsertPetugasUiState())
        private set

    private val id_petugas: String = checkNotNull(savedStateHandle[DestinasiUpdatePetugas.IDPETUGAS])

    init {
        viewModelScope.launch {
            try {
                val petugas = petugasRepository.getPetugasById(id_petugas).data
                updateUIState = petugas.toUiStatePetugas()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertPetugasState(insertPetugasEvent: InsertPetugasEvent) {
        updateUIState = InsertPetugasUiState(insertPetugasEvent = insertPetugasEvent)
    }

    suspend fun updateData() {
        viewModelScope.launch {
            try {
                val petugas = updateUIState.insertPetugasEvent.toPetugas()
                petugasRepository.updatePetugas(id_petugas, petugas)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


fun Petugas.toUiStatePetugasForUpdate(): InsertPetugasUiState {
    return InsertPetugasUiState(
        insertPetugasEvent = InsertPetugasEvent(
            // Map properties from domain model to UI state fields
            idPetugas = this.idPetugas,
            namaPetugas = this.namaPetugas,
            jabatan = this.jabatan,
        )
    )
}

fun InsertPetugasEvent.toUpdatePetugas(): Petugas {
    return Petugas(
        idPetugas = idPetugas,
        namaPetugas = namaPetugas,
        jabatan = jabatan
    )
}
