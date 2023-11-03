package com.example.myapplicationtest.dialog.mydialog

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.myapplicationtest.R

class CustomDialogBuilder() {

    private var title: String = ""
    private var message: String = ""
    private var positiveButtonText: String = ""
    private var negativeButtonText: String = ""
    private var width: Int = ViewGroup.LayoutParams.MATCH_PARENT
    private var height: Int = ViewGroup.LayoutParams.WRAP_CONTENT
    private var xPosition: Int = 0
    private var yPosition: Int = 0
    private var listener: BaseDialogFragment.DialogListener? = null

    fun setTitle(title: String): CustomDialogBuilder {
        this.title = title
        return this
    }

    fun setMessage(message: String): CustomDialogBuilder {
        this.message = message
        return this
    }

    fun setPositiveButtonText(text: String): CustomDialogBuilder {
        this.positiveButtonText = text
        return this
    }

    fun setNegativeButtonText(text: String): CustomDialogBuilder {
        this.negativeButtonText = text
        return this
    }

    fun setWidth(width: Int): CustomDialogBuilder {
        this.width = width
        return this
    }

    fun setHeight(height: Int): CustomDialogBuilder {
        this.height = height
        return this
    }

    fun setPosition(x: Int, y: Int): CustomDialogBuilder {
        this.xPosition = x
        this.yPosition = y
        return this
    }

    fun setListener(listener: BaseDialogFragment.DialogListener): CustomDialogBuilder {
        this.listener = listener
        return this
    }

    fun build(): BaseDialogFragment {
        val customDialog = BaseDialogFragment()
        val args = Bundle()
        args.putString("title", title)
        args.putString("message", message)
        args.putString("positiveButtonText", positiveButtonText)
        args.putString("negativeButtonText", negativeButtonText)
        args.putInt("width", width)
        args.putInt("height", height)
        args.putInt("xPosition", xPosition)
        args.putInt("yPosition", yPosition)
        customDialog.arguments = args
        listener?.let { customDialog.setDialogListener(it) }
        return customDialog
    }

    fun show(fragmentManager: FragmentManager, tag: String) {
        build().show(fragmentManager, tag)
    }
}