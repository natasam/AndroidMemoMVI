package com.natasamisic.mymemos.feature.ui.add_memo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natasamisic.mymemos.feature.domain.model.InvalidMemoException
import com.natasamisic.mymemos.feature.domain.usecase.MemoUseCases
import com.natasamisic.mymemos.feature.domain.model.MemoDto
import com.natasamisic.mymemos.feature.domain.util.ColorUtils.memoColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditMemoViewModel @Inject constructor(
    private val memoUseCases: MemoUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _memoTitle = mutableStateOf(MemoTextFieldState(hint = "Enter title..."))
    val memoTitle: State<MemoTextFieldState> = _memoTitle

    private val _memoContent = mutableStateOf(MemoTextFieldState(hint = "Enter text..."))
    val memoContent: State<MemoTextFieldState> = _memoContent

    private val _memoColor = mutableStateOf(memoColors.random().toArgb())
    val memoColor: State<Int> = _memoColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentMemoId: Int? = null

    init {
        savedStateHandle.get<Int>("memoId")?.let { memoId ->
            if (memoId != -1) {
                viewModelScope.launch {
                    memoUseCases.getMemoUseCase(memoId)?.also { memo ->
                        currentMemoId = memo.id
                        _memoTitle.value = memoTitle.value.copy(text = memo.title, isHintVisible = false)
                        _memoContent.value = memoContent.value.copy(text = memo.text, isHintVisible = false)
                        _memoColor.value = memo.colorPriority
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditMemoEvent) {
        when (event) {
            is AddEditMemoEvent.ChangeColor -> _memoColor.value = event.color
            is AddEditMemoEvent.ChangedContentFocused -> updateHintVisibility(_memoContent, memoContent, event.focusState)
            is AddEditMemoEvent.ChangedTitleFocused -> updateHintVisibility(_memoTitle, memoTitle, event.focusState)
            is AddEditMemoEvent.EnteredContent -> _memoContent.value = memoContent.value.copy(text = event.value)
            is AddEditMemoEvent.EnteredTitle -> _memoTitle.value = memoTitle.value.copy(text = event.value)
            AddEditMemoEvent.SaveMemo -> saveMemo()
        }
    }

    private fun updateHintVisibility(state: MutableState<MemoTextFieldState>, currentState: State<MemoTextFieldState>, focusState: FocusState) {
        state.value = currentState.value.copy(isHintVisible = !focusState.isFocused && currentState.value.text.isBlank())
    }

    private fun saveMemo() {
        viewModelScope.launch {
            try {
                memoUseCases.addMemoUseCase(
                    MemoDto(
                        title = memoTitle.value.text,
                        text = memoContent.value.text,
                        timestamp = System.currentTimeMillis(),
                        colorPriority = memoColor.value,
                        id = currentMemoId
                    )
                )
                _eventFlow.emit(UiEvent.SaveMemo)
            } catch (e: InvalidMemoException) {
                _eventFlow.emit(UiEvent.ShowSnackBar(message = e.message))
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveMemo : UiEvent()
    }
}
