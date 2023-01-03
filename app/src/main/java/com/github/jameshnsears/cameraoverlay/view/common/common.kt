import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.jameshnsears.cameraoverlay.R

@Composable
fun ButtonOk(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(width = 120.dp, height = 45.dp)
            .padding(bottom = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Text(stringResource(R.string.about_dialog_ok))
    }
}
