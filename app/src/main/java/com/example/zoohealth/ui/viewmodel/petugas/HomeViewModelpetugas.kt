package com.example.zoohealth.ui.viewmodel.petugas


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.zoohealth.model.Petugas
import com.example.zoohealth.repository.PetugasRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class PetugasUiState {
    data class Success(val petugas: List<Petugas>) : PetugasUiState()
    object Error : PetugasUiState()
    object Loading : PetugasUiState()
}

class PetugasViewModel(private val petugasRepository: PetugasRepository) : ViewModel() {
    var petugasUiState: PetugasUiState by mutableStateOf(PetugasUiState.Loading)
        private set

    init {
        getPetugas()
    }

    fun getPetugas() {
        viewModelScope.launch {
            petugasUiState = PetugasUiState.Loading
            petugasUiState = try {
                PetugasUiState.Success(petugasRepository.getPetugas().data)
            } catch (e: IOException) {
                PetugasUiState.Error
            } catch (e: HttpException) {
                PetugasUiState.Error
            }
        }
    }
}