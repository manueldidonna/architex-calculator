package com.manueldidonna.architex.mathevaluator

import com.manueldidonna.architex.mathevaluator.evaluator.Exp4JEvaluator
import com.manueldidonna.architex.mathevaluator.evaluator.MathEvaluator
import com.manueldidonna.architex.mathevaluator.evaluator.MathResult
import com.manueldidonna.architex.mathevaluator.format.MathFormat
import com.manueldidonna.architex.mathevaluator.format.StandardMathFormat
import io.kotlintest.Spec
import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec
import io.mockk.spyk
import java.util.*

class ConstantTest : FeatureSpec() {
    private lateinit var mathEvaluator: MathEvaluator
    private lateinit var mathExpression: MathExpression
    private lateinit var mathFormat: MathFormat

    override fun beforeSpec(spec: Spec) {
        mathFormat = spyk(StandardMathFormat(Locale.ITALIAN))
        mathExpression = spyk(MathExpression(mathFormat, debug = true))
        mathEvaluator = spyk(Exp4JEvaluator(mathFormat))
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        mathExpression.clear()
    }

    init {
        feature("Math Constants") {
            scenario("pi constant") {
                mathExpression.buildExpression(false) {
                    constant(Exp4JEvaluator.PI_CONST)
                } shouldBe Exp4JEvaluator.PI_CONST.value
                mathExpression.eval(mathEvaluator) shouldBe MathResult.Success(Math.PI)
            }
            scenario("euler constant") {
                mathExpression.buildExpression(false) {
                    constant(Exp4JEvaluator.EULER_CONST)
                } shouldBe Exp4JEvaluator.EULER_CONST.value
                mathExpression.eval(mathEvaluator) shouldBe MathResult.Success(Math.E)
            }
            scenario("custom constant") {
                val customValue = "${Exp4JEvaluator.LN_FUNC.sanitizedValue}35$CLOSE_BRACKET_SYMBOL"
                val customConst = MathConstant("test", customValue)
                mathExpression.buildExpression(false){
                    constant(customConst)
                } shouldBe "test"
                mathExpression.eval(mathEvaluator) shouldBe mathEvaluator.eval(customValue)
            }
        }
    }
}