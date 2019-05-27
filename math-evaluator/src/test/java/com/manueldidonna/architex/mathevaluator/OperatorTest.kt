package com.manueldidonna.architex.mathevaluator

import io.kotlintest.Spec
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec
import io.mockk.spyk
import java.util.*

class OperatorTest : FeatureSpec() {
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
        feature("Math Operators") {
            scenario("plus&minus") {
                mathExpression.buildExpression(false) {
                    appendNumber("1")
                    appendOperator(MathOperator.PLUS_MINUS)
                } shouldBe "1+"
            }
            scenario("plus&minus with an empty string") {
                mathExpression.buildExpression(false) {
                    appendOperator(MathOperator.PLUS_MINUS)
                } shouldBe "-"
            }
            scenario("plus&minus twice in a row") {
                mathExpression.buildExpression(false) {
                    appendNumber("1")
                    appendOperator(MathOperator.PLUS_MINUS)
                    appendOperator(MathOperator.PLUS_MINUS)
                } shouldBe "1-"
            }
            scenario("plus&minus twice in a row with an empty string") {
                mathExpression.buildExpression(false) {
                    appendOperator(MathOperator.PLUS_MINUS)
                    appendOperator(MathOperator.PLUS_MINUS)
                } shouldBe "-"
            }
            scenario("multiply/divide/plus with an empty string") {
                mathExpression.buildExpression(false) {
                    appendOperator(MathOperator.PLUS)
                } shouldBe ""
                mathExpression.buildExpression(false) {
                    appendOperator(MathOperator.DIVIDE)
                } shouldBe ""
                mathExpression.buildExpression(false) {
                    appendOperator(MathOperator.MULTIPLY)
                } shouldBe ""
            }
            scenario("replace last operator") {
                mathExpression.buildExpression(false) {
                    appendNumber("1")
                    appendOperator(MathOperator.MULTIPLY)
                } shouldBe "1$MULTIPLY_SYMBOL"
                mathExpression.buildExpression(false) {
                    appendOperator(MathOperator.DIVIDE)
                } shouldBe "1$DIVIDE_SYMBOL"
            }
            scenario("replace minus") {
                mathExpression.buildExpression(false) {
                    appendNumber("1")
                    appendOperator(MathOperator.MINUS)
                    appendOperator(MathOperator.DIVIDE)
                } shouldBe "1$DIVIDE_SYMBOL"
                mathExpression.buildExpression(false) {
                    clear()
                    appendOperator(MathOperator.MINUS)
                    appendOperator(MathOperator.DIVIDE)
                } shouldBe "-"
            }
            scenario("sanitize an expression removes last operator"){
                mathExpression.buildExpression(false){
                    appendNumber("3")
                    appendOperator(MathOperator.PLUS)
                    appendNumber("4")
                    appendOperator(MathOperator.PLUS)
                } shouldBe "3+4+"
                mathExpression.buildExpression(sanitize = true) shouldBe "3+4"
            }
            scenario("operators after an open bracket"){
                mathExpression.buildExpression(false){
                    appendNumber("3")
                    appendBracket(MathBracket.OPEN)
                    appendOperator(MathOperator.DIVIDE)
                    appendOperator(MathOperator.PLUS_MINUS)
                    appendOperator(MathOperator.MULTIPLY)
                    appendNumber("5")
                } shouldBe "3${OPEN_BRACKET_SYMBOL}${MINUS_SYMBOL}5"
            }
        }
    }
}