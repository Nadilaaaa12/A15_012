package com.example.zoohealth.ui.view.monitoring

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zoohealth.ui.customwidget.CustomTopAppBar
import com.example.zoohealth.ui.navigation.DestinasiNavigasi
import com.example.zoohealth.ui.viewmodel.PenyediaViewModel
import com.example.zoohealth.ui.viewmodelmonitoring.InsertMonitoringEvent
import com.example.zoohealth.ui.viewmodelmonitoring.InsertMonitoringUiState
import com.example.zoohealth.ui.viewmodelmonitoring.InsertMonitoringViewModel
import kotlinx.coroutines.launch

object DestinasiEntryMonitoring : DestinasiNavigasi {
    override val route = "item_entrymonitoring"
    override val titleRes = "Tambah Monitoring"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMonitoringScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertMonitoringViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiEntryMonitoring.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = {navigateBack() }
            )
        }
    ) { innerPadding ->
        EntryMonitoringBody(
            insertUiState = viewModel.uiState,
            onMonitoringValueChange = viewModel::updateInsertMonitoringState,
            onSaveClick = {
                coroutineScope.launch {
                    try {
                        viewModel.insertMonitoring()
                        navigateBack()
                    } catch (e: Exception) {
                        Log.e("EntryMonitoringScreen", "Error saat menyimpan data: ${e.message}")
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryMonitoringBody(
    insertUiState: InsertMonitoringUiState,
    onMonitoringValueChange: (InsertMonitoringEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        MonitoringFormInput(
            insertUiEvent = insertUiState.insertMonitoringEvent,
            onValueChange = onMonitoringValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun MonitoringFormInput(
    insertUiEvent: InsertMonitoringEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertMonitoringEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.idPetugas.toString(),
            onValueChange = { onValueChange(insertUiEvent.copy(idPetugas = it.toIntOrNull() ?: 0)) },
            label = { Text(text = "Id Petugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.idKandang.toString(),
            onValueChange = { onValueChange(insertUiEvent.copy(idKandang = it.toIntOrNull() ?: 0)) },
            label = { Text(text = "Id Kandang") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.tanggalMonitoring,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggalMonitoring = it)) },
            label = { Text(text = "Tanggal Monitoring (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.hewanSakit.toString(),
            onValueChange = { onValueChange(insertUiEvent.copy(hewanSakit = it.toIntOrNull() ?: 0)) },
            label = { Text(text = "Hewan Sakit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.hewanSehat.toString(),
            onValueChange = { onValueChange(insertUiEvent.copy(hewanSehat = it.toIntOrNull() ?: 0)) },
            label = { Text(text = "Hewan Sehat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.status,
            onValueChange = { onValueChange(insertUiEvent.copy(status = it)) },
            label = { Text(text = "Status") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}
