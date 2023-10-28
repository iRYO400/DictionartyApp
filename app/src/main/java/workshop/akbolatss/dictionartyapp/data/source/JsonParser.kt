package workshop.akbolatss.dictionartyapp.data.source

import workshop.akbolatss.dictionartyapp.data.model.Word

interface JsonParser {
    fun getJsonContent(): List<Word>
}