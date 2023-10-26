package workshop.akbolatss.dictionartyapp.ui

import workshop.akbolatss.dictionartyapp.data.model.WordWithDefinitions

data class DictionartyScreenState(
    val itemCount: Int = 0,
    val queryResult: List<WordWithDefinitions> = emptyList(),
    val isQueryEmpty: Boolean? = null,
    val errorMessage: String? = null,
    val databaseCorrupted: Boolean? = null
) {
    companion object {
        fun default() = DictionartyScreenState()
    }
}