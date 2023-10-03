package com.natasamisic.mymemos.feature.domain.model

 data class  InvalidMemoException(override val message: String): Exception(message)
