package com.manueldidonna.architex.mathevaluator

import io.kotlintest.*
import io.kotlintest.specs.FeatureSpec
import io.mockk.spyk
import java.util.*


class NumberTest : FeatureSpec() {
    private lateinit var mathExpression: MathExpression
    private lateinit var mathFormat: MathFormat

    override fun beforeSpec(spec: Spec) {
        val configuration = Configuration(maximumFractionDigits = 4)
        mathFormat = spyk(StandardMathFormat(Locale.ITALIAN, configuration))
        mathExpression = spyk(MathExpression(mathFormat, debug = true))
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        mathExpression.clear()
    }

    init {
        feature("Numbers") {
            scenario("7 digits") {
                mathExpression.buildExpression(false) {
                    appendNumber("3456797")
                } shouldBe mathFormat.format(3456797)
            }
            scenario("6 digits after a decimal separator") {
                mathExpression.buildExpression(false) {
                    appendNumber("7")
                    appendDecimalSeparator()
                    appendNumber("123456")
                } shouldBe "7${mathFormat.decimalSeparator}123456"
            }
            scenario("6 digits before & after a decimal separator") {
                mathExpression.buildExpression(false) {
                    appendNumber("123456")
                    appendDecimalSeparator()
                    appendNumber("123456")
                } shouldBe "${mathFormat.format(123456)}${mathFormat.decimalSeparator}123456"
            }
            scenario("delete digits") {
                mathExpression.buildExpression(false) {
                    appendNumber("1234567")
                    deleteLastElement()
                } shouldBe mathFormat.format(123456)
                mathExpression.buildExpression(false) {
                    for (i in 0 until 4)
                        deleteLastElement()
                } shouldBe "12"
            }
            scenario("delete an operator & append a number") {
                mathExpression.buildExpression(false) {
                    appendNumber("123")
                    appendOperator(MathOperator.PLUS)
                } shouldBe "123+"
                mathExpression.buildExpression(false) {
                    deleteLastElement()
                    appendNumber("4")
                } shouldBe mathFormat.format(1234)
            }
            scenario("append letters with appendNumber()") {
                shouldThrow<IllegalArgumentException> { mathExpression.appendNumber("3E") }
            }
            scenario("multiple zeros") {
                mathExpression.buildExpression(true) {
                    appendNumber("0")
                    appendNumber("0")
                    appendNumber("0")
                    appendNumber("0")
                    appendNumber("7")
                } shouldBe "7"

                mathExpression.buildExpression(true) {
                    deleteLastElement()
                } shouldBe ""
            }
            scenario("number with only decimals") {
                mathExpression.buildExpression(true) {
                    appendDecimalSeparator()
                    appendNumber("345")
                } shouldBe "${mathFormat.decimalSeparator}345"
            }
        }
    }
}



