package com.manueldidonna.architexcalc.core.ui

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.addListener
import com.bluelinelabs.conductor.changehandler.AnimatorChangeHandler
import com.manueldidonna.architexcalc.core.extensions.pxToDp
import timber.log.Timber

class HorizontalSlideChangeHandler(
    durationInMillis: Long = -1,
    removesFromViewOnPush: Boolean = false,
    private val fromViewTranslationPercentage: Int = 30
) : AnimatorChangeHandler(durationInMillis, removesFromViewOnPush) {

    override fun getAnimator(
        container: ViewGroup,
        from: View?, to: View?,
        isPush: Boolean, toAddedToContainer: Boolean
    ): Animator {
        require(from?.z ?: 0F == 0F && to?.z ?: 0F == 0F) { "From or To views have elevation" }
        val viewAnimators = mutableListOf<Animator>()

        if (from != null) {
            viewAnimators.add(
                ObjectAnimator.ofFloat(
                    from,
                    View.TRANSLATION_X,
                    translationValue(from, fromViewTranslationPercentage, !isPush)
                )
            )
        }

        if (to != null) {
            viewAnimators.add(
                ObjectAnimator.ofFloat(
                    to,
                    View.TRANSLATION_X,
                    translationValue(to, fromViewTranslationPercentage, isPush), 0f
                )
            )
        }

        (if (isPush) to else from)?.z = 8.pxToDp()

        val resetZ: (it: Animator) -> Unit = {
            from?.z = 0F
            to?.z = 0F
        }
        return AnimatorSet().apply {
            playTogether(viewAnimators)
            interpolator = AccelerateDecelerateInterpolator()
            addListener(
                onEnd = { resetZ(it); Timber.d("resetElevationOnEnd") },
                onCancel = { resetZ(it); Timber.d("resetElevationOnCancel") }
            )
        }
    }

    // TODO: better translation for aborted animation
    private fun translationValue(view: View, percentage: Int, returnWidth: Boolean): Float {
        return if (!returnWidth) -view.width.toFloat() / 100 * percentage else view.width.toFloat()
    }

    override fun resetFromView(from: View) {
        from.translationX = 0f
    }
}