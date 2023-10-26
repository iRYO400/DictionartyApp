package workshop.akbolatss.dictionartyapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import workshop.akbolatss.dictionartyapp.data.model.WordWithDefinitions
import workshop.akbolatss.dictionartyapp.ui.theme.DictionartyAppTheme

private const val LOADING_DELAY = 1500L

@Composable
fun DictionartyApp(
    uiState: DictionartyScreenState,
    onSubmitQuery: (String) -> Unit = {},
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        var isLoading by remember { mutableStateOf(true) }

        AnimatedVisibility(
            visible = uiState.itemCount != 0 && !isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column {
                SearchBar(onSubmitQuery)
                AnimatedVisibility(visible = uiState.isQueryEmpty == false) {
                    Content(
                        uiState.queryResult,
                    )
                }

                if (uiState.isQueryEmpty == true) {
                    NotFoundScreen()
                }
            }
        }

        if (uiState.itemCount == 0 || isLoading) {
            WelcomeScreen()
        }

        LaunchedEffect(key1 = isLoading) {
            delay(LOADING_DELAY)
            isLoading = false
        }
    }
}

@Composable
private fun WelcomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 8.dp)
            )

            Text(
                text = "Preparing DB. Please wait",
                modifier = Modifier,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun NotFoundScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {

            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(40.dp),
                imageVector = Icons.Outlined.Info,
                contentDescription = null
            )

            Text(
                text = "Nothing found. Type a new word",
                modifier = Modifier,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun Content(
    wordsWithDefinitions: List<WordWithDefinitions>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            vertical = 8.dp
        )
    ) {
        items(wordsWithDefinitions) {
            WordCard(it)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ColumnScope.SearchBar(onSubmitQuery: (String) -> Unit) {
    var queryString by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = if (isActive) 0.dp else 8.dp)
            .padding(vertical = 8.dp),
        query = queryString,
        onQueryChange = { newQueryString ->
            queryString = newQueryString
        },
        onSearch = {
            isActive = false
            onSubmitQuery(it)
            queryString = ""
        },
        active = isActive,
        onActiveChange = { activeChange ->
            isActive = activeChange
        },
        placeholder = {
            Text(text = "Type a word")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = queryString.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }
    ) {
        // TODO suggestions using QUERY LIKE, I guess
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DictionartyAppTheme {
        DictionartyApp(
            uiState = DictionartyScreenState(
                itemCount = 0
            )
        )
    }
}