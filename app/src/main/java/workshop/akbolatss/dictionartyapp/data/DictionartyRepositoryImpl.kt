package workshop.akbolatss.dictionartyapp.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import workshop.akbolatss.dictionartyapp.data.model.DefinitionEntity
import workshop.akbolatss.dictionartyapp.data.model.Word
import workshop.akbolatss.dictionartyapp.data.model.WordEntity
import workshop.akbolatss.dictionartyapp.data.source.JsonProvider
import workshop.akbolatss.dictionartyapp.data.source.SharedPrefsWrapper
import workshop.akbolatss.dictionartyapp.data.source.WordDao

class DictionartyRepositoryImpl(
    private val dao: WordDao,
    private val moshi: Moshi,
    private val jsonProvider: JsonProvider,
    private val sharedPrefsWrapper: SharedPrefsWrapper
) : DictionartyRepository {

    override suspend fun initDatabase() = withContext(Dispatchers.IO) {
        if (sharedPrefsWrapper.isDatabaseReady())
            return@withContext

        val content = parseJson()

        prepopulatedDatabase(content)
        sharedPrefsWrapper.setDatabaseAsReady()
    }

    /**
     * Parse json content using Moshi to data classes
     * @throws RuntimeException if content is empty or null,
     * because app is useless without these data
     */
    private fun parseJson(): List<Word> {
        val listType = Types.newParameterizedType(List::class.java, Word::class.java)
        val adapter: JsonAdapter<List<Word>> = moshi.adapter(listType)

        val jsonString = jsonProvider.getJsonContent()
        val content = adapter.fromJson(jsonString)

        if (content.isNullOrEmpty())
            throw RuntimeException("Json data was empty or null")

        return content
    }

    /**
     * Insert all data to RoomDB as single transaction.
     */
    private suspend fun prepopulatedDatabase(result: List<Word>) {
        val mapped = result.mapIndexed { index, word ->
            WordEntity(
                wordId = index,
                pos = word.pos,
                word = word.word,
                synonyms = word.synonyms
            ) to word.definitions.map { DefinitionEntity(it, index) }
        }

        val words = mapped.map { it.first }
        val definitions = mapped.map { it.second }.flatten()

        dao.insertWordsWithDefinitions(words, definitions)
    }

    override fun currentCount(): Flow<Int> = dao.getWordsCount()
    override suspend fun query(query: String) = dao.queryWords(query)
}