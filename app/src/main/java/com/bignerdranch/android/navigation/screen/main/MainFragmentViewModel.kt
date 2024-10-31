package com.bignerdranch.android.navigation.screen.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bignerdranch.android.navigation.utilits.REPOSITORY

class MainFragmentViewModel(application: Application): AndroidViewModel(application) {
    val allNotes = REPOSITORY.allNotes

    fun signOut() {
        REPOSITORY.signOut()
    }
}