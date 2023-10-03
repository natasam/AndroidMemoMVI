package com.natasamisic.mymemos.feature.domain.usecase


data class MemoUseCases(
    val getMemosUseCase: GetMemosUseCase,
    val deleteMemoUseCase: DeleteMemoUseCase,
    val addMemoUseCase: AddMemoUseCase,
    val getMemoUseCase: GetMemoUseCase)

