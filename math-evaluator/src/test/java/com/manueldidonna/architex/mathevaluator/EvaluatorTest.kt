package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.evaluator.Exp4JEvaluator
import com.manueldidonna.architex.mathevaluator.evaluator.MathEvaluator
import com.manueldidonna.architex.mathevaluator.evaluator.MathResult
import com.manueldidonna.architex.mathevaluator.format.FormatConfiguration
import com.manueldidonna.architex.mathevaluator.format.MathFormat
import com.manueldidonna.architex.mathevaluator.format.StandardMathFormat
import io.kotlintest.Spec
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec
import io.mockk.spyk
import java.util.*

class EvaluatorTest : FeatureSpec() {
    private lateinit var mathExpression: MathExpression
    private lateinit var mathFormat: MathFormat
    private lateinit var resultMathFormat: MathFormat
    private lateinit var mathEvaluator: MathEvaluator

    override fun beforeSpec(spec: Spec) {
        mathFormat = spyk(StandardMathFormat(Locale.ITALIAN))
        mathExpression = spyk(MathExpression(mathFormat, debug = true))
        mathEvaluator = spyk(Exp4JEvaluator(mathFormat))
        resultMathFormat = StandardMathFormat(
            Locale.ITALIAN,
            FormatConfiguration(
                maximumFractionDigits = 4,
                maximumIntegerDigits = 1,
                useScientificNotation = true
            )
        )
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        mathExpression.clear()
    }

    init {
        feature("Evaluator") {
            scenario("eval a scientific number") {
                val expr = mathExpression.buildExpression(true) {
                    number("3")
                    operator(MathOperator.PLUS)
                    number("345")
                    decimalSeparator()
                    number("2345")
                    scientificNotation()
                    number("3")
                }
                mathEvaluator.eval(expr) shouldBe MathResult.Success(345237.5)
            }
            scenario("format result to scientific notation") {
                val expr = mathExpression.buildExpression(true) {
                    number("123456484")
                    operator(MathOperator.MULTIPLY)
                    number("344")
                    decimalSeparator()
                    number("2345")
                    scientificNotation()
                    number("3")
                }
                val result = mathEvaluator.eval(expr)
                result::class.java shouldBe MathResult.Success::class.java
                resultMathFormat.format((result as MathResult.Success).value) shouldBe "4,2498E13"
            }
        }
    }
}