package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Optional;

import static com.example.calculator.R.id.clear_text;
import static com.example.calculator.R.id.equally;
import static com.example.calculator.R.id.input_field;
import static com.example.calculator.R.id.minus;
import static com.example.calculator.R.id.number_0;
import static com.example.calculator.R.id.number_1;
import static com.example.calculator.R.id.number_2;
import static com.example.calculator.R.id.number_3;
import static com.example.calculator.R.id.number_4;
import static com.example.calculator.R.id.number_5;
import static com.example.calculator.R.id.number_6;
import static com.example.calculator.R.id.number_7;
import static com.example.calculator.R.id.number_8;
import static com.example.calculator.R.id.number_9;
import static com.example.calculator.R.id.plus;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "[LifeCycleActivity]";
    private final static double DEFAULT_RESULT = 0;
    private final static String DEFAULT_NUMBER_FORMAT = "%1$,.2f";

    private TextView inputField;
    private double calculationResult;
    private String currentOperation = "";
    private Optional<Double> bufferedNumber = Optional.empty();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.calculationResult = DEFAULT_RESULT;
        initView();
    }

    private void initView() {
        inputField = findViewById(input_field);
        initClickListener(plus);
        initClickListener(minus);
        initClickListener(clear_text);
        initClickListener(equally);
        initClickListener(number_0);
        initClickListener(number_1);
        initClickListener(number_2);
        initClickListener(number_3);
        initClickListener(number_4);
        initClickListener(number_5);
        initClickListener(number_6);
        initClickListener(number_7);
        initClickListener(number_8);
        initClickListener(number_9);
    }

    private double getInputValueAsDouble() {
        Log.i(TAG, String.format("Getting input value: %s", inputField.getText()));
        return Double.parseDouble(inputField.getText().toString());
    }

    private Optional<Double> getInputValueAsDoubleOpt() {
        Optional<Double> value = Optional.empty();
        try {
            value = Optional.of(getInputValueAsDouble());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return value;
    }

    private void updateCalculationResult() {
        if (!currentOperation.isEmpty() && bufferedNumber.isPresent()) {
            try {
                calculationResult = bufferedNumber
                        .map(num -> Operations.valueOf(currentOperation).calculate(getInputValueAsDouble(), num))
                        .orElseGet(() -> calculationResult);
                Log.i(TAG, "Calculation result is updated to " + calculationResult);
                currentOperation = "";
                bufferedNumber = Optional.empty();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            clearText();
        } else {
            calculationResult = getInputValueAsDoubleOpt().orElseGet(() -> calculationResult);
        }
    }

    private void enterValueToInputField(Button button) {
        Log.d(TAG, String.format(Locale.getDefault(), "Entering value from view: %s", button.getText()));
        inputField.append(button.getText());
    }

    private void showCalculatedResult() {
        Log.i(TAG, String.format(Locale.getDefault(), "Showing calculated result %f", calculationResult));
        clearText();
        inputField.setText(String.format(Locale.getDefault(), DEFAULT_NUMBER_FORMAT, calculationResult));
    }

    private void clearCalculationResult() {
        this.calculationResult = DEFAULT_RESULT;
    }

    private void clearText() {
        Log.i(TAG, "Clearing text...");
        inputField.setText("");
    }

    private void initClickListener(int id) {
        findViewById(id).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case plus:
                Log.i(TAG, "Entering +");
                currentOperation = Operations.PLUS.name();
                bufferedNumber = getInputValueAsDoubleOpt();
                clearText();
                updateCalculationResult();
                break;
            case minus:
                Log.i(TAG, "Entering -");
                currentOperation = Operations.MINUS.name();
                bufferedNumber = getInputValueAsDoubleOpt();
                clearText();
                updateCalculationResult();
                break;
            case clear_text:
                clearCalculationResult();
                clearText();
                break;
            case equally:
                bufferedNumber = getInputValueAsDoubleOpt();
                updateCalculationResult();
                showCalculatedResult();
                break;
            // todo: add next operations
            case number_0:
            case number_1:
            case number_2:
            case number_3:
            case number_4:
            case number_5:
            case number_6:
            case number_7:
            case number_8:
            case number_9:
                if (calculationResult == DEFAULT_RESULT) {
                    clearText();
                }
                enterValueToInputField((Button) v);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}