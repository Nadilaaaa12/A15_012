package com.example.zoohealth.ui.viewmodelkandang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zoohealth.model.Kandang
import com.example.zoohealth.repository.KandangRepository
import com.example.zoohealth.ui.view.kandang.DestinasiDetailKandang
import kotlinx.coroutines.launch

class DetailKandangViewModel(
    savedStateHandle: SavedStateHandle,
    private val kandangRepository: KandangRepository
) : ViewModel() {
    private val idKandang: String = checkNotNull(savedStateHandle[DestinasiDetailKandang.IDKANDANG])

    var detailUiState: DetailKandangUiState by mutableStateOf(DetailKandangUiState())
        private set

    init {
        getKandangById()
    }

    private fun getKandangById() {
        viewModelScope.launch {
            detailUiState = DetailKandangUiState(isLoading = true)
            try {
                val result = kandangRepository.getKandangById(idKandang).data
                detailUiState = DetailKandangUiState(
                    detailUiEvent = result.toDetailUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailUiState = DetailKandangUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown Error"
                )
            }
        }
    }

    fun deleteKandang() {
        viewModelScope.launch {
            detailUiState = DetailKandangUiState(isLoading = true)
            try {
                kandangRepository.deleteKandang(idKandang)
                detailUiState = DetailKandangUiState(isLoading = false)
            } catch (e: Exception) {
                detailUiState = DetailKandangUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown Error"
                )
            }
        }
    }
}

data class DetailKandangUiState(
    val detailUiEvent: InsertUiEventKandang = InsertUiEventKandang(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == InsertUiEventKandang()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEventKandang()
}

fun Kandang.toDetailUiEvent(): InsertUiEventKandang {
    return InsertUiEventKandang(
        idKandang = idKandang.toString(),
        idHewan = idHewan.toString(),
        kapasitas = kapasitas,
        lokasi = lokasi
    )
}


data class InsertUiEventKandang(
    val idKandang: String = "",
    val idHewan: String = "",
    val kapasitas: Int = 0,
    val lokasi: String = ""
)
