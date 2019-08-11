package com.manueldidonna.architex.calculator.ui

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.manueldidonna.arch.view.*
import com.manueldidonna.architex.calculator.R
import com.manueldidonna.architexcalc.core.extensions.find

internal sealed class ClipboardButtonEvent {
    object PasteExpressionFromClip : ClipboardButtonEvent()
    object ShowExpressionFromClip : ClipboardButtonEvent()
}

internal class ClipboardButtonView(private val view: View) : ArxRenderView<Boolean>,
    ArxInteractiveView<ClipboardButtonEvent> by ArxSubjectView() {
    private val pasteButton = view.find<Button>(R.id.paste_from_clipboard_button) {
        transitionName = BUTTON_TRANSITION_NAME
        emitOnClick(this, ClipboardButtonEvent.PasteExpressionFromClip)
        emitOnLongClick(this, ClipboardButtonEvent.ShowExpressionFromClip)
    }

    override fun render(state: Boolean) {
        TransitionManager.beginDelayedTransition(view as ViewGroup, slidePasteButtonTransition)
        pasteButton.isVisible = state
    }

    companion object {
        private const val BUTTON_TRANSITION_NAME = "paste_from_clip_t"

        private val slidePasteButtonTransition = TransitionSet().apply {
            addTransition(Slide(Gravity.START).apply {
                addTarget(BUTTON_TRANSITION_NAME)
            })
            interpolator = LinearOutSlowInInterpolator()
            duration = 240L
        }
    }
}