package workshop.akbolatss.dictionartyapp.data.source

import android.content.SharedPreferences

class SharedPrefsWrapperImpl(
    private val sharedPreferences: SharedPreferences
) : SharedPrefsWrapper {

    companion object {
        private const val IS_DB_READY = "IS_DB_READY"
    }

    override fun isDatabaseReady(): Boolean =
        sharedPreferences.getBoolean(IS_DB_READY, false)

    override fun setDatabaseAsReady() =
        sharedPreferences.edit().putBoolean(IS_DB_READY, true).apply()
}