package workshop.akbolatss.dictionartyapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import workshop.akbolatss.dictionartyapp.data.DictionartyRepository

class DictionartyViewModel(
    private val repository: DictionartyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DictionartyScreenState.default())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                repository.initDatabase()
            } catch (e: RuntimeException) {
                _uiState.update {
                    it.copy(databaseCorrupted = true) //TODO need to handle this state
                }
            }
        }
        viewModelScope.launch {
            repository.currentCount().collect { itemCount ->
                _uiState.update {
                    it.copy(itemCount = itemCount)
                }
            }
        }
    }

    fun onSubmitQuery(query: String) {
        viewModelScope.launch {
            val result = repository.query(query)
            _uiState.update {
                it.copy(
                    queryResult = result,
                    isQueryEmpty = result.isEmpty()
                )
            }
        }
    }
}