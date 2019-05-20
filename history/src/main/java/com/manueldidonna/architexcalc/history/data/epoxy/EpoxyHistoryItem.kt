package com.manueldidonna.architexcalc.history.data.epoxy

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.transition.ChangeBounds
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
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
        expressionView = findViewById<TextView>(R.id.expression).apply {
            transitionName = EXPRESSION_TRANSITION_NAME
        }
        resultVIew = findViewById<TextView>(R.id.result).apply {
            transitionName = RESULT_TRANSITION_NAME
        }
        checkBox = findViewById<AppCompatCheckBox>(R.id.selection_check_box).apply {
            transitionName = CHECK_TRANSITION_NAME
        }
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
        // animate checkbox visibility changes
        TransitionManager.beginDelayedTransition(this, selectionModeTransition)

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
                listener?.invoke(true)
                true
            }
        }
    }

    companion object {
        private const val EXPRESSION_TRANSITION_NAME = "expression_t"
        private const val RESULT_TRANSITION_NAME = "result_t"
        private const val CHECK_TRANSITION_NAME = "check_t"

        private val selectionModeTransition = TransitionSet().apply {
            addTransition(ChangeBounds().apply {
                addTarget(EXPRESSION_TRANSITION_NAME)
                addTarget(RESULT_TRANSITION_NAME)
            })
            addTransition(Slide(Gravity.START).apply {
                addTarget(CHECK_TRANSITION_NAME)
            })
            interpolator = LinearOutSlowInInterpolator()
            duration = 240L
        }
    }
}