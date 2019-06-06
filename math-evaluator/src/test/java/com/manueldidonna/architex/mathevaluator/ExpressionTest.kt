package com.manueldidonna.architex.mathevaluator

import io.kotlintest.Spec
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec
import io.mockk.spyk
import java.util.*

class ExpressionTest : FeatureSpec() {
    private lateinit var mathExpression: MathExpression
    private lateinit var mathFormat: MathFormat

    override fun beforeSpec(spec: Spec) {
        mathFormat = spyk(StandardMathFormat(Locale.ITALIAN))
        mathExpression = spyk(MathExpression(mathFormat, debug = true), recordPrivateCalls = true)
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        mathExpression.clear()
    }

    private fun MathExpression.scan(expr: String) = buildExpression(true) {
        addExpression(expr, overwriteCurrentExpression = true)
    }

    init {
        feature("Math Expressions") {
            scenario("scan -> 12.3,4.5,6 7/87*45 67+l o g 3sin4") {
                mathExpression.scan("12.3,4.5,6 7/87*45 67+l o g 3sin4") shouldBe "123,4567÷87×4.567+log(3sin(4))"
            }
            scenario("scan -> 3,,,5)(lo g4)") {
                mathExpression.scan("3,,,5)(lo g4)") shouldBe "3,5(log(4))"
            }
            scenario("scan -> +4*6") {
                mathExpression.scan("+4*6") shouldBe "4×6"
            }
            scenario("scan -> -/5") {
                mathExpression.scan("-/5") shouldBe "-5"
            }
            scenario("scan -> 3-/5") {
                mathExpression.scan("3-/5") shouldBe "3÷5"
            }
            scenario("scan -> 3+(log4)/(5+2)") {
                mathExpression.scan("3+(log4)/(5+2)") shouldBe "3+(log(4)÷(5+2))"
            }
            scenario("scan -> 3+(-/4*6))"){
                mathExpression.scan(" 3+(-/4*6))") shouldBe "3+(-4×6)"
            }
            scenario("scan -> 3+4,56E34,3"){
                mathExpression.scan("3+4,56E34,3") shouldBe "3+4${mathFormat.decimalSeparator}56E343"
            }
        }
    }
}