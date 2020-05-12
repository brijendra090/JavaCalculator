package com.crackcode.ashu.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView screen;
    private String display = "";
    private String currentOperator = "";
    private String results = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen=findViewById(R.id.textView);
        screen.setText(display);
    }

    private void updateScreen(){
        screen.setText(display);
    }

    public void onClickNumber(View v) {
        if (results != "") {
            clear();
            updateScreen();
        }
        Button b = (Button) v;
        display += b.getText();
        updateScreen();
    }

    private boolean isOperator(char op) {
        switch (op){
            case '+':
            case '-':
            case '*':
            case '%': return true;
            default: return false;
        }
    }

    public void onClickOperator(View v){
        if (display == "") return;
        Button b = (Button) v;

        if (results !=""){
            String _display = results;
            clear();
            display = _display;
        }

        if (currentOperator != ""){
            Log.d("Calc", ""+display.charAt(display.length()-1));
            if (isOperator(display.charAt(display.length()-1))){

                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            } else{
                getResult();
                display = results;
                results = "";
            }
            currentOperator = b.getText().toString();
        }

        display += b.getText();
        currentOperator = b.getText().toString();
        updateScreen();
    }

    private void clear(){
        display = "";
        currentOperator = "";
        results = "";
    }

    public void onClickClear(View v){
        clear();
        updateScreen();
    }

    private double operate(String a, String b, String op){
        switch (op){
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "*": return Double.valueOf(a) * Double.valueOf(b);
            case "%": try {
                return Double.valueOf(a) / Double.valueOf(b);
            } catch (Exception e){
                Log.d("Calc", e.getMessage());
            }
                default: return -1;
        }
    }

    private boolean getResult(){
        if (currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if (operation.length < 2) return false;
        results = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void onClickEqual(View v){
        if (display == "") return;
        if (!getResult()) return;
        screen.setText(display + "\n" + String.valueOf(results));
    }
}
