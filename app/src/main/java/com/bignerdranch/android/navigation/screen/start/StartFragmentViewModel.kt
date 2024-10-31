package com.bignerdranch.android.navigation.screen.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bignerdranch.android.navigation.database.firebase.AppFirebaseRepository
import com.bignerdranch.android.navigation.database.room.AppRoomDatabase
import com.bignerdranch.android.navigation.database.room.AppRoomRepository
import com.bignerdranch.android.navigation.utilits.REPOSITORY
import com.bignerdranch.android.navigation.utilits.TYPE_FIREBASE
import com.bignerdranch.android.navigation.utilits.TYPE_ROOM
import com.bignerdranch.android.navigation.utilits.showToast

class StartFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val mContext = application

    fun initDatabase(type: String, onSuccess:()->Unit){
        when(type){
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(mContext).getAppRoomDao()
                REPOSITORY = AppRoomRepository(dao)
                onSuccess()
            }
            TYPE_FIREBASE -> {
                REPOSITORY = AppFirebaseRepository()
                REPOSITORY.connectToDatabase({onSuccess()}, { showToast(it) })
            }
        }

    }
}