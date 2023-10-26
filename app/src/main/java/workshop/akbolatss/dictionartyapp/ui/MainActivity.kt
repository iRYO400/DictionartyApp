package workshop.akbolatss.dictionartyapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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