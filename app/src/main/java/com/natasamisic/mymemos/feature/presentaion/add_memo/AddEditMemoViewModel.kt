package com.natasamisic.mymemos.feature.presentaion.add_memo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natasamisic.mymemos.feature.domain.model.InvalidMemoException
import com.natasamisic.mymemos.feature.domain.use_case.MemoUseCases
import com.natasamisic.mymemos.feature.data.data_source.model.Memo
import com.natasamisic.mymemos.feature.domain.util.ColorUtils.memoColors
import com.natasamisic.mymemos.presentaion.add_edit_Memo.MemoTextFeildState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditMemoViewModel @Inject constructor(
    private val MemoUseCases: MemoUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _MemoTitle = mutableStateOf(
        MemoTextFeildState(
            hint = "Enter title..."
        )
    )
    val MemoTitle: State<MemoTextFeildState> = _MemoTitle

    private val _MemoContent = mutableStateOf(
        MemoTextFeildState(
            hint = "Enter some content..."
        )
    )
    val MemoContent: State<MemoTextFeildState> = _MemoContent

    private val _MemoColor = mutableStateOf(memoColors.random().toArgb())
    val MemoColor: State<Int> = _MemoColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentMemoId: Int? = null


    init {
        savedStateHandle.get<Int>("MemoId")?.let { MemoId ->
            if (MemoId != -1) {
                viewModelScope.launch {
                    MemoUseCases.getMemoUseCase(MemoId)?.also { Memo ->
                        currentMemoId = Memo.id
                        _MemoTitle.value = MemoTitle.value.copy(
                            text = Memo.title,
                            isHintVisible = false
                        )
                        _MemoContent.value = MemoContent.value.copy(
                            text = Memo.text,
                            isHintVisible = false
                        )
                        _MemoColor.value = Memo.colorPriority
                    }
                }
            }
        }
    }


    fun onEvent(event: AddEditMemoEvent) {
        when (event) {
            is AddEditMemoEvent.ChangeColor -> {
                _MemoColor.value = event.color
            }
            is AddEditMemoEvent.ChangedContentFocused -> {
                _MemoContent.value = MemoContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && MemoContent.value.text.isBlank()
                )
            }
            is AddEditMemoEvent.ChangedTitleFocused -> {
                _MemoTitle.value = MemoTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && MemoTitle.value.text.isBlank()
                )
            }
            is AddEditMemoEvent.EnteredContent -> {
                _MemoContent.value = MemoContent.value.copy(
                    text = event.value
                )
            }
            is AddEditMemoEvent.EnteredTitle -> {
                _MemoTitle.value = MemoTitle.value.copy(
                    text = event.value
                )
            }
            AddEditMemoEvent.SaveMemo -> {
                viewModelScope.launch {
                    try {
                        MemoUseCases.addMemoUseCase(
                            Memo(
                                title = MemoTitle.value.text,
                                text = MemoContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                colorPriority = MemoColor.value,
                                id = currentMemoId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveMemo)
                    } catch (e: InvalidMemoException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't save Memo!"
                            )
                        )
                    }
                }
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveMemo : UiEvent()
    }

}


