package com.example.zoohealth.ui.view.petugas

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zoohealth.ui.customwidget.CustomTopAppBar
import com.example.zoohealth.ui.navigation.DestinasiNavigasi
import com.example.zoohealth.ui.viewmodel.PenyediaViewModel
import com.example.zoohealth.ui.viewmodel.petugas.UpdatePetugasViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdatePetugas : DestinasiNavigasi {
    override val route = "update_petugas"
    override val titleRes = "Edit Petugas"
    const val IDPETUGAS = "id_petugas"
    val routeWithArgs = "$route/{$IDPETUGAS}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePetugasView(
    NavigateBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePetugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdatePetugas.titleRes,
                canNavigateBack = true,
                navigateUp = NavigateBack,
            )
        }
    ) { padding ->
        EntryBodyPetugas(
            modifier = Modifier.padding(padding),
            onPetugasValueChange = viewModel::updateInsertPetugasState,
            insertUiState = viewModel.updateUIState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateData()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}