package com.manueldidonna.architexcalc.history

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.manueldidonna.architexcalc.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class EpoxyHistoryItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val expressionView: TextView
    private val resultVIew: TextView

    init {
        inflate(context, R.layout.epoxy_view_history_item, this)
        orientation = LinearLayout.VERTICAL
        expressionView = findViewById(R.id.expression)
        resultVIew = findViewById(R.id.result)
    }

    @TextProp
    fun setExpression(text: CharSequence) {
        expressionView.text = text
    }

    @TextProp
    fun setResult(text: CharSequence) {
        resultVIew.text = text
    }

}