package com.example.zoohealth.ui.viewmodel.petugas

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zoohealth.model.Petugas
import com.example.zoohealth.repository.PetugasRepository
import com.example.zoohealth.ui.view.petugas.DestinasiDetailPetugas
import kotlinx.coroutines.launch

class DetailPetugasViewModel(
    savedStateHandle: SavedStateHandle,
    private val petugasRepository: PetugasRepository
) : ViewModel() {
    private val idPetugas: String = checkNotNull(savedStateHandle[DestinasiDetailPetugas.IDPETUGAS])

    var detailUiState: DetailPetugasUiState by mutableStateOf(DetailPetugasUiState())
        private set

    init {
        getPetugasById()
    }

    private fun getPetugasById() {
        viewModelScope.launch {
            detailUiState = DetailPetugasUiState(isLoading = true)
            try {
                val result = petugasRepository.getPetugasById(idPetugas).data
                detailUiState = DetailPetugasUiState(
                    detailUiEvent = result.toDetailUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailUiState = DetailPetugasUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown Error"
                )
            }
        }
    }

    fun deletePetugas() {
        viewModelScope.launch {
            detailUiState = DetailPetugasUiState(isLoading = true)
            try {
                petugasRepository.deletePetugas(idPetugas)
                detailUiState = DetailPetugasUiState(isLoading = false)
            } catch (e: Exception) {
                detailUiState = DetailPetugasUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown Error"
                )
            }
        }
    }
}

data class DetailPetugasUiState(
    val detailUiEvent: InsertUiEventPetugas = InsertUiEventPetugas(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == InsertUiEventPetugas()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEventPetugas()
}

fun Petugas.toDetailUiEvent(): InsertUiEventPetugas {
    return InsertUiEventPetugas(
        idPetugas = idPetugas.toString(),
        namaPetugas = namaPetugas,
        jabatan = jabatan
    )
}


data class InsertUiEventPetugas(
    val idPetugas: String = "",
    val namaPetugas: String = "",
    val jabatan: String = ""
)
