package com.example.zoohealth.ui.view.kandang

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zoohealth.ui.customwidget.CustomTopAppBar
import com.example.zoohealth.ui.navigation.DestinasiNavigasi
import com.example.zoohealth.ui.view.hewan.EntryBody
import com.example.zoohealth.ui.viewmodel.PenyediaViewModel
import com.example.zoohealth.ui.viewmodel.UpdateKandangViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateKandang : DestinasiNavigasi {
    override val route = "update_kandang"
    override val titleRes = "Edit Kandang"
    const val IDKANDANG = "id_kandang"
    val routeWithArgs = "$route/{$IDKANDANG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateKandangView(
    NavigateBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateKandangViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateKandang.titleRes,
                canNavigateBack = true,
                navigateUp = NavigateBack,
            )
        }
    ) { padding ->
        EntryKandangBody(
            modifier = Modifier.padding(padding),
            onKandangValueChange = viewModel::updateInsertKandangState,
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
