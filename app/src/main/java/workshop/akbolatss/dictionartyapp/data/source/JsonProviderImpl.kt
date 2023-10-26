package workshop.akbolatss.dictionartyapp.data.source

import android.content.Context
import java.io.IOException

class JsonProviderImpl(
    private val context: Context
) : JsonProvider {

    companion object {
        private const val FILE_NAME = "dictionary.json"
    }

    override fun getJsonContent(): String = try {
        context.assets.open(FILE_NAME)
            .bufferedReader()
            .use { it.readText() }
    } catch (e: IOException) {
        throw RuntimeException("JSON NOT FOUND, CHECK ASSETS")
    }
}