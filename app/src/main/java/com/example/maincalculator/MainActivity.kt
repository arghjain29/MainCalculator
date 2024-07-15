package com.example.maincalculator

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var numView: TextView
    private lateinit var resultView: TextView

    private fun appendOnExpression(string: String, canClear: Boolean) {
        if (resultView.text.isNotEmpty()) {
            numView.text = ""
        }

        if (canClear) {
            resultView.text = ""
            numView.append(string)
        } else {
            numView.append(resultView.text)
            numView.append(string)
            resultView.text = ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val clickAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.button_click)

        numView = findViewById(R.id.numView)
        resultView = findViewById(R.id.resultView)

        // Number buttons
        val button0: Button = findViewById(R.id.button0)
        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val buttonDot: Button = findViewById(R.id.buttonDot)

        // Operator buttons
        val buttonAdd: Button = findViewById(R.id.buttonAdd)
        val buttonSub: Button = findViewById(R.id.buttonSub)
        val buttonMul: Button = findViewById(R.id.buttonMul)
        val buttonDiv: Button = findViewById(R.id.buttonDiv)
        val buttonAC: Button = findViewById(R.id.buttonAC)
        val buttonBack: Button = findViewById(R.id.buttonBack)
        val buttonResult: Button = findViewById(R.id.buttonResult)



        //Setting up button listeners
        button0.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(button0); appendOnExpression("0", true) }
        button1.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(button1); appendOnExpression("1", true) }
        button2.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(button2); appendOnExpression("2", true) }
        button3.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(button3); appendOnExpression("3", true) }
        button4.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(button4); appendOnExpression("4", true) }
        button5.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(button5); appendOnExpression("5", true) }
        button6.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(button6); appendOnExpression("6", true) }
        button7.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(button7); appendOnExpression("7", true) }
        button8.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(button8); appendOnExpression("8", true) }
        button9.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(button9); appendOnExpression("9", true) }
        buttonDot.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(buttonDot); appendOnExpression(".", true) }

        buttonAdd.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(buttonAdd); appendOnExpression("+", false) }
        buttonSub.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(buttonSub); appendOnExpression("-", false) }
        buttonMul.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(buttonMul); appendOnExpression("*", false) }
        buttonDiv.setOnClickListener {it.startAnimation(clickAnimation); animateButtonColor(buttonDiv); appendOnExpression("/", false) }



        buttonResult.setOnClickListener {
            try {

                val expression = ExpressionBuilder(numView.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble()) {
                    it.startAnimation(clickAnimation);
                    animateButtonColorEqual(buttonResult);
                    resultView.text = longResult.toString()
                } else {
                    it.startAnimation(clickAnimation);
                    animateButtonColorEqual(buttonResult);
                    resultView.text = result.toString()
                }
            } catch (e: Exception) {
                resultView.text = "Error"
            }
        }

        buttonAC.setOnClickListener {
            it.startAnimation(clickAnimation);
            animateButtonColor(buttonAC);
            numView.text = ""
            resultView.text = ""
        }

        buttonBack.setOnClickListener {
            it.startAnimation(clickAnimation);
            animateButtonColor(buttonBack);
            val string = numView.text.toString()
            if (string.isNotEmpty()) {
                numView.text = string.substring(0, string.length - 1)
            }
            resultView.text = ""
        }


    }

    private fun animateButtonColor(button: Button) {
        val colorFrom = ContextCompat.getColor(this, R.color.original_button_color)
        val colorTo = ContextCompat.getColor(this, R.color.lighter_button_color)
        val background = button.background as GradientDrawable

            val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo, colorFrom).apply {
                duration = 600 // Total duration of the color animation
                interpolator = AccelerateDecelerateInterpolator() // Smooth fade in and out
                addUpdateListener { animator ->
                    background.setColor(animator.animatedValue as Int)
                }
            }
        colorAnimation.start()
    }

    private fun animateButtonColorEqual(button: Button) {
        val colorFrom = ContextCompat.getColor(this, R.color.lighter_button_color)
        val colorTo = ContextCompat.getColor(this, R.color.original_button_color)
        val background = button.background as GradientDrawable

        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo, colorFrom).apply {
            duration = 600 // Total duration of the color animation
            interpolator = AccelerateDecelerateInterpolator() // Smooth fade in and out
            addUpdateListener { animator ->
                background.setColor(animator.animatedValue as Int)
            }
        }
        colorAnimation.start()
    }

}
