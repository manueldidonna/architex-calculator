package com.manueldidonna.architex.mathevaluator

import io.kotlintest.*
import io.kotlintest.specs.FeatureSpec
import io.mockk.spyk
import java.util.*

class FunctionTest : FeatureSpec() {
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
        feature("Math Functions") {
            scenario("brackets after function name") {
                mathExpression.buildExpression(true) {
                    appendFunction("log")
                    appendNumber("3")
                } shouldBe "log(3)"
                mathExpression.buildExpression(true) {
                    addExpression("log3", overwrite = true)
                } shouldBe "log(3)"
            }
            scenario("delete a function") {
                mathExpression.buildExpression(true) {
                    appendFunction("log")
                    appendNumber("3")
                    deleteLastElement()
                    deleteLastElement()
                } shouldBe ""
            }
            scenario("function name must contains only letter") {
                shouldThrow<IllegalArgumentException> { mathExpression.appendFunction("log3") }
            }
        }
    }
}