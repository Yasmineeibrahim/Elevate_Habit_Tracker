
// import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elevate.R

@Composable
fun SleepScreen(onNextClicked: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.drinkk),
                contentDescription = "Drink Illustration",
                alignment =Alignment.TopCenter ,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)

            )

            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Improve your sleep quality",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(35.dp))

            Text(
                text = "Improve your sleep quality by creating a relaxing bedtime routine, staying consistent with your schedule, and avoiding screens before bed.\nWake up feeling refreshed and ready for the day.",
                fontSize = 8.sp,
                color = Color.DarkGray
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(modifier = Modifier.size(100.dp)) {
                // Background line
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Curve Line",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(80.dp)
                )

            }
        }
    }
}
