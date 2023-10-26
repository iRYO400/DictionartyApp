package workshop.akbolatss.dictionartyapp.data.source

interface SharedPrefsWrapper {
    fun isDatabaseReady(): Boolean
    fun setDatabaseAsReady()
}