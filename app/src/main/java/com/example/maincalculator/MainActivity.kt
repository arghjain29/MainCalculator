package com.example.maincalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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



        // Setting up button listeners
        button0.setOnClickListener { appendOnExpression("0", true) }
        button1.setOnClickListener { appendOnExpression("1", true) }
        button2.setOnClickListener { appendOnExpression("2", true) }
        button3.setOnClickListener { appendOnExpression("3", true) }
        button4.setOnClickListener { appendOnExpression("4", true) }
        button5.setOnClickListener { appendOnExpression("5", true) }
        button6.setOnClickListener { appendOnExpression("6", true) }
        button7.setOnClickListener { appendOnExpression("7", true) }
        button8.setOnClickListener { appendOnExpression("8", true) }
        button9.setOnClickListener { appendOnExpression("9", true) }
        buttonDot.setOnClickListener { appendOnExpression(".", true) }

        buttonAdd.setOnClickListener { appendOnExpression("+", false) }
        buttonSub.setOnClickListener { appendOnExpression("-", false) }
        buttonMul.setOnClickListener { appendOnExpression("*", false) }
        buttonDiv.setOnClickListener { appendOnExpression("/", false) }

        buttonResult.setOnClickListener {
            try {
                val expression = ExpressionBuilder(numView.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                if (result == longResult.toDouble()) {
                    resultView.text = longResult.toString()
                } else {
                    resultView.text = result.toString()
                }
            } catch (e: Exception) {
                resultView.text = "Error"
            }
        }

        buttonAC.setOnClickListener {
            numView.text = ""
            resultView.text = ""
        }

        buttonBack.setOnClickListener {
            val string = numView.text.toString()
            if (string.isNotEmpty()) {
                numView.text = string.substring(0, string.length - 1)
            }
            resultView.text = ""
        }
    }
}