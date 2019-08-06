package com.manueldidonna.architex.mathevaluator

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
                    bracket(MathBracket.OPEN)
                    number("345")
                } shouldBe "${OPEN_BRACKET_SYMBOL_CHAR}345"
                mathExpression.buildExpression(true) shouldBe "${OPEN_BRACKET_SYMBOL_CHAR}345$CLOSE_BRACKET_SYMBOL_CHAR"
            }
            scenario("close a bracket without opening it") {
                mathExpression.buildExpression(false) {
                    number("345")
                    bracket(MathBracket.CLOSE)
                    number("6")
                } shouldBe "3${mathFormat.groupingSeparator}456"
            }
        }
    }
}