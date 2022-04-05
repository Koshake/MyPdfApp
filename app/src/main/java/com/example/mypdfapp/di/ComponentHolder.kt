package com.example.mypdfapp.di

interface ComponentHolder<out Component> {
    fun provideComponent(): Component

    fun onDependencyReleased()
}