package workshop.akbolatss.dictionartyapp.data

import kotlinx.coroutines.flow.Flow
import workshop.akbolatss.dictionartyapp.data.model.WordWithDefinitions

interface DictionartyRepository {
    suspend fun initDatabase()
    fun currentCount(): Flow<Int>
    suspend fun query(query: String): List<WordWithDefinitions>
}