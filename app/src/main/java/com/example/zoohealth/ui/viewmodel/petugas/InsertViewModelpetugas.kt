package com.example.zoohealth.ui.viewmodel.petugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zoohealth.model.Petugas
import com.example.zoohealth.repository.PetugasRepository
import kotlinx.coroutines.launch

class InsertPetugasViewModel(private val petugasRepository: PetugasRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPetugasUiState())
        private set

    fun updateInsertPetugasState(insertPetugasEvent: InsertPetugasEvent) {
        uiState = InsertPetugasUiState(insertPetugasEvent = insertPetugasEvent)
    }

    suspend fun insertPetugas() {
        viewModelScope.launch {
            try {
                petugasRepository.insertPetugas(uiState.insertPetugasEvent.toPetugas())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPetugasUiState(
    val insertPetugasEvent: InsertPetugasEvent = InsertPetugasEvent()
)

data class InsertPetugasEvent(
    val idPetugas: Int = 0,
    val namaPetugas: String = "",
    val jabatan: String = "",
)

fun InsertPetugasEvent.toPetugas(): Petugas {
    return Petugas(
        idPetugas = idPetugas,
        namaPetugas = namaPetugas,
        jabatan = jabatan
    )
}
fun InsertUiEventPetugas.toPetugas(): Petugas {
    return Petugas(
        idPetugas = idPetugas.toIntOrNull() ?: 0,
        namaPetugas = namaPetugas,
        jabatan = jabatan
    )
}


fun Petugas.toUiStatePetugas(): InsertPetugasUiState = InsertPetugasUiState(
    insertPetugasEvent = toInsertPetugasEvent()
)

fun Petugas.toInsertPetugasEvent(): InsertPetugasEvent = InsertPetugasEvent(
    idPetugas = idPetugas,
    namaPetugas = namaPetugas,
    jabatan = jabatan,

)