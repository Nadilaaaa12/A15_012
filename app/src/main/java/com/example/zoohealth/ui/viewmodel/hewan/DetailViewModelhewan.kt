package com.example.zoohealth.ui.viewmodelhewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zoohealth.model.Hewan
import com.example.zoohealth.repository.HewanRepository
import com.example.zoohealth.ui.view.hewan.DestinasiDetailHewan
import kotlinx.coroutines.launch



class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val hewanRepository: HewanRepository
) : ViewModel() {
    private val idHewan: String = checkNotNull(savedStateHandle[DestinasiDetailHewan.IDHEWAN])

    var detailUiState: DetailHewanUiState by mutableStateOf(DetailHewanUiState())
        private set

    init {
        getHewanById()
    }

    private fun getHewanById() {
        viewModelScope.launch {
            detailUiState = DetailHewanUiState(isLoading = true)
            try {
                val result = hewanRepository.getHewanById(idHewan).data
                detailUiState = DetailHewanUiState(
                    detailUiEvent = result.toDetailUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailUiState = DetailHewanUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown Error"
                )
            }
        }
    }

    fun deleteHewan() {
        viewModelScope.launch {
            detailUiState = DetailHewanUiState(isLoading = true)
            try {
                hewanRepository.deleteHewan(idHewan)
                detailUiState = DetailHewanUiState(isLoading = false)
            } catch (e: Exception) {
                detailUiState = DetailHewanUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown Error"
                )
            }
        }
    }
}

data class DetailHewanUiState(
    val detailUiEvent: InsertUiEvent = InsertUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == InsertUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEvent()
}

fun Hewan.toDetailUiEvent(): InsertUiEvent {
    return InsertUiEvent(
        namaHewan = namaHewan,
        tipePakan = tipePakan,
        populasi = populasi,
        zonaWilayah = zonaWilayah
    )
}

data class HewanUiEvent(
    val idHewan: String = "",
    val namaHewan: String = "",
    val tipePakan: String = "",
    val populasi: String = "",
    val zonaWilayah: String = ""
)
