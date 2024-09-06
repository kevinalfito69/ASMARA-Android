import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    private const val INPUT_DATE_FORMAT = "yyyy-MM-dd"
    private const val DISPLAY_DATE_FORMAT = "dd MMMM yyyy"

    private val inputDateFormat = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.getDefault())
    private val displayDateFormat = SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.getDefault())

    /**
     * Mengonversi tanggal dari format API (yyyy-MM-dd) ke format yang lebih ramah pengguna (dd MMMM yyyy).
     */
    fun formatDateForDisplay(dateString: String): String {
        return try {
            val date = inputDateFormat.parse(dateString)
            date?.let { displayDateFormat.format(it) } ?: dateString
        } catch (e: ParseException) {
            e.printStackTrace()
            dateString
        }
    }

    /**
     * Mengonversi tanggal dari format yang lebih ramah pengguna (dd MMMM yyyy) ke format API (yyyy-MM-dd).
     */
    fun formatDateForApi(date: Date): String {
        return inputDateFormat.format(date)
    }

    /**
     * Mengonversi tanggal dari format yang lebih ramah pengguna (dd MMMM yyyy) ke format API (yyyy-MM-dd).
     */
    fun parseDateFromDisplayFormat(dateString: String): String {
        return try {
            val date = displayDateFormat.parse(dateString)
            date?.let { inputDateFormat.format(it) } ?: dateString
        } catch (e: ParseException) {
            e.printStackTrace()
            dateString
        }
    }
}
