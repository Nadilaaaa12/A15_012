package com.example.zoohealth.ui.view.monitoring


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zoohealth.model.Monitoring
import com.example.zoohealth.ui.customwidget.CustomTopAppBar
import com.example.zoohealth.ui.navigation.DestinasiNavigasi
import com.example.zoohealth.ui.view.hewan.OnError
import com.example.zoohealth.ui.view.hewan.OnLoading
import com.example.zoohealth.ui.viewmodel.PenyediaViewModel
import com.example.zoohealth.ui.viewmodelmonitoring.MonitoringUiState
import com.example.zoohealth.ui.viewmodelmonitoring.MonitoringViewModel

object DestinasiMonitoring : DestinasiNavigasi {
    override val route = "monitoring"
    override val titleRes = "Monitoring"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoringScreen(
    navigateToItemEntry: () -> Unit,
    NavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    viewModel: MonitoringViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiMonitoring.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getMonitoring()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Monitoring")
            }
        },
    ) { innerPadding ->
        MonitoringStatus(
            monitoringUiState = viewModel.monitoringUiState,
            retryAction = { viewModel.getMonitoring() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun MonitoringStatus(
    monitoringUiState: MonitoringUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit
) {
    when (monitoringUiState) {
        is MonitoringUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }

        is MonitoringUiState.Success -> {
            if (monitoringUiState.monitoring.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Monitoring")
                }
            } else {
                MonitoringLayout(
                    monitoring = monitoringUiState.monitoring,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idMonitoring.toString())
                    }
                )
            }
        }

        is MonitoringUiState.Error -> {
            OnError(retryAction, modifier = modifier.fillMaxSize())
        }

        else -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Status tidak dikenal")
            }
        }
    }
}

@Composable
fun MonitoringLayout(
    monitoring: List<Monitoring>,
    modifier: Modifier = Modifier,
    onDetailClick: (Monitoring) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(monitoring) { monitoring ->
            MonitoringCard(
                monitoring = monitoring,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(monitoring) }
            )
        }
    }
}

@Composable
fun MonitoringCard(
    monitoring: Monitoring,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Id Petugas: ${monitoring.idPetugas}",
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "Id Kandang: ${monitoring.idKandang}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Tanggal: ${monitoring.tanggalMonitoring}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Hewan Sakit: ${monitoring.hewanSakit}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Hewan Sehat: ${monitoring.hewanSehat}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Status: ${monitoring.status}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
