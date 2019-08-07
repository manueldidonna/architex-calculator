package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.format.FormatConfiguration
import com.manueldidonna.architex.mathevaluator.format.MathFormat
import com.manueldidonna.architex.mathevaluator.format.StandardMathFormat
import io.kotlintest.*
import io.kotlintest.specs.FeatureSpec
import io.mockk.spyk
import java.util.*


class NumberTest : FeatureSpec() {
    private lateinit var mathExpression: MathExpression
    private lateinit var mathFormat: MathFormat

    override fun beforeSpec(spec: Spec) {
        val configuration = FormatConfiguration(maximumFractionDigits = 4)
        mathFormat = spyk(
            StandardMathFormat(
                Locale.ITALIAN,
                configuration
            )
        )
        mathExpression = spyk(MathExpression(mathFormat, debug = true))
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        mathExpression.clear()
    }

    init {
        feature("Numbers") {
            scenario("7 digits") {
                mathExpression.buildExpression(false) {
                    number("3456797")
                } shouldBe mathFormat.format(3456797)
            }
            scenario("6 digits after a decimal separator") {
                mathExpression.buildExpression(false) {
                    number("7")
                    decimalSeparator()
                    number("123456")
                } shouldBe "7${mathFormat.decimalSeparator}123456"
            }
            scenario("6 digits before & after a decimal separator") {
                mathExpression.buildExpression(false) {
                    number("123456")
                    decimalSeparator()
                    number("123456")
                } shouldBe "${mathFormat.format(123456)}${mathFormat.decimalSeparator}123456"
            }
            scenario("delete digits") {
                mathExpression.buildExpression(false) {
                    number("1234567")
                    deleteLastElement()
                } shouldBe mathFormat.format(123456)
                mathExpression.buildExpression(false) {
                    for (i in 0 until 4)
                        deleteLastElement()
                } shouldBe "12"
            }
            scenario("delete an operator & append a number") {
                mathExpression.buildExpression(false) {
                    number("123")
                    operator(MathOperator.PLUS)
                } shouldBe "123+"
                mathExpression.buildExpression(false) {
                    deleteLastElement()
                    number("4")
                } shouldBe mathFormat.format(1234)
            }
            scenario("append letters with number()") {
                shouldThrow<IllegalArgumentException> { mathExpression.number("3E") }
            }
            scenario("multiple zeros") {
                mathExpression.buildExpression(true) {
                    number("0")
                    number("0")
                    number("0")
                    number("0")
                    number("7")
                } shouldBe "7"

                mathExpression.buildExpression(true) {
                    deleteLastElement()
                } shouldBe ""
            }
            scenario("number with only decimals") {
                mathExpression.buildExpression(true) {
                    decimalSeparator()
                    number("345")
                } shouldBe "${mathFormat.decimalSeparator}345"
            }
            scenario("delete a decimal number"){
                mathExpression
                    .number(2)
                    .decimalSeparator()
                    .deleteLastElement()
                    .buildExpression(false) shouldBe "2"
                mathExpression
                    .number(3)
                    .decimalSeparator()
                    .number(45)
                    .deleteLastElement()
                    .buildExpression(false) shouldBe "23${mathExpression.mathFormat.decimalSeparator}4"
            }
        }
    }
}



