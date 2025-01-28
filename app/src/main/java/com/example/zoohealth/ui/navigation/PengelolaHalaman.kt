import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.zoohealth.ui.view.hewan.DestinasiDetailHewan
import com.example.zoohealth.ui.view.hewan.DestinasiEntryHewan
import com.example.zoohealth.ui.view.hewan.DestinasiHome
import com.example.zoohealth.ui.view.hewan.DestinasiUpdate
import com.example.zoohealth.ui.view.hewan.DetailHewanView
import com.example.zoohealth.ui.view.hewan.EntryHewanScreen
import com.example.zoohealth.ui.view.hewan.HomeScreen
import com.example.zoohealth.ui.view.hewan.UpdateView
import com.example.zoohealth.ui.view.kandang.DestinasiDetailKandang
import com.example.zoohealth.ui.view.kandang.DestinasiEntryKandang
import com.example.zoohealth.ui.view.kandang.DestinasiKandang
import com.example.zoohealth.ui.view.kandang.DestinasiUpdateKandang
import com.example.zoohealth.ui.view.kandang.DetailKandangView
import com.example.zoohealth.ui.view.kandang.EntryKandangScreen
import com.example.zoohealth.ui.view.kandang.HomeKandangScreen
import com.example.zoohealth.ui.view.kandang.UpdateKandangView
import com.example.zoohealth.ui.view.monitoring.DestinasiDetailMonitoring
import com.example.zoohealth.ui.view.monitoring.DestinasiEntryMonitoring
import com.example.zoohealth.ui.view.monitoring.DestinasiMonitoring
import com.example.zoohealth.ui.view.monitoring.DestinasiUpdateMonitoring
import com.example.zoohealth.ui.view.monitoring.DetailMonitoringView
import com.example.zoohealth.ui.view.monitoring.EntryMonitoringScreen
import com.example.zoohealth.ui.view.monitoring.MonitoringScreen
import com.example.zoohealth.ui.view.monitoring.UpdateMonitoringView
import com.example.zoohealth.ui.view.petugas.DestinasiDetailPetugas
import com.example.zoohealth.ui.view.petugas.DestinasiEntryPetugas
import com.example.zoohealth.ui.view.petugas.DestinasiPetugas
import com.example.zoohealth.ui.view.petugas.DestinasiUpdatePetugas
import com.example.zoohealth.ui.view.petugas.DetailPetugasView
import com.example.zoohealth.ui.view.petugas.EntryPetugasScreen
import com.example.zoohealth.ui.view.petugas.HomePetugasScreen
import com.example.zoohealth.ui.view.petugas.UpdatePetugasView

