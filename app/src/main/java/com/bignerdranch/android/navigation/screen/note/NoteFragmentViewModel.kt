package com.bignerdranch.android.navigation.screen.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.navigation.models.AppNote
import com.bignerdranch.android.navigation.utilits.REPOSITORY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NoteFragmentViewModel(application: Application): AndroidViewModel(application) {
    fun delete(note: AppNote, onSuccess:() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(note) {
                onSuccess()
            }
        }
    }
}