package com.example.myapplicationtest.loading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.example.myapplicationtest.R

class LoadingViewDelegate : LoadingStateView.ViewDelegate(ViewType.LOADING) {
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
        return inflater.inflate(R.layout.loading, parent, false)
    }
}