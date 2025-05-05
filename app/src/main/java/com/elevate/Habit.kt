import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    val title: String,
    val imageResId: Int,
    var isSelected: Boolean = false
) : Parcelable
