package com.example.zoohealth.ui.view.hewan

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zoohealth.ui.customwidget.CustomTopAppBar
import com.example.zoohealth.ui.navigation.DestinasiNavigasi
import com.example.zoohealth.ui.viewmodel.PenyediaViewModel
import com.example.zoohealth.ui.viewmodelhewan.InsertUiEvent
import com.example.zoohealth.ui.viewmodelhewan.InsertUiState
import com.example.zoohealth.ui.viewmodelhewan.InsertViewModel
import kotlinx.coroutines.launch

object DestinasiEntryHewan : DestinasiNavigasi {
    override val route = "item_entryhewan"
    override val titleRes = "Tambah Hewan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryHewanScreen(
    navigateBack: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiEntryHewan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = { navigateBack() }
            )

        }
    ) { innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onHewanValueChange = viewModel::updateInsertHewanState,
            onSaveClick = {
                coroutineScope.launch {
                    try {
                        viewModel.insertHewan()
                        navigateBack()
                    } catch (e: Exception) {
                        Log.e("EntryHewanScreen", "Error saat menyimpan data: ${e.message}")
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
fun EntryBody(
    insertUiState: InsertUiState,
    onHewanValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onHewanValueChange,
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
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val tipePakanOptions = listOf("herbivora", "carnivora", "omnivora")
    val selectedTipePakan = remember { mutableStateOf(insertUiEvent.tipePakan) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.namaHewan,
            onValueChange = { onValueChange(insertUiEvent.copy(namaHewan = it)) },
            label = { Text(text = "Nama Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        Text(text = "Tipe Pakan", style = MaterialTheme.typography.bodyMedium)
        tipePakanOptions.forEach { tipe ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = tipe == selectedTipePakan.value,
                    onClick = {
                        selectedTipePakan.value = tipe
                        onValueChange(insertUiEvent.copy(tipePakan = tipe))
                    },
                    enabled = enabled
                )
                Text(
                    text = tipe,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        OutlinedTextField(
            value = insertUiEvent.populasi,
            onValueChange = { onValueChange(insertUiEvent.copy(populasi = it)) },
            label = { Text(text = "Populasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.zonaWilayah,
            onValueChange = { onValueChange(insertUiEvent.copy(zonaWilayah = it)) },
            label = { Text(text = "Zona Wilayah") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}