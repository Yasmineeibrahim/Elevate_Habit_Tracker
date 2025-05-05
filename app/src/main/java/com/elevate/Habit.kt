import android.os.Parcel
import android.os.Parcelable

data class Habit(
    val title: String,
    val imageResId: Int,
    var isSelected: Boolean = false // used only in UI, not passed between activities
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt()
        // Do NOT read isSelected — it's UI-specific
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(imageResId)
        parcel.writeByte(if (isSelected) 1 else 0)
        // Do NOT write isSelected
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Habit> {
        override fun createFromParcel(parcel: Parcel): Habit = Habit(parcel)
        override fun newArray(size: Int): Array<Habit?> = arrayOfNulls(size)
    }
}
