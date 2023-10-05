package com.example.myapplicationtest.ktx

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

fun <VB : ViewBinding> Activity.inflate(inflater: (LayoutInflater) -> VB) = lazy {
    inflater(layoutInflater).apply { setContentView(root) }
}

fun <VB : ViewBinding> Dialog.inflate(inflater: (LayoutInflater) -> VB) = lazy {
    inflater(layoutInflater).apply { setContentView(root) }
}

