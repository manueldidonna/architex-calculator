package com.manueldidonna.arch.controller

import android.app.Activity
import androidx.annotation.StringRes
import com.bluelinelabs.conductor.Controller

fun Controller.requireActivity(): Activity = activity
    ?: throw IllegalStateException("Controller not attached to an activity")

fun Controller.requireParentController(): Controller = parentController
    ?: throw IllegalStateException("Parent Controller does not exist")

fun Controller.requireTargetController(): Controller = targetController
    ?: throw IllegalStateException("Target Controller does not exist")

inline fun <reified T> Controller.castTargetController(): T = requireTargetController() as? T
    ?: throw IllegalStateException("Target Controller must implements ${T::class.java.simpleName} ")

fun Controller.getString(@StringRes resId: Int): String = requireActivity().getString(resId)

fun <C : Controller> C.setTarget(controller: Controller): C = apply { targetController = controller }