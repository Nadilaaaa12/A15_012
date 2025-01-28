package com.example.zoohealth.ui.view.petugas

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
import com.example.zoohealth.ui.customwidget.CustomTopAppBar
import com.example.zoohealth.ui.navigation.DestinasiNavigasi
import com.example.zoohealth.ui.viewmodel.PenyediaViewModel
import com.example.zoohealth.ui.viewmodel.petugas.InsertPetugasEvent
import com.example.zoohealth.ui.viewmodel.petugas.InsertPetugasUiState
import com.example.zoohealth.ui.viewmodel.petugas.InsertPetugasViewModel
import kotlinx.coroutines.launch

object DestinasiEntryPetugas : DestinasiNavigasi {
    override val route = "item_entrypetugas"
    override val titleRes = "Tambah Petugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPetugasScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPetugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiEntryPetugas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = {navigateBack() }
            )
        }
    ) { innerPadding ->
        EntryBodyPetugas(
            insertUiState = viewModel.uiState,
            onPetugasValueChange = viewModel::updateInsertPetugasState,
            onSaveClick = {
                coroutineScope.launch {
                    try {
                    viewModel.insertPetugas()
                    navigateBack()
                } catch (e: Exception) {
                        Log.e("EntryPetugasScreen", "Error saat menyimpan data: ${e.message}")
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
fun EntryBodyPetugas(
    insertUiState: InsertPetugasUiState,
    onPetugasValueChange: (InsertPetugasEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPetugas(
            insertPetugasEvent = insertUiState.insertPetugasEvent,
            onValueChange = onPetugasValueChange,
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
fun FormInputPetugas(
    insertPetugasEvent: InsertPetugasEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPetugasEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertPetugasEvent.namaPetugas,
            onValueChange = { onValueChange(insertPetugasEvent.copy(namaPetugas = it)) },
            label = { Text(text = "Nama Petugas") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPetugasEvent.jabatan,
            onValueChange = { onValueChange(insertPetugasEvent.copy(jabatan = it)) },
            label = { Text(text = "Jabatan") },
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
