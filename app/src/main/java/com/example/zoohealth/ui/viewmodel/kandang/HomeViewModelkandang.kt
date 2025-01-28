package com.example.zoohealth.ui.viewmodelkandang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.zoohealth.model.Kandang
import com.example.zoohealth.repository.KandangRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class KandangUiState {
    data class Success(val kandang: List<Kandang>) : KandangUiState()
    object Error : KandangUiState()
    object Loading : KandangUiState()
}

class KandangViewModel(private val kandangRepository: KandangRepository) : ViewModel() {
    var kandangUiState: KandangUiState by mutableStateOf(KandangUiState.Loading)
        private set

    init {
        getKandang()
    }

    fun getKandang() {
        viewModelScope.launch {
            kandangUiState = KandangUiState.Loading
            kandangUiState = try {
                KandangUiState.Success(kandangRepository.getKandang().data)
            } catch (e: IOException) {
                KandangUiState.Error
            } catch (e: HttpException) {
                KandangUiState.Error
            }
        }
    }
}
