package com.manueldidonna.architex.calculator.ui.funckeyboard

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelView
import com.manueldidonna.architex.calculator.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, fullSpan = true)
class ExtraHeaderEpoxyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        inflate(context, R.layout.epoxy_view_extra_header, this)
    }
}