package com.manueldidonna.architexcalc.common.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.HorizontalScrollView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import androidx.core.widget.TextViewCompat
import com.manueldidonna.architexcalc.common.ui.R
import com.manueldidonna.architexcalc.core.extensions.doWithStyledAttributes
import com.manueldidonna.architexcalc.common.ui.extensions.layoutParams

class HorizontalScrollTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr) {

    @PublishedApi
    internal val textView: AppCompatTextView

    val canScroll: Boolean
        get() = canScrollHorizontally(-1) || canScrollHorizontally(1)

    init {
        inflate(context, R.layout.layout_horizontal_scroll_text_view, this)
        overScrollMode = OVER_SCROLL_NEVER
        isHorizontalScrollBarEnabled = false
        textView = findViewById(R.id.scrollable_text_view)
        context.doWithStyledAttributes(attrs, R.styleable.HorizontalScrollTextView) {
            TextViewCompat.setTextAppearance(
                textView,
                getResourceIdOrThrow(R.styleable.HorizontalScrollTextView_textAppearance)
            )
            val textSize = getDimension(R.styleable.HorizontalScrollTextView_textSize, 0f)
            if (textSize != 0f)
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
            textView.setTextColor(getColorOrThrow(R.styleable.HorizontalScrollTextView_textColor))
            textView.text = getString(R.styleable.HorizontalScrollTextView_text) ?: ""
            textGravity = getInt(R.styleable.HorizontalScrollTextView_textGravity, 0)
        }
    }

    inline fun onChildClickListener(crossinline block: (AppCompatTextView) -> Unit) {
        textView.apply {
            isFocusable = true
            isClickable = true
            setOnClickListener { block(this) }
        }
    }

    inline fun onChildLongClickListener(crossinline block: (AppCompatTextView) -> Boolean) {
        textView.apply {
            isFocusable = true
            isClickable = true
            setOnLongClickListener { block(this) }
        }
    }

    fun updateText(text: CharSequence) {
        textView.text = text
        doOnLayout {
            scrollTo(textView.width, 0)
        }
    }

    var text: CharSequence
        get() = textView.text
        set(value) {
            textView.text = value
        }

    var textGravity: Int
        get() = if (textView.layoutParams<LayoutParams>().gravity == Gravity.START) TEXT_GRAVITY_START else TEXT_GRAVITY_END
        set(value) = textView.updateLayoutParams<LayoutParams> {
            gravity = if (value == TEXT_GRAVITY_START) Gravity.START else Gravity.END
        }

    companion object {
        const val TEXT_GRAVITY_START = 0
        const val TEXT_GRAVITY_END = 1
    }
}