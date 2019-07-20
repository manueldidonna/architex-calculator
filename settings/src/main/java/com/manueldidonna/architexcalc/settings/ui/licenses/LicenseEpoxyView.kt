package com.manueldidonna.architexcalc.settings.ui.licenses

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.manueldidonna.architexcalc.core.extensions.setSelectableBackground
import com.manueldidonna.architexcalc.settings.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
internal class LicenseEpoxyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val nameTextView: AppCompatTextView
    private val typeTextView: AppCompatTextView

    init {
        inflate(context, R.layout.epoxy_view_license, this)
        setSelectableBackground()
        orientation = VERTICAL
        nameTextView = findViewById(R.id.license_name)
        typeTextView = findViewById(R.id.license_type)
    }

    @TextProp
    fun setName(text: CharSequence) {
        nameTextView.text = text
    }

    @TextProp
    fun setType(text: CharSequence) {
        typeTextView.text = text
    }

    @CallbackProp
    fun setListener(listener: OnClickListener?) {
        setOnClickListener(listener)
    }

}