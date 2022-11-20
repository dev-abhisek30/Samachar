package com.dev.abhisek30.samachar.core.mvi

interface IView<I : IIntent, S : IState, E : IEffect> {
    fun render(state: S)
    fun render(effect: E)
    fun triggerIntent(intent: I)
}