package com.example.zoohealth.ui.viewmodelhewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zoohealth.model.Hewan
import com.example.zoohealth.repository.HewanRepository
import kotlinx.coroutines.launch

class InsertViewModel(private val hewanRepository: HewanRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertHewanState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertHewan() {
        viewModelScope.launch {
            try {
                hewanRepository.insertHewan(uiState.insertUiEvent.toHwn())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idHewan : Int=0,
    val namaHewan: String = "",
    val tipePakan: String = "",
    val populasi: String = "",
    val zonaWilayah: String = ""
)

fun InsertUiEvent.toHwn(): Hewan = Hewan(
    idHewan = idHewan,
    namaHewan = namaHewan,
    tipePakan = tipePakan,
    populasi = populasi,
    zonaWilayah = zonaWilayah,
)

fun Hewan.toUiStateHwn(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Hewan.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idHewan =idHewan,
    namaHewan = namaHewan,
    tipePakan = tipePakan,
    populasi = populasi,
    zonaWilayah = zonaWilayah
)
