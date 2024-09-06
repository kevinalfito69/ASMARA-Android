import android.content.Context
import android.content.SharedPreferences

object PreferencesUtils {

    private const val PREFS_NAME = "app_prefs"
    private const val KEY_NIK = "NIK"
    private const val KEY_TOKEN = "TOKEN"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Menyimpan atau Memperbarui NIK
    fun saveOrUpdateNIK(context: Context, nik: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NIK, nik)
        editor.apply() // Simpan perubahan secara asinkron
    }

    // Mengambil NIK
    fun getNIK(context: Context): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(KEY_NIK, null)
    }

    // Menghapus NIK
    fun clearNIK(context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.remove(KEY_NIK)
        editor.apply() // Simpan perubahan secara asinkron
    }

    // Menyimpan atau Memperbarui Token
    fun saveOrUpdateToken(context: Context, token: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply() // Simpan perubahan secara asinkron
    }

    // Mengambil Token
    fun getToken(context: Context): String? {
        val sharedPreferences = getSharedPreferences(context)
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    // Menghapus Token
    fun clearToken(context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.remove(KEY_TOKEN)
        editor.apply() // Simpan perubahan secara asinkron
    }
}
