package workshop.akbolatss.dictionartyapp.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "word"
)
data class WordEntity(
    @PrimaryKey
    val wordId: Int,
    val pos: String,
    val word: String,
    val synonyms: String?
)

@Entity(
    tableName = "definition"
)
data class DefinitionEntity(
    val value: String,
    val parentId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var definitionId: Int = 0
}

data class WordWithDefinitions(
    @Embedded
    val word: WordEntity,
    @Relation(
        parentColumn = "wordId",
        entityColumn = "parentId"
    )
    val definitions: List<DefinitionEntity>
)