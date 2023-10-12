package com.example.myapplicationtest.dialog

import android.content.Context
import android.view.View
import android.view.animation.Animation
import com.example.myapplicationtest.R
import com.example.myapplicationtest.databinding.DialogSimpleBinding
import com.example.myapplicationtest.extensions.screenHeight
import razerdp.basepopup.BasePopupWindow
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

class SimpleDialog(context: Context?, block: () -> Unit) : BasePopupWindow(context) {
    private lateinit var mBinding: DialogSimpleBinding

    init {
        val view = createPopupById(R.layout.dialog_simple)
        contentView = view
//        setBackground(0)
        setViewClickListener({
            block()
        }, mBinding.tvName)

    }


    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        mBinding = DialogSimpleBinding.bind(contentView)
    }

    override fun setViewClickListener(listener: View.OnClickListener?, vararg views: View?) {
        super.setViewClickListener(listener, *views)
    }

    override fun onCreateShowAnimation(): Animation {
//        return super.onCreateShowAnimation()
        return AnimationHelper.asAnimation()
            .withTranslation(TranslationConfig.FROM_BOTTOM.fromY(screenHeight - height))
            .toShow()
    }
}
