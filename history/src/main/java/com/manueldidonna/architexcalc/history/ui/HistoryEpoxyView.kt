package com.manueldidonna.architexcalc.history.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.transition.ChangeBounds
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.airbnb.epoxy.*
import com.manueldidonna.architexcalc.common.ui.extensions.setSelectableBackground
import com.manueldidonna.architexcalc.common.ui.widgets.HorizontalScrollTextView
import com.manueldidonna.architexcalc.history.R

@Suppress("MemberVisibilityCanBePrivate")
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class HistoryEpoxyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val expressionView: HorizontalScrollTextView
    private val resultView: HorizontalScrollTextView
    private val checkBox: AppCompatCheckBox

    init {
        inflate(context, R.layout.epoxy_view_history_item, this)
        setSelectableBackground()
        expressionView = findViewById<HorizontalScrollTextView>(R.id.expression).apply {
            transitionName = EXPRESSION_TRANSITION_NAME
            setOnTouchListener(FixedTouchListener(this, this@HistoryEpoxyView))
        }
        resultView = findViewById<HorizontalScrollTextView>(R.id.result).apply {
            transitionName = RESULT_TRANSITION_NAME
            setOnTouchListener(FixedTouchListener(this, this@HistoryEpoxyView))
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
        resultView.text = text
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
        TransitionManager.beginDelayedTransition(
            this,
            selectionModeTransition
        )

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

    @SuppressLint("ClickableViewAccessibility")
    private class FixedTouchListener(
        private val scrollView: HorizontalScrollTextView,
        private val parentView: View
    ) : OnTouchListener {
        private var pressedX = 0F
        private var pressedY = 0F
        private var stayedWithinClickDistance = true

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    pressedX = event.x
                    pressedY = event.y
                    stayedWithinClickDistance = true
                    parentView.onTouchEvent(event)
                }
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> parentView.onTouchEvent(event)
                MotionEvent.ACTION_MOVE -> if (scrollView.canScroll) {
                    if (isTouchWithinCustomDistanceInDp(event)) {
                        parentView.onTouchEvent(event)
                    } else {
                        val ev = MotionEvent.obtain(event)
                        ev.action = MotionEvent.ACTION_CANCEL
                        parentView.onTouchEvent(ev)
                        ev.recycle()
                    }
                } else parentView.onTouchEvent(event)
            }
            return false
        }

        private fun isTouchWithinCustomDistanceInDp(event: MotionEvent): Boolean {
            if (stayedWithinClickDistance && measureDistance(pressedX, pressedY, event.x, event.y) > 15) {
                stayedWithinClickDistance = false
            }
            return stayedWithinClickDistance
        }

        private fun measureDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
            val dx = x2 - x1
            val dy = y2 - y1
            val pixelDistance = Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
            return (pixelDistance / parentView.context.resources.displayMetrics.density)
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