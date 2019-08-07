package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.format.MathFormat
import com.manueldidonna.architex.mathevaluator.utils.replaceLast
import com.manueldidonna.architex.mathevaluator.utils.safeLast
import java.util.*

typealias SanitizeExpressionCallback = (MathExpression, StringBuilder) -> Unit

class MathExpression(
    val mathFormat: MathFormat,
    val debug: Boolean = false,
    private val sanitizeExpressionCallback: SanitizeExpressionCallback = DEFAULT_SANITIZE_CALLBACK
) {

    private val elements = ArrayDeque<MathElement>()
    private val builder = StringBuilder()

    val config: MutableMap<String, Any> = mutableMapOf()

    inline fun <reified T> config(key: String): T {
        return config[key] as T
    }

    inline fun <reified T : Any> config(key: String, defaultValue: T): T {
        val value = config[key]
        return if (value == null) {
            config[key] = defaultValue
            defaultValue
        } else {
            value as T
        }
    }

    fun getElementsSize(): Int = elements.size

    fun getLastElement(): MathElement? = elements.safeLast()

    fun insertElement(element: MathElement) = elements.add(element)

    fun removeLastElement(): MathElement = elements.removeLast()

    fun replaceLastElement(element: MathElement) = elements.replaceLast(element)

    fun buildExpression(sanitizeToEval: Boolean): String {
        if (elements.size <= 0)
            return ""
        builder.clear()
        if (!sanitizeToEval) {
            elements.forEach { e ->
                builder.append(e.value)
            }
        } else {
            elements.forEach { e ->
                builder.append(e.sanitizedValue)
            }
            sanitizeExpressionCallback.invoke(this, builder)
        }
        return builder.toString()
    }


    fun deleteLastElement(): MathExpression = apply {
        if (elements.size > 0 && elements.last.delete()) {
            elements.removeLast().onDeleteCallback(this)
        }
    }

    /**
     * Reset MathExpression to the initialization state
     */
    fun clear(): MathExpression = apply {
        if (builder.isNotEmpty())
            builder.clear()
        elements.clear()
        config.clear()
    }

    companion object {
        @JvmStatic
        var DEFAULT_SANITIZE_CALLBACK: SanitizeExpressionCallback = { expr, builder ->
            if (expr.getLastElement() is Operator)
                builder.deleteCharAt(builder.lastIndex)
            for (i in 0 until expr.config(OPEN_BRACKETS_KEY, 0))
                builder.append(CLOSE_BRACKET_SYMBOL_CHAR)
        }
    }
}