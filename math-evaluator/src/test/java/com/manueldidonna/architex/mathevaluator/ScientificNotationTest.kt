package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.format.Configuration
import com.manueldidonna.architex.mathevaluator.format.MathFormat
import com.manueldidonna.architex.mathevaluator.format.StandardMathFormat
import com.manueldidonna.architex.mathevaluator.internal.MINUS_SYMBOL
import io.kotlintest.Spec
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec
import io.mockk.spyk
import java.util.*

class ScientificNotationTest : FeatureSpec() {
    private lateinit var mathExpression: MathExpression
    private lateinit var mathFormat: MathFormat

    override fun beforeSpec(spec: Spec) {
        val configuration = Configuration(maximumFractionDigits = 4)
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
        feature("Scientific Notation") {
            scenario("minus symbol after 'E'") {
                mathExpression
                    .scientificNumber("2345,85E-4")
                    .decimalSeparator()
                    .number("345")
                    .buildExpression(false) shouldBe "2.345,85E${MINUS_SYMBOL}4345"
            }
            scenario("can't combine two scientific number") {
                mathExpression
                    .scientificNumber("4E6")
                    .scientificNumber("3E5")
                    .buildExpression(false) shouldBe "4E6"
            }
            scenario("minus operator with scientific notation") {
                mathExpression
                    .number("345")
                    .scientificNotation()
                    .operator(MathOperator.MINUS)
                    .number("12345")
                    .buildExpression(false) shouldBe "345E${MINUS_SYMBOL}12345"
                mathExpression
                    .clear()
                    .number("345")
                    .scientificNotation()
                    .number("12345")
                    .operator(MathOperator.MINUS)
                    .number("12")
                    .buildExpression(false) shouldBe "345E12345${MINUS_SYMBOL}12"
            }
            scenario("delete elements from scientific number") {
                mathExpression
                    .number("45")
                    .scientificNotation()
                    .buildExpression(false) shouldBe "45E0"
                mathExpression.number("2").buildExpression(false) shouldBe "45E2"
                mathExpression.deleteLastElement().buildExpression(false) shouldBe "45E0"
                mathExpression.number("86").buildExpression(false) shouldBe "45E86"
            }
        }
    }
}