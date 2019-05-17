package com.manueldidonna.architexcalc.history.data.epoxy

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.*
import com.manueldidonna.architexcalc.core.extensions.setSelectableBackground
import com.manueldidonna.architexcalc.history.R

@Suppress("MemberVisibilityCanBePrivate")
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class EpoxyHistoryItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val expressionView: TextView
    private val resultVIew: TextView
    private val checkBox: AppCompatCheckBox

    init {
        inflate(context, R.layout.epoxy_view_history_item, this)
        setSelectableBackground()
        expressionView = findViewById(R.id.expression)
        resultVIew = findViewById(R.id.result)
        checkBox = findViewById(R.id.selection_check_box)
    }

    @TextProp
    fun setExpression(text: CharSequence) {
        expressionView.text = text
    }

    @TextProp
    fun setResult(text: CharSequence) {
        resultVIew.text = text
    }

    var showCheckBox: Boolean? = null
        @ModelProp set

    var isSelected: Boolean? = null
        @ModelProp set

    var listener: ((isSelected: Boolean) -> Unit)? = null
        @CallbackProp set

    @AfterPropsSet
    fun setupCheckBox() {
        // TODO: animate checkbox visibility
        checkBox.visibility = if (showCheckBox == true) View.VISIBLE else View.GONE
        if (showCheckBox == true) {
            checkBox.isChecked = isSelected ?: false
            checkBox.setOnCheckedChangeListener { _, isChecked -> listener?.invoke(isChecked) }
        }
        setOnClickListener {
            if (showCheckBox == true) {
                checkBox.toggle()
            }
        }
        setOnLongClickListener {
            return@setOnLongClickListener if (showCheckBox == true) false else {
                checkBox.isChecked
                listener?.invoke(true)
                true
            }
        }

    }
}