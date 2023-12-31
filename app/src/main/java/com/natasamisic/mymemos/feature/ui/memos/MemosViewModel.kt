package com.natasamisic.mymemos.feature.ui.memos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natasamisic.mymemos.feature.domain.model.MemoDto
import com.natasamisic.mymemos.feature.domain.usecase.MemoUseCases
import com.natasamisic.mymemos.feature.domain.util.MemoSortType
import com.natasamisic.mymemos.feature.domain.util.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemosViewModel @Inject constructor(
    private val memoUseCases: MemoUseCases
) : ViewModel() {

    private val _state = mutableStateOf(MemoState())
    val state: State<MemoState> = _state

    private var recentlyDeletedMemo: MemoDto? = null

    private var getMemosJob: Job? = null
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getMemos(MemoSortType.Date(SortType.Descending))
    }

    fun onEvent(event: MemosEvent) {
        when (event) {
            is MemosEvent.Sort -> {
                if (
                    state.value.memoOrder::class == event.order::class
                    && state.value.memoOrder.sortType == event.order.sortType
                ) {
                    return
                }
                getMemos(event.order)

            }

            is MemosEvent.DeleteMemo -> {
                recentlyDeletedMemo = event.memo
                viewModelScope.launch {
                    memoUseCases.deleteMemoUseCase(event.memo)
                }
            }

            MemosEvent.RestoreMemo -> {
                viewModelScope.launch {
                    memoUseCases.addMemoUseCase(recentlyDeletedMemo ?: return@launch)
                    recentlyDeletedMemo = null
                }
            }

            MemosEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }

            is MemosEvent.EditMemo -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.EditMemo(event.memo))

                }
            }

        }
    }

    private fun getMemos(memoSortType: MemoSortType) {
        getMemosJob?.cancel()
        getMemosJob = memoUseCases.getMemosUseCase(memoSortType).onEach { list ->
            _state.value = state.value.copy(
                memos = list,
                memoOrder = memoSortType
            )
        }.launchIn(viewModelScope)
    }

}

sealed class UiEvent {
    data class EditMemo(val memoDto: MemoDto) : UiEvent()
}