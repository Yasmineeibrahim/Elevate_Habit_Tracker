import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SleepQualityScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.drink.jpeg), // 🔁 Replace with actual image name
                contentDescription = "Relaxing Girl",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Improve your sleep quality",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Improve your sleep quality by creating a relaxing bedtime routine, staying consistent with your schedule and avoiding screens before bed. Wake up feeling refreshed and ready for the day.",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            IconButton(onClick = { /* Navigate to next screen */ }) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Next",
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = Color(0xFFD291BC), // 🎨 Replace with your color
                            shape = CircleShape
                        )
                        .padding(12.dp),
                    tint = Color.White
                )
            }
        }
    }
}
