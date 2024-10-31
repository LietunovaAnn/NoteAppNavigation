package com.bignerdranch.android.navigation.database

import androidx.lifecycle.LiveData
import com.bignerdranch.android.navigation.models.AppNote

interface DatabaseRepository {
    val allNotes: LiveData<List<AppNote>>
    suspend fun insert(note: AppNote, onSuccess:()->Unit)
    suspend fun delete(note: AppNote, onSuccess:()->Unit)

    fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit){}
    fun signOut(){}
}