package workshop.akbolatss.dictionartyapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.viewmodel.ext.android.viewModel
import workshop.akbolatss.dictionartyapp.ui.theme.DictionartyAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: DictionartyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionartyAppTheme {
                val uiState by viewModel.uiState.collectAsState()

                DictionartyApp(
                    uiState,
                    viewModel::onSubmitQuery
                )
            }
        }
    }
}

@Composable
fun DictionartyApp(
    uiState: DictionartyScreenState,
    onSubmitQuery: (String) -> Unit = {},
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        AnimatedVisibility(
            visible = uiState.isReady,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            WordsListScreen(uiState, onSubmitQuery)
        }

        if (!uiState.isReady) {
            WelcomeScreen(uiState.errorMessage)
        }
    }
}
