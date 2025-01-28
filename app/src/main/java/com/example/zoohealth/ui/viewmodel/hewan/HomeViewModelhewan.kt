package com.example.zoohealth.ui.viewmodelhewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.zoohealth.model.Hewan
import com.example.zoohealth.repository.HewanRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val hewan: List<Hewan>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModel(private val hewan: HewanRepository) : ViewModel() {
    var hewanUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getHewan()
    }

    fun getHewan() {
        viewModelScope.launch {
            hewanUiState = HomeUiState.Loading
            hewanUiState = try {
                HomeUiState.Success(hewan.getHewan().data)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}
