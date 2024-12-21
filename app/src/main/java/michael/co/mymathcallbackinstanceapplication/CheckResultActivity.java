package michael.co.mymathcallbackinstanceapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import michael.co.model.Calculator;
import michael.co.model.ParametersStorage;

public class CheckResultActivity extends AppCompatActivity {
    private MaterialTextView tvEquationProject;
    private MaterialTextView tvResult;
    private MaterialButton btnReturn;

    private Calculator calculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
    }

    private void initializeViews() {
        initialization();
        initializeObjects();
        extras();
        initializeResultReaction();
        initializeOnClickListeners();
    }

    @SuppressLint("SetTextI18n")
    private void initializeResultReaction() {
        calculator.calculate();
        if (calculator.getIsSameResult()){
            tvResult.setText("Right");
            tvResult.setTextColor(Color.GREEN);
        }
        else{
            tvResult.setText("Wrong");
            tvResult.setTextColor(Color.RED);
        }
    }

    private void initializeObjects() {
        calculator = new Calculator();
    }

    private void initializeOnClickListeners() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                if (tvResult.getText().toString().equals("Right")){
                    setResult(RESULT_OK, i);
                    finish();
                }
                else{
                    setResult(RESULT_CANCELED, i);
                    finish();
                }
            }
        });
    }

    private void extras() {
        Intent i = getIntent();
        String str = "";
        int number1 = i.getIntExtra("NUM1", 0);
        str += String.valueOf(number1) + " ";
        char operation = i.getCharExtra("OPERATION", '+');
        str += String.valueOf(operation) + " ";
        int number2 = i.getIntExtra("NUM2", 0);
        str += String.valueOf(number2) + " = ";
        int result = i.getIntExtra("RESULT", 0);
        str += String.valueOf(result);
        tvEquationProject.setText(str);
        ParametersStorage ps = new ParametersStorage();
        ps.setNumber1(number1);
        ps.setOperation(operation);
        ps.setNumber2(number2);
        ps.setResult(result);
        calculator.setParametersStorage(ps);
    }

    private void initialization() {
        tvEquationProject = (MaterialTextView)findViewById(R.id.tvEquationProject);
        tvResult = (MaterialTextView)findViewById(R.id.tvResult);
        btnReturn = (MaterialButton)findViewById(R.id.btnReturn);
    }
}