@Composable
fun PengelolaHalaman( modifier: Modifier = Modifier,navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestinasiHalamanHome.route,
        modifier = Modifier

    ){

// HomeView
        composable (DestinasiHalamanHome.route){
            HalamanHome(navController)
        }

// Hewan
        composable (DestinasiHome.route){
            HomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryHewan.route)},
                onDetailClick = { idHewan ->
                    navController.navigate("${DestinasiDetailHewan.route}/$idHewan")
                },
                NavigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable (DestinasiEntryHewan.route){
            EntryHewanScreen(navigateBack = {
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){inclusive = true} }
            }
            )
        }
        composable(
            DestinasiDetailHewan.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailHewan.IDHEWAN){
                    type = NavType.StringType
                }
            )
        ) {
            val idHewan = it.arguments?.getString(DestinasiDetailHewan.IDHEWAN)
            idHewan?.let {
                DetailHewanView(
                    NavigateBack = {
                        navController.navigate(DestinasiHome.route) {
                            popUpTo(DestinasiHome.route) { inclusive = true }
                        }
                    },
                    onEditClick =  {navController.navigate("${DestinasiUpdate.route}/$it")},
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            DestinasiUpdate.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdate.IDHEWAN) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateView(
                NavigateBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
            )
        }


        // Kandang
        composable (DestinasiKandang.route){
            HomeKandangScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryKandang.route)},
                onDetailClick = { idKandang ->
                    navController.navigate("${DestinasiDetailKandang.route}/$idKandang")
                },
                NavigateBack = {
                    navController.navigate(DestinasiKandang.route) {
                        popUpTo(DestinasiKandang.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable (DestinasiEntryKandang.route){
            EntryKandangScreen(navigateBack = {
                navController.navigate(DestinasiKandang.route){
                    popUpTo(DestinasiKandang.route){inclusive = true} }
            }
            )
        }
        composable(
            DestinasiDetailKandang.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailKandang.IDKANDANG){
                    type = NavType.StringType
                }
            )
        ) {
            val idKandang = it.arguments?.getString(DestinasiDetailKandang.IDKANDANG)
            idKandang?.let {
                DetailKandangView(
                    NavigateBack = {
                        navController.navigate(DestinasiKandang.route) {
                            popUpTo(DestinasiKandang.route) {
                                inclusive = true
                            }
                        }
                    },
                    onEditClick =  {navController.navigate("${DestinasiUpdateKandang.route}/$it")},
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            DestinasiUpdateKandang.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateKandang.IDKANDANG) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateKandangView(
                NavigateBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
            )
        }

        // Petugas
        composable (DestinasiPetugas.route){
            HomePetugasScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryPetugas.route)},
                onDetailClick = { idPetugas ->
                    navController.navigate("${DestinasiDetailPetugas.route}/$idPetugas")
                },
                NavigateBack = {
                    navController.navigate(DestinasiPetugas.route) {
                        popUpTo(DestinasiPetugas.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable (DestinasiEntryPetugas.route){
            EntryPetugasScreen(navigateBack = {
                navController.navigate(DestinasiPetugas.route){
                    popUpTo(DestinasiPetugas.route){inclusive = true} }
            }
            )
        }
        composable(
            DestinasiDetailPetugas.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailPetugas.IDPETUGAS){
                    type = NavType.StringType
                }
            )
        ) {
            val idPetugas = it.arguments?.getString(DestinasiDetailPetugas.IDPETUGAS)
            idPetugas?.let {
                DetailPetugasView(
                    NavigateBack = {
                        navController.navigate(DestinasiPetugas.route) {
                            popUpTo(DestinasiPetugas.route) {
                                inclusive = true
                            }
                        }
                    },
                    onEditClick =  {navController.navigate("${DestinasiUpdatePetugas.route}/$it")},
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            DestinasiUpdatePetugas.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdatePetugas.IDPETUGAS) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdatePetugasView(
                NavigateBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
            )
        }

        // Monitoring
        composable (DestinasiMonitoring.route){
            MonitoringScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryMonitoring.route)},
                onDetailClick = { idMonitoring ->
                    navController.navigate("${DestinasiDetailMonitoring.route}/$idMonitoring")
                },
                NavigateBack = {
                    navController.navigate(DestinasiMonitoring.route) {
                        popUpTo(DestinasiMonitoring.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable (DestinasiEntryMonitoring.route){
            EntryMonitoringScreen(navigateBack = {
                navController.navigate(DestinasiMonitoring.route){
                    popUpTo(DestinasiMonitoring.route){inclusive = true} }
            }
            )
        }
        composable(
            DestinasiDetailMonitoring.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetailMonitoring.IDMONITORING){
                    type = NavType.StringType
                }
            )
        ) {
            val idMonitoring = it.arguments?.getString(DestinasiDetailMonitoring.IDMONITORING)
            idMonitoring?.let {
                DetailMonitoringView(
                    NavigateBack = {
                        navController.navigate(DestinasiMonitoring.route) {
                            popUpTo(DestinasiMonitoring.route) {
                                inclusive = true
                            }
                        }
                    },
                    onEditClick =  {navController.navigate("${DestinasiUpdateMonitoring.route}/$it")},
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
        composable(
            DestinasiUpdateMonitoring.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiUpdateMonitoring.IDMONITORING) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateMonitoringView(
                NavigateBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
            )
        }
    }
}

