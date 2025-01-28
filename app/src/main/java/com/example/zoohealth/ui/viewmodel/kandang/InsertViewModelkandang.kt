package com.example.zoohealth.ui.viewmodelkandang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zoohealth.model.Kandang
import com.example.zoohealth.model.Petugas
import com.example.zoohealth.repository.KandangRepository
import com.example.zoohealth.ui.viewmodel.petugas.InsertPetugasEvent
import com.example.zoohealth.ui.viewmodel.petugas.InsertPetugasUiState
import kotlinx.coroutines.launch


class InsertKandangViewModel(private val kandangRepository: KandangRepository) : ViewModel() {


    var uiState by mutableStateOf(InsertKandangUiState())
        private set


    fun updateInsertKandangState(insertKandangEvent: InsertKandangEvent) {
        uiState = InsertKandangUiState(insertKandangEvent = insertKandangEvent)
    }



    fun insertKandang() {
        viewModelScope.launch {
            try {

                kandangRepository.insertKandang(uiState.insertKandangEvent.toKandang())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


data class InsertKandangUiState(
    val insertKandangEvent: InsertKandangEvent = InsertKandangEvent()
)


data class InsertKandangEvent(
    val idKandang: Int = 0,
    val idHewan: Int = 0,
    val kapasitas: Int = 0,
    val lokasi: String = ""
)
fun InsertKandangEvent.toKandang(): Kandang {
    return Kandang(
        idKandang= idKandang,
        idHewan = idHewan,
        kapasitas = kapasitas,
        lokasi = lokasi
    )
}

fun InsertUiEventKandang.toKandang(): Kandang {
    return Kandang(
        idKandang = idKandang.toIntOrNull() ?: 0,
        idHewan = idHewan.toInt(),
        kapasitas = kapasitas,
        lokasi = lokasi
    )
}


fun Kandang.toUiStateKandang(): InsertKandangUiState  = InsertKandangUiState(
        insertKandangEvent = this.toInsertKandangEvent()
    )


fun Kandang.toInsertKandangEvent(): InsertKandangEvent = InsertKandangEvent(
    idKandang= idKandang,
    idHewan = idHewan,
    kapasitas = kapasitas,
    lokasi = lokasi
)


