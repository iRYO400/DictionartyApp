package workshop.akbolatss.dictionartyapp.data

import workshop.akbolatss.dictionartyapp.data.model.DefinitionEntity
import workshop.akbolatss.dictionartyapp.data.model.Word
import workshop.akbolatss.dictionartyapp.data.model.WordEntity
import workshop.akbolatss.dictionartyapp.data.source.WordDao
import workshop.akbolatss.dictionartyapp.domain.DictionartyRepository

class DictionartyRepositoryImpl(
    private val dao: WordDao
) : DictionartyRepository {

    override suspend fun isDatabaseReady() = dao.wordsCount() > 0

    override suspend fun prepareDatabase(result: List<Word>) {
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

    override suspend fun query(query: String) = dao.queryWords(query)
}