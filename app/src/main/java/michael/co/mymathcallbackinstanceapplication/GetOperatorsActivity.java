package michael.co.mymathcallbackinstanceapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import michael.co.model.RandomizedGenerator;

public class GetOperatorsActivity extends AppCompatActivity {
    private MaterialTextView tvTitleParameter;
    private MaterialTextView tvProject;
    private MaterialButton btnGenerate;
    private MaterialButton btnReturn;

    private static RandomizedGenerator randomizedGenerator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_operators);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
    }

    private void initializeViews() {
        tvTitleParameter = (MaterialTextView)findViewById(R.id.tvTitleParameter);
        tvProject = (MaterialTextView)findViewById(R.id.tvProject);
        btnGenerate = (MaterialButton)findViewById(R.id.btnGenerate);
        btnReturn = (MaterialButton)findViewById(R.id.btnReturn);
        initializeObjects();
        getExtra();
        initializeOnClickListeners();
    }

    @SuppressLint("SetTextI18n")
    private void getExtra() {
        Intent i = getIntent();
        int typeInput = i.getIntExtra("TypeInput", 1);
        if (typeInput == 1){ // numbers - NUM1 & NUM2
            int numberNo = i.getIntExtra("NUMBER_NO", 1);
            if (numberNo == 1){ // first number
                tvTitleParameter.setText("NUM1");
            }
            else{ // numberNo - 2
                // second number
                tvTitleParameter.setText("NUM2");
            }
            randomizedGenerator.setMin(i.getIntExtra("MIN", 0));
            randomizedGenerator.setMax(i.getIntExtra("MAX", 0));
        }
        else{//typeInput == 0 - OPERATOR
            tvTitleParameter.setText("OPERATION");
        }
    }

    private void initializeOnClickListeners() {
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvTitleParameter.getText().toString().equals("NUM1") ||
                        tvTitleParameter.getText().toString().equals("NUM2")){
                    randomizedGenerator.generateNumber();
                    tvProject.setText(String.valueOf(randomizedGenerator.getNumber()));
                }
                else{ // operator
                    randomizedGenerator.generateOperation();
                    tvProject.setText(String.valueOf(randomizedGenerator.charGetOperation()));
                }
            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                if (tvTitleParameter.getText().toString().equals("NUM1") ||
                        tvTitleParameter.getText().toString().equals("NUM2")){
                    i.putExtra("NUMBER", randomizedGenerator.getNumber());
                    if (tvTitleParameter.getText().toString().equals("NUM1"))
                        i.putExtra("NoNumber", 1);
                    else
                        i.putExtra("NoNumber", 2);
                    i.putExtra("TypeData", 1);
                }
                else{ // operator
                    i.putExtra("OPERATION", randomizedGenerator.charGetOperation());
                    i.putExtra("TypeData", 0);
                }
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }

    private void initializeObjects() {
        randomizedGenerator = new RandomizedGenerator();
    }
}