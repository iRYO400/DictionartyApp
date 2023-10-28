package workshop.akbolatss.dictionartyapp.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import workshop.akbolatss.dictionartyapp.data.source.JsonParser

interface InstantiateDatabaseUseCase {
    suspend operator fun invoke(): Result<Unit>
}

class InstantiateDatabaseUseCaseImpl(
    private val repository: DictionartyRepository,
    private val jsonParser: JsonParser
) : InstantiateDatabaseUseCase {

    override suspend operator fun invoke(): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                if (repository.isDatabaseReady())
                    return@withContext Result.success(Unit)

                val content = jsonParser.getJsonContent()
                repository.prepareDatabase(content)

                return@withContext Result.success(Unit)
            } catch (e: RuntimeException) {
                return@withContext Result.failure(e)
            }
        }
}