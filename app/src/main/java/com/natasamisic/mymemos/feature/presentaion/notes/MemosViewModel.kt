package com.natasamisic.mymemos.feature.presentaion.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natasamisic.mymemos.feature.data.data_source.model.Memo
import com.natasamisic.mymemos.feature.domain.use_case.MemoUseCases
import com.natasamisic.mymemos.feature.domain.util.MemoSortType
import com.natasamisic.mymemos.feature.domain.util.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    private var recentlyDeletedMemo: Memo? = null

    private var getMemosJob: Job? = null

    init {
        getMemos(MemoSortType.Date(SortType.Descending))
    }

    fun onEvent(event: MemosEvent) {
        when (event) {
            is MemosEvent.Order -> {
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