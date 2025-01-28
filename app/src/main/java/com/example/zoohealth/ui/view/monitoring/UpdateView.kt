package com.example.zoohealth.ui.view.monitoring

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
import com.example.zoohealth.ui.viewmodelmonitoring.UpdateMonitoringViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateMonitoring : DestinasiNavigasi {
    override val route = "update_monitoring"
    override val titleRes = "Edit Monitoring"
    const val IDMONITORING = "id_monitoring"
    val routeWithArgs = "$route/{$IDMONITORING}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMonitoringView(
    NavigateBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMonitoringViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdateMonitoring.titleRes,
                canNavigateBack = true,
                navigateUp = NavigateBack,
            )
        }
    ) { padding ->
        EntryMonitoringBody(
            modifier = Modifier.padding(padding),
            onMonitoringValueChange = viewModel::updateInsertMonitoringState,
            insertUiState = viewModel.updateMonitoringUiState,
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
