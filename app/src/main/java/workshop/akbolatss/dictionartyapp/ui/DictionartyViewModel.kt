package workshop.akbolatss.dictionartyapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import workshop.akbolatss.dictionartyapp.domain.InstantiateDatabaseUseCase
import workshop.akbolatss.dictionartyapp.domain.QueryWordUseCase

class DictionartyViewModel(
    private val instantiateDatabaseUseCase: InstantiateDatabaseUseCase,
    private val queryWordUseCase: QueryWordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DictionartyScreenState.default())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            instantiateDatabaseUseCase.invoke()
                .onSuccess {
                    _uiState.update {
                        it.copy(isReady = true)
                    }
                }
                .onFailure {
                    _uiState.update {
                        it.copy(errorMessage = "Database corrupted, please reinstall app")
                    }
                }
        }
    }

    fun onSubmitQuery(query: String) {
        viewModelScope.launch {
            val result = queryWordUseCase.invoke(query)
            _uiState.update {
                it.copy(
                    queryResult = result,
                    isQueryResult = true
                )
            }
        }
    }
}