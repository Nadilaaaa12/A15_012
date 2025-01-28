import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zoohealth.R
import com.example.zoohealth.ui.navigation.DestinasiNavigasi
import com.example.zoohealth.ui.view.hewan.DestinasiHome
import com.example.zoohealth.ui.view.kandang.DestinasiKandang
import com.example.zoohealth.ui.view.monitoring.DestinasiMonitoring
import com.example.zoohealth.ui.view.petugas.DestinasiPetugas

object DestinasiHalamanHome : DestinasiNavigasi {
    override val route = "halamanhome"
    override val titleRes = "Halaman Utama"
}

@Composable
fun HalamanHome(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFBBDEFB), Color(0xFFE3F2FD))
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(32.dp))


        Text(
            text = "Monitoring Loka Zoo",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = Color(0xFF1976D2),
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            MenuCard(
                title = "Hewan",
                icon = R.drawable.hewan,
                onClick = { navController.navigate(DestinasiHome.route) }
            )

            MenuCard(
                title = "Kandang",
                icon = R.drawable.kandang,
                onClick = { navController.navigate(DestinasiKandang.route) }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))


        BottomNavigationBar(navController = navController)
    }
}

@Composable
fun MenuCard(title: String, icon: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(160.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF64B5F6)),
        elevation = CardDefaults.cardElevation(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(72.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                color = Color.White
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(35.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NavigationItem("Home", R.drawable.home, onClick = { navController.navigate(DestinasiHalamanHome.route) })
        NavigationItem("Petugas", R.drawable.petugas, onClick = { navController.navigate(DestinasiPetugas.route) })
        NavigationItem("Monitoring", R.drawable.monitoring, onClick = { navController.navigate(DestinasiMonitoring.route) })
    }
}

@Composable
fun NavigationItem(title: String, icon: Int, onClick: () -> Unit) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF1976D2) else Color(0xFFE3F2FD)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(64.dp)
            .background(backgroundColor, CircleShape)
            .clickable {
                isSelected = !isSelected
                onClick()
            }
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            color = if (isSelected) Color.White else Color.Black
        )
    }
}
