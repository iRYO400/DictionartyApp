package workshop.akbolatss.dictionartyapp.ui

import workshop.akbolatss.dictionartyapp.data.model.WordWithDefinitions

data class DictionartyScreenState(
    val isReady: Boolean = false,
    val queryResult: List<WordWithDefinitions> = emptyList(),
    val isQueryResult: Boolean = false,
    val errorMessage: String? = null
) {
    companion object {
        fun default() = DictionartyScreenState()
    }

    fun isEmpty() = queryResult.isEmpty()
}