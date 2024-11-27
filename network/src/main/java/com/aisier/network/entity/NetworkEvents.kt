package com.aisier.network.entity

import kotlinx.coroutines.flow.MutableSharedFlow

object NetworkEvents {
    val errorFlow = MutableSharedFlow<String>()
}
