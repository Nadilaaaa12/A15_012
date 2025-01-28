package com.example.zoohealth.ui.view.kandang

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
import com.example.zoohealth.model.Kandang
import com.example.zoohealth.ui.customwidget.CustomTopAppBar
import com.example.zoohealth.ui.navigation.DestinasiNavigasi
import com.example.zoohealth.ui.view.petugas.OnError
import com.example.zoohealth.ui.view.petugas.OnLoading
import com.example.zoohealth.ui.viewmodel.PenyediaViewModel
import com.example.zoohealth.ui.viewmodelkandang.KandangUiState
import com.example.zoohealth.ui.viewmodelkandang.KandangViewModel

object DestinasiKandang : DestinasiNavigasi {
    override val route = "homekandang"
    override val titleRes = "Home Kandang"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeKandangScreen(
    navigateToItemEntry: () -> Unit,
    NavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    viewModel: KandangViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedKandang: Kandang? by remember { mutableStateOf(null) }
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiKandang.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getKandang()
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kandang")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.zoo),
                    contentDescription = "Zoo Banner",
                    modifier = Modifier.fillMaxSize()
                )
            }


            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                placeholder = { Text(text = "Cari kandang...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(8.dp)
            )


            KandangStatus(
                kandangUiState = viewModel.kandangUiState,
                retryAction = { viewModel.getKandang() },
                onDetailClick = onDetailClick,
                searchText = searchText.text
            )
        }
    }
}

@Composable
fun KandangStatus(
    kandangUiState: KandangUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kandang) -> Unit = {},
    onDetailClick: (String) -> Unit,
    searchText: String
) {
    when (kandangUiState) {
        is KandangUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }

        is KandangUiState.Success -> {
            val filteredKandang = kandangUiState.kandang.filter {
                it.lokasi.contains(searchText, ignoreCase = true)
            }

            if (filteredKandang.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Kandang")
                }
            } else {
                KandangLayout(
                    kandang = filteredKandang,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idKandang.toString()) },
                    onDeleteClick = onDeleteClick
                )
            }
        }

        is KandangUiState.Error -> {
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
fun KandangLayout(
    kandang: List<Kandang>,
    modifier: Modifier = Modifier,
    onDetailClick: (Kandang) -> Unit,
    onDeleteClick: (Kandang) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kandang) { kandang ->
            KandangCard(
                kandang = kandang,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(kandang) },
                onDeleteClick = onDeleteClick
            )
        }
    }
}

@Composable
fun KandangCard(
    kandang: Kandang,
    modifier: Modifier = Modifier,
    onDeleteClick: (Kandang) -> Unit = {}
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
                    kandang.idHewan.toString(),
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(kandang) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }
            Text(
                text = "Lokasi: ${kandang.lokasi}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Kapasitas: ${kandang.kapasitas}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
