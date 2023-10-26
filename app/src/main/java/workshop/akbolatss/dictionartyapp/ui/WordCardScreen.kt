package workshop.akbolatss.dictionartyapp.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import workshop.akbolatss.dictionartyapp.data.model.DefinitionEntity
import workshop.akbolatss.dictionartyapp.data.model.WordEntity
import workshop.akbolatss.dictionartyapp.data.model.WordWithDefinitions
import workshop.akbolatss.dictionartyapp.ui.theme.DictionartyAppTheme

@Composable
fun WordCard(
    wordWithDefinitions: WordWithDefinitions
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colors.surface,
        ),
        border = BorderStroke(0.5.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            WordWithPos(wordWithDefinitions)
            Definitions(wordWithDefinitions)
            Synonyms(wordWithDefinitions)
        }
    }
}

@Composable
private fun WordWithPos(wordWithDefinitions: WordWithDefinitions) {
    val wordWithPosText = buildAnnotatedString {
        withStyle(
            style = LocalTextStyle.current.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ).toSpanStyle()
        ) {
            append(wordWithDefinitions.word.word.lowercase())
        }
        append(" ( ")
        withStyle(
            style = LocalTextStyle.current.copy(
                fontFamily = FontFamily.Cursive,
                fontSize = 20.sp
            ).toSpanStyle()
        ) {
            append(wordWithDefinitions.word.pos)
        }
        append(") ")
    }
    Text(
        text = wordWithPosText,
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Start,
    )
}

@Composable
private fun Definitions(wordWithDefinitions: WordWithDefinitions) {
    wordWithDefinitions.definitions.forEachIndexed { index, definitionEntity ->
        val definitionText = buildAnnotatedString {
            withStyle(
                style = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.SemiBold
                ).toSpanStyle()
            ) {
                val idx = index + 1
                append("$idx. ")
            }
            append(definitionEntity.value)
        }
        Text(
            text = definitionText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Start,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun Synonyms(wordWithDefinitions: WordWithDefinitions) {
    val synonyms = wordWithDefinitions.word.synonyms
    if (synonyms != null) {
        Divider(
            thickness = DividerDefaults.Thickness,
            modifier = Modifier
                .padding(vertical = 4.dp)
        )
        val synonymsText = buildAnnotatedString {
            withStyle(
                style = LocalTextStyle.current.copy(
                    fontFamily = FontFamily.Monospace
                ).toSpanStyle()
            ) {
                append(synonyms.lowercase())
            }
        }
        Text(
            text = synonymsText,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
        )
    }
}

@Preview
@Composable
fun WordCardPreview() {
    DictionartyAppTheme {
        WordCard(
            wordWithDefinitions = WordWithDefinitions(
                word = WordEntity(
                    wordId = 0,
                    pos = "n.",
                    word = "ABBREVIATE",
                    synonyms = "Boombastik"
                ),
                definitions = listOf(
                    DefinitionEntity(
                        value = "An abridgment. [Obs.] Elyot. An abridgment. [Obs.] Elyot. An abridgment. [Obs.] Elyot. An abridgment. [Obs.] Elyot. An abridgment. [Obs.] Elyot.",
                        parentId = 0
                    ),
                    DefinitionEntity(
                        value = "An abridgment. [Obs.] Elyot.",
                        parentId = 0
                    )
                )
            )
        )
    }
}
