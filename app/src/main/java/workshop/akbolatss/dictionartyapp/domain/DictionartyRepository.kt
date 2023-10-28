package workshop.akbolatss.dictionartyapp.domain

import workshop.akbolatss.dictionartyapp.data.model.Word
import workshop.akbolatss.dictionartyapp.data.model.WordWithDefinitions

interface DictionartyRepository {
    suspend fun query(query: String): List<WordWithDefinitions>
    suspend fun isDatabaseReady(): Boolean
    suspend fun prepareDatabase(result: List<Word>)
}