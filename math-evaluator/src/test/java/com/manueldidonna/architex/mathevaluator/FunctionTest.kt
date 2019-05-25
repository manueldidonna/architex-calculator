package com.manueldidonna.architex.mathevaluator

import io.kotlintest.*
import io.kotlintest.specs.FeatureSpec
import io.mockk.spyk
import io.mockk.verify
import java.util.*

class FunctionTest : FeatureSpec() {
    private lateinit var mathExpression: MathExpression
    private lateinit var mathFormat: MathFormat

    override fun beforeSpec(spec: Spec) {
        mathFormat = spyk(StandardMathFormat(Locale.ITALIAN))
        mathExpression = spyk(MathExpression(mathFormat, debug = true), recordPrivateCalls = true)
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        mathExpression.clear()
    }

    init {
        feature("Append Functions") {
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
                verify(exactly = 2) { mathExpression.deleteLastElement() }
                verify(exactly = 1) { mathExpression["deleteFunction"]() }
                verify(exactly = 0) { mathExpression["deleteNumber"]() }
            }
            scenario("function name must contains only letter") {
                shouldThrow<IllegalArgumentException> { mathExpression.appendFunction("log3") }
            }
        }
    }
}