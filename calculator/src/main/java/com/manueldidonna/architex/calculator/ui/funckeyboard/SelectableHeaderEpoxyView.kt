package com.manueldidonna.architex.calculator.ui.funckeyboard

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architexcalc.core.extensions.setSelectableBackground

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, fullSpan = true)
class SelectableHeaderEpoxyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val headerTitle: TextView

    init {
        inflate(context, R.layout.epoxy_view_selectable_header, this)
        setSelectableBackground()
        headerTitle = findViewById(R.id.header_title)
    }

    @TextProp
    fun setHeaderTitle(title: CharSequence) {
        headerTitle.text = title
    }

    @CallbackProp
    fun setListener(listener: OnClickListener?) {
        setOnClickListener(listener)
    }
}