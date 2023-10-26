package workshop.akbolatss.dictionartyapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Word(
    val pos: String,
    val word: String,
    val definitions: List<String>,
    val synonyms: String?
)