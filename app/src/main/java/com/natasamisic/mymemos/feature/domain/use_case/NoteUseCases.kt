package com.natasamisic.mymemos.feature.domain.use_case


data class MemoUseCases(
    val getMemosUseCase: GetMemosUseCase,
    val deleteMemoUseCase: DeleteMemoUseCase,
    val addMemoUseCase: AddMemoUseCase,
    val getMemoUseCase: GetMemoUseCase
)

