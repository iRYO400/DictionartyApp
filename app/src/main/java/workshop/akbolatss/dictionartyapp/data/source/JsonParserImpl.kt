package workshop.akbolatss.dictionartyapp.data.source

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import workshop.akbolatss.dictionartyapp.data.model.Word
import java.io.IOException

class JsonParserImpl(
    private val context: Context,
    private val moshi: Moshi
) : JsonParser {

    companion object {
        private const val FILE_NAME = "dictionary.json"
    }

    /**
     * Parse json content using Moshi to data classes
     * @throws RuntimeException if content is empty or null,
     * because app is useless without these data
     */
    override fun getJsonContent(): List<Word> {
        val jsonString = getJsonString()

        val listType = Types.newParameterizedType(List::class.java, Word::class.java)
        val adapter: JsonAdapter<List<Word>> = moshi.adapter(listType)
        val content = adapter.fromJson(jsonString)

        if (content.isNullOrEmpty())
            throw RuntimeException("Json data was empty or null")

        return content
    }

    private fun getJsonString() = try {
        context.assets.open(FILE_NAME)
            .bufferedReader()
            .use { it.readText() }
    } catch (e: IOException) {
        throw RuntimeException("JSON NOT FOUND, CHECK ASSETS")
    }
}