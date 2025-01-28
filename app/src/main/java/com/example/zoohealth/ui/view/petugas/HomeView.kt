package com.example.zoohealth.ui.view.petugas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zoohealth.R
import com.example.zoohealth.model.Petugas
import com.example.zoohealth.ui.customwidget.CustomTopAppBar
import com.example.zoohealth.ui.navigation.DestinasiNavigasi
import com.example.zoohealth.ui.view.hewan.OnError
import com.example.zoohealth.ui.view.hewan.OnLoading
import com.example.zoohealth.ui.viewmodel.PenyediaViewModel
import com.example.zoohealth.ui.viewmodel.petugas.PetugasUiState
import com.example.zoohealth.ui.viewmodel.petugas.PetugasViewModel

object DestinasiPetugas : DestinasiNavigasi {
    override val route = "homePetugas"
    override val titleRes = "Home Petugas"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePetugasScreen(
    navigateToItemEntry: () -> Unit,
    NavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    viewModel: PetugasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedPetugas: Petugas? by remember { mutableStateOf(null) }
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiPetugas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPetugas()
                },
                navigateUp = NavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Petugas")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Full-screen Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp), // Adjust height as needed
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.zoo), // Replace with your image
                    contentDescription = "Zoo Banner",
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Search bar
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                placeholder = { Text(text = "Cari petugas...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp)
            )

            // Display petugas
            PetugasStatus(
                homeUiState = viewModel.petugasUiState,
                retryAction = { viewModel.getPetugas() },
                onDetailClick = onDetailClick,
                searchText = searchText.text
            )
        }
    }
}

@Composable
fun PetugasStatus(
    homeUiState: PetugasUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Petugas) -> Unit = {},
    onDetailClick: (String) -> Unit,
    searchText: String
) {
    when (homeUiState) {
        is PetugasUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }

        is PetugasUiState.Success -> {
            val filteredPetugas = homeUiState.petugas.filter {
                it.namaPetugas.contains(searchText, ignoreCase = true)
            }

            if (filteredPetugas.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Petugas")
                }
            } else {
                PetugasLayout(
                    petugas = filteredPetugas,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idPetugas.toString()) },
                    onDeleteClick = onDeleteClick
                )
            }
        }

        is PetugasUiState.Error -> {
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
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .size(100.dp)
            .padding(40.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = "Loading"
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.connectionerror), contentDescription = "")
        Text(
            text = "Gagal memuat data",
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text("Coba Lagi")
        }
    }
}

@Composable
fun PetugasLayout(
    petugas: List<Petugas>,
    modifier: Modifier = Modifier,
    onDetailClick: (Petugas) -> Unit,
    onDeleteClick: (Petugas) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(petugas) { petugas ->
            PetugasCard(
                petugas = petugas,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(petugas) },
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun PetugasCard(
    petugas: Petugas,
    modifier: Modifier = Modifier,
    onDeleteClick: (Petugas) -> Unit = {}
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    petugas.namaPetugas,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(petugas) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
            Text(
                text = "Jabatan: ${petugas.jabatan}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
