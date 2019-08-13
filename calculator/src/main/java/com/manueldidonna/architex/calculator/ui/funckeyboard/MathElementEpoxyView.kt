package com.manueldidonna.architex.calculator.ui.funckeyboard

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architexcalc.common.ui.extensions.setSelectableBackground

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, fullSpan = false)
class MathElementEpoxyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val mathElementTextView: AppCompatTextView

    init {
        inflate(context, R.layout.epoxy_view_math_element, this)
        setSelectableBackground()
        mathElementTextView = findViewById(R.id.math_element_name)
    }

    @TextProp
    fun setElementName(funcName: CharSequence) {
        mathElementTextView.text = funcName
    }

    @CallbackProp
    fun setListener(listener: OnClickListener?) {
        setOnClickListener(listener)
    }
}