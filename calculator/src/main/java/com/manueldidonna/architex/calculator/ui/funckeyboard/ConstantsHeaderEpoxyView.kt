package com.manueldidonna.architex.calculator.ui.funckeyboard

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.TooltipCompat
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.manueldidonna.architex.calculator.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, fullSpan = true)
class ConstantsHeaderEpoxyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val deleteAllConstantsIcon: AppCompatImageView
    private val addConstantIcon: AppCompatImageView

    init {
        inflate(context, R.layout.epoxy_view_constants_header, this)
        deleteAllConstantsIcon = findViewById(R.id.delete_all_constants)
        addConstantIcon = findViewById(R.id.add_constant)
        TooltipCompat.setTooltipText(deleteAllConstantsIcon, context.getText(R.string.delete_all_constants_tooltip))
        TooltipCompat.setTooltipText(addConstantIcon, context.getText(R.string.create_constant_tooltip))
    }

    @CallbackProp
    fun addConstantListener(listener: OnClickListener?) {
        addConstantIcon.setOnClickListener(listener)
    }

    @CallbackProp
    fun removeAllConstantsListener(listener: OnClickListener?) {
        deleteAllConstantsIcon.setOnClickListener(listener)
    }
}