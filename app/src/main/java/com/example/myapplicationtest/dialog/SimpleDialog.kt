package com.example.myapplicationtest.dialog

import android.content.Context
import android.view.animation.Animation
import com.example.myapplicationtest.R
import com.example.myapplicationtest.extensions.screenHeight
import razerdp.basepopup.BasePopupWindow
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

class SimpleDialog(context: Context?) : BasePopupWindow(context) {
    init {
        contentView = createPopupById(R.layout.dialog_simple)
//        setBackground(0)
    }

    override fun onCreateShowAnimation(): Animation {
//        return super.onCreateShowAnimation()
        return AnimationHelper.asAnimation()
            .withTranslation(TranslationConfig.FROM_BOTTOM.fromY(screenHeight - height))
            .toShow()
    }
}