package com.example.myapplication

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Event
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LCViewModel @Inject constructor(
    val auth: FirebaseAuth
) : ViewModel() {
    init {

    }
    var inProgress = false
    var eventMutableState = mutableStateOf<Event<String>?>(null)
    fun signUP(name: String, number: Number, email: String, password: String) {
        inProgress = true

        fun handleException(exception: Exception? = null, customMessage: String = "") {
            Log.d("LiveChatApp", "live chat exception: ${exception?.message}")
            exception?.printStackTrace()
            val errorMsg = exception?.localizedMessage ?: ""
            val message = if (customMessage.isEmpty()) errorMsg else customMessage

            eventMutableState.value = Event(message)
            inProgress = false
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("LCViewModel", "User created successfully")
            } else {
                Log.d("LCViewModel", "User creation failed: ${task.exception?.message}")
            }
        }
    }
}

