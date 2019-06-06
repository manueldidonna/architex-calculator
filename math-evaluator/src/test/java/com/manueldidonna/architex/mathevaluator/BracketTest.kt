package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.internal.CLOSE_BRACKET_SYMBOL
import com.manueldidonna.architex.mathevaluator.internal.OPEN_BRACKET_SYMBOL
import io.kotlintest.Spec
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec
import io.mockk.spyk
import java.util.*

class BracketTest : FeatureSpec() {
    private lateinit var mathExpression: MathExpression
    private lateinit var mathFormat: MathFormat

    override fun beforeSpec(spec: Spec) {
        mathFormat = spyk(StandardMathFormat(Locale.ITALIAN))
        mathExpression = spyk(MathExpression(mathFormat, debug = true))
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        mathExpression.clear()
    }

    init {
        feature("Math Brackets") {
            scenario("close brackets") {
                mathExpression.buildExpression(false) {
                    appendBracket(MathBracket.OPEN)
                    appendNumber("345")
                } shouldBe "${OPEN_BRACKET_SYMBOL}345"
                mathExpression.buildExpression(true) shouldBe "${OPEN_BRACKET_SYMBOL}345$CLOSE_BRACKET_SYMBOL"
            }
            scenario("close a bracket without opening it") {
                mathExpression.buildExpression(false) {
                    appendNumber("345")
                    appendBracket(MathBracket.CLOSE)
                    appendNumber("6")
                } shouldBe "3${mathFormat.groupingSeparator}456"
            }
        }
    }
}