package com.bignerdranch.android.navigation.database.firebase

import androidx.lifecycle.LiveData
import com.bignerdranch.android.navigation.database.DatabaseRepository
import com.bignerdranch.android.navigation.models.AppNote
import com.bignerdranch.android.navigation.utilits.AUTH
import com.bignerdranch.android.navigation.utilits.AppPreference
import com.bignerdranch.android.navigation.utilits.CURRENT_ID
import com.bignerdranch.android.navigation.utilits.EMAIL
import com.bignerdranch.android.navigation.utilits.ID_FIREBASE
import com.bignerdranch.android.navigation.utilits.NAME
import com.bignerdranch.android.navigation.utilits.PASSWORD
import com.bignerdranch.android.navigation.utilits.REF_DATABASE
import com.bignerdranch.android.navigation.utilits.TEXT
import com.bignerdranch.android.navigation.utilits.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AppFirebaseRepository : DatabaseRepository {

    init {
        AUTH = FirebaseAuth.getInstance()
    }

    override val allNotes: LiveData<List<AppNote>> = AllNoteLiveData()

    override suspend fun insert(note: AppNote, onSuccess: () -> Unit) {
        val idNote = REF_DATABASE.push().key.toString()
        val mapNote = hashMapOf<String, Any>()
        mapNote[ID_FIREBASE] = idNote
        mapNote[NAME] = note.name
        mapNote[TEXT] = note.text

        REF_DATABASE.child(idNote)
            .updateChildren(mapNote)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message.toString()) }


    }

    override suspend fun delete(note: AppNote, onSuccess: () -> Unit) {
        REF_DATABASE.child(note.idFirebase).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        if (AppPreference.getInitUser()) {
            initRefs()
            onSuccess()
        } else {
            AUTH.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnSuccessListener {
                    initRefs()
                    onSuccess() }
                .addOnFailureListener {
                    AUTH.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                        .addOnSuccessListener {
                            initRefs()
                            onSuccess() }
                        .addOnFailureListener { onFail(it.message.toString()) }
                }
        }
    }

    private fun initRefs() {
        CURRENT_ID = AUTH.currentUser?.uid.toString()
        REF_DATABASE = FirebaseDatabase.getInstance().reference
            .child(CURRENT_ID)
    }

    override fun signOut() {
        AUTH.signOut()
    }
}