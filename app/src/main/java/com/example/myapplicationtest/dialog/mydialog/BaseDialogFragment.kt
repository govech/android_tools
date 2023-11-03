package com.example.myapplicationtest.dialog.mydialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import click
import com.example.myapplicationtest.R

class BaseDialogFragment(
    private val layoutresId: Int = R.layout.dialog_base
) : DialogFragment() {

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    interface DialogListener {
        fun onPositiveButtonClick()
        fun onNegativeButtonClick()
    }

    private var listener: DialogListener? = null

    fun setDialogListener(listener: DialogListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        isCancelable = true

    }

    // 重写onCreateView方法，使用LayoutInflater.inflate方法加载布局
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutresId, container, false)
        val args = arguments
        val title = args?.getString("title", "Default Title")
        val message = args?.getString("message", "Default Message")
        val positiveButtonText = args?.getString("positiveButtonText", "OK")
        val negativeButtonText = args?.getString("negativeButtonText", "Cancel")
        mWidth = args?.getInt("width", ViewGroup.LayoutParams.MATCH_PARENT)
            ?: ViewGroup.LayoutParams.MATCH_PARENT
        mHeight = args?.getInt("height", ViewGroup.LayoutParams.WRAP_CONTENT)
            ?: ViewGroup.LayoutParams.WRAP_CONTENT


        view.run {
            val titleTextView = findViewById<TextView>(R.id.tv_dialog_title)
            titleTextView.text = title
            val contentTextView = findViewById<TextView>(R.id.tv_dialog_content)
            titleTextView.text = message
            val btcLeft = findViewById<TextView>(R.id.btc_dialog_left).apply {
                click {
                    listener?.onNegativeButtonClick()
                    dismiss()
                }
            }
            val btcRight = findViewById<TextView>(R.id.btc_dialog_right).apply {
                click {
                    listener?.onPositiveButtonClick()
                    dismiss()
                }
            }

        }

        return view
    }

    override fun onStart() {
        super.onStart()
//        setupDialog()
    }

    // 定义一个open方法，用于设置对话框的样式和属性
    open fun setupDialog() {
        // 设置对话框的背景为透明色,这一步必须设置
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog?.window?.decorView?.setPadding(100, 0, 100, 0)


        dialog?.window?.let {
            // 设置对话框的位置和大小
            val wlp = it.attributes
//            wlp.gravity = Gravity.BOTTOM
            wlp.width = mWidth
            wlp.height = mHeight
//            wlp.horizontalMargin=100f
            it.attributes = wlp
        }
        // 设置对话框的动画效果
//        dialog.window?.setWindowAnimations(R.style.DialogAnimation)
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDialog()
    }
}