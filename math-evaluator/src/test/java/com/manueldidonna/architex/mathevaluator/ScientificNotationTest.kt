package com.manueldidonna.architex.mathevaluator

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
        mathFormat = spyk(StandardMathFormat(Locale.ITALIAN, configuration))
        mathExpression = spyk(MathExpression(mathFormat, debug = true))
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        mathExpression.clear()
    }

    init {
        feature("Scientific Notation") {
            scenario("minus symbol after 'E'") {
                mathExpression.buildExpression(false) {
                    appendScientificNumber("2345,85E-4")
                    appendDecimalSeparator()
                    appendNumber("345")
                } shouldBe "2.345,85E${MINUS_SYMBOL}4345"
            }
            scenario("can't combine two scientific number"){
                mathExpression.buildExpression(false) {
                    appendScientificNumber("4E6")
                    appendScientificNumber("3E5")
                } shouldBe "4E6"
            }
            scenario("minus operator with scientific notation"){
                mathExpression.buildExpression(false){
                    appendNumber("345")
                    appendScientificNotation()
                    appendOperator(MathOperator.MINUS)
                    appendNumber("12345")
                } shouldBe "345E${MINUS_SYMBOL}12345"
                mathExpression.clear()
                mathExpression.buildExpression(false){
                    appendNumber("345")
                    appendScientificNotation()
                    appendNumber("12345")
                    appendOperator(MathOperator.MINUS)
                    appendNumber("12")
                } shouldBe "345E12345${MINUS_SYMBOL}12"
            }
            scenario("delete elements from scientific number"){
                mathExpression.buildExpression(false){
                    appendNumber("45")
                    appendScientificNotation()
                } shouldBe "45E0"
                mathExpression.buildExpression(false){
                    appendNumber("2")
                } shouldBe "45E2"
                mathExpression.buildExpression(false){
                    deleteLastElement()
                } shouldBe "45E0"
                mathExpression.buildExpression(false){
                    appendNumber("86")
                } shouldBe "45E86"
            }
        }
    }
}