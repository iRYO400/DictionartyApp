package workshop.akbolatss.dictionartyapp.domain

import workshop.akbolatss.dictionartyapp.data.model.WordWithDefinitions

interface QueryWordUseCase {
    suspend operator fun invoke(query: String): List<WordWithDefinitions>
}

class QueryWordUseCaseImpl(
    private val repository: DictionartyRepository
) : QueryWordUseCase {

    override suspend operator fun invoke(query: String) = repository.query(query)
}