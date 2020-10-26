package com.example.boxowl.bases

import com.example.boxowl.NavigationManager


interface HasNavigationManager {
    fun provideNavigationManager(): NavigationManager
}