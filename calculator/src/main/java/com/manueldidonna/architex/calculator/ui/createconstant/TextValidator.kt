package com.manueldidonna.architex.calculator.ui.createconstant

import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.CheckResult
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.util.concurrent.atomic.AtomicBoolean

private const val DEFAULT_DEBOUNCE = 100L

internal inline fun CoroutineScope.validateInputs(
    firstInputFlow: Flow<Boolean>,
    secondInputFlow: Flow<Boolean>,
    crossinline doOnValidation: (Boolean) -> Unit
) = launch {
    val isFirstValid = AtomicBoolean(false)
    val isSecondValid = AtomicBoolean(false)
    // TODO: investigate about coroutine cancellation
    val outerScope = this
    launch {
        try {
            firstInputFlow.collect { isValid ->
                isFirstValid.set(isValid)
                doOnValidation(isValid && isSecondValid.get())
            }
        } catch (e: CancellationException) {
            outerScope.cancel(e)
        }
    }
    secondInputFlow.collect { isValid ->
        isSecondValid.set(isValid)
        doOnValidation(isValid && isFirstValid.get())
    }
}

internal fun TextInputEditText.validateName(): Flow<Boolean> = observeTextChanges(this)
    .map { inputText ->
        if (inputText.isEmpty() || inputText.length > 8)
            return@map false
        // TODO: avoid name duplicates
        return@map !inputText.any { c -> !c.isLetter() && c != '_' }
    }.flowOn(Dispatchers.Default)

internal fun TextInputEditText.validateValue(): Flow<Boolean> = observeTextChanges(this)
    .map { inputText ->
        if (inputText.isEmpty())
            return@map false
        // TODO: support decimal number as constants
        return@map !inputText.any { c -> !c.isDigit() }
    }.flowOn(Dispatchers.Default)

private fun observeTextChanges(input: TextInputEditText): Flow<CharSequence> =
    channelFlow<CharSequence> {
        offer(input.text?.toString() ?: "")
        val watcher = textWatcher(this, ::offer)
        input.addTextChangedListener(watcher)
        awaitClose {
            input.removeTextChangedListener(watcher)
        }
    }.debounce(DEFAULT_DEBOUNCE)

@CheckResult
private fun textWatcher(scope: CoroutineScope, emitter: (CharSequence) -> Boolean) = object : TextWatcher {
    override fun afterTextChanged(s: Editable) = Unit

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (scope.isActive) emitter(s)
    }
}