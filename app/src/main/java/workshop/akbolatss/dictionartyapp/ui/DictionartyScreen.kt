package workshop.akbolatss.dictionartyapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import workshop.akbolatss.dictionartyapp.data.model.WordWithDefinitions
import workshop.akbolatss.dictionartyapp.ui.theme.DictionartyAppTheme


@Composable
fun WordsListScreen(
    uiState: DictionartyScreenState,
    onSubmitQuery: (String) -> Unit = {}
) {
    var isSearchActive by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SearchBarWidget(
                isSearchActive,
                onSearchStateChanged = {
                    isSearchActive = it
                },
                onSubmitQuery
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isSearchActive = true
                }
            ) {
                Icon(Icons.Outlined.Search, null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            AnimatedVisibility(visible = !uiState.isEmpty()) {
                WordListWidget(uiState.queryResult)
            }

            if (uiState.isEmpty()) {
                EmptyDataWidget(uiState.isQueryResult)
            }
        }
    }
}

@Composable
fun WelcomeScreen(errorMessage: String?) {
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
                text = errorMessage ?: "Preparing DB. Please wait",
                modifier = Modifier,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun EmptyDataWidget(isQueryResult: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {

            val vector =
                if (isQueryResult) Icons.Outlined.Info
                else Icons.Outlined.Search
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(40.dp),
                imageVector = vector,
                contentDescription = null
            )

            val text =
                if (isQueryResult) "Nothing found. Type a new word"
                else "Click FAB to learn new about words"
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun WordListWidget(
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
            WordItemWidget(it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBarWidget(
    isActive: Boolean,
    onSearchStateChanged: (Boolean) -> Unit = {},
    onSubmitQuery: (String) -> Unit = {}
) {
    var queryString by remember { mutableStateOf("") }

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
            onSearchStateChanged(false)
            onSubmitQuery(it)
            queryString = ""
        },
        active = isActive,
        onActiveChange = { activeChange ->
            onSearchStateChanged(activeChange)
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
    ) {}
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DictionartyAppTheme {
        DictionartyApp(
            uiState = DictionartyScreenState(
                isReady = false
            )
        )
    }
}