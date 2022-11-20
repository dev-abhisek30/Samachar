package com.dev.abhisek30.samachar.core.mvi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IModel<S : IState, I : IIntent, E: IEffect> {
    val intents: Channel<I>
    val state: StateFlow<S>
    val effect: Flow<E>

    fun handlerIntent()
}