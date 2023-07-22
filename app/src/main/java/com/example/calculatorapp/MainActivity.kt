package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculatorapp.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastnumeric  = false
    var stateError = false
    var lastDot = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun OnClearClick(view: View) {
        binding.dataTv.text = ""
        lastnumeric = false
    }

    fun OnbackClick(view: View) {
        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)
        try {
            val lastchar = binding.dataTv.text.toString().last()
            if (lastchar.isDigit()){
                onEqual()
            }
        }
        catch (e:Exception){
            binding.resultTv.text = ""
            binding.resultTv.visibility = View.GONE
            Log.e("last char error",e.toString())
        }
    }

    fun OnOpeartorClick(view: View) {
        if (!stateError && lastnumeric){
            binding.dataTv.append((view as Button).text)
            lastnumeric = false
            stateError = false
            onEqual()
        }
    }

    fun OnDigClick(view: View) {
        if(stateError){
            binding.dataTv.text = (view as Button).text
            stateError = false
        }
        else{
            binding.dataTv.append((view as Button).text)

        }
        lastnumeric = true
        onEqual()
    }

    fun OnAllClearClick(view: View) {
        binding.dataTv.text = ""
        stateError = false
        lastnumeric = false
        lastDot = false
        binding.resultTv.visibility = View.GONE

    }

    fun OnEqualClick(view: View) {
        onEqual()
        binding.dataTv.text = binding.resultTv.text.toString().drop(1)
        binding.resultTv.text = "0"
    }


    fun onEqual(){

        if (lastnumeric && !stateError){
            val txt = binding.dataTv.text.toString()
            expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()
                binding.resultTv.visibility = View.VISIBLE
                binding.resultTv.text = "= " + result.toString()
            }
            catch (ex:ArithmeticException){
                Log.e("evaluate error",ex.toString())
                binding.resultTv.text = "Error"
                stateError = true
                lastnumeric = false

            }
        }
    }
}