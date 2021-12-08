package com.jenni.jeezyfashion.util

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


object Constants {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val db = Firebase.firestore
}