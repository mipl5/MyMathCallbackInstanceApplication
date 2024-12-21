package michael.co.mymathcallbackinstanceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

import michael.co.model.Calculator;
import michael.co.model.ParametersStorage;

public class MainActivity extends AppCompatActivity {
    private MaterialTextView    tvNUM1;
    private MaterialTextView    tvOPERATOR;
    private MaterialTextView    tvNUM2;
    private MaterialTextView    tvRESULT;
    private MaterialButton      btnGetFirstNumber;
    private MaterialButton      btnGetOperation;
    private MaterialButton      btnGetSecondNumber;
    private MaterialButton      btnGetResult;
    private MaterialButton      btnCheck;
    private TextInputEditText   etMin;
    private TextInputEditText   etMax;

    private ActivityResultLauncher<Intent> getParametersLauncher;
    private ActivityResultLauncher<Intent> getResultLauncher;
    private ActivityResultLauncher<Intent> getCheckedResultLauncher;

    private ParametersStorage parametersStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();

        doLaunchers();
    }

    private void doLaunchers() {
        getParametersLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == RESULT_OK){
                            Intent data = o.getData();
                            assert data != null;
                            int typeData = data.getIntExtra("TypeData", -1);
                            if (typeData == 1){
                                int noNumber = data.getIntExtra("NoNumber", 1);
                                if (noNumber == 1){
                                    parametersStorage.setNumber1(data.getIntExtra("NUMBER", 0));
                                    tvNUM1.setText(String.valueOf(parametersStorage.getNumber1()));
                                }
                                else{ // noNumber == 2
                                    parametersStorage.setNumber2(data.getIntExtra("NUMBER", 0));
                                    tvNUM2.setText(String.valueOf(parametersStorage.getNumber2()));
                                }
                            }
                            else{ // typeData == 0
                                parametersStorage.setOperation(data.getCharExtra("OPERATION", '+'));
                                tvOPERATOR.setText(String.valueOf(parametersStorage.getOperation()));
                            }
                        }
                    }
                });
        getResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == RESULT_OK){
                            Intent data = o.getData();
                            assert data != null;
                            parametersStorage.setResult(data.getIntExtra("RESULT", 0));
                            tvRESULT.setText(String.valueOf(parametersStorage.getResult()));
                        }
                    }
                });
        getCheckedResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == RESULT_OK){
                            Toast.makeText(MainActivity.this, "RESULT IS OK", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "RESULT IS NOT OK", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void initializeViews() {
        initialization();

        initializeOnClickListeners();
    }

    private void initialization() {
        tvNUM1 = (MaterialTextView)findViewById(R.id.tvNUM1);
        tvOPERATOR = (MaterialTextView)findViewById(R.id.tvOPERATOR);
        tvNUM2 = (MaterialTextView)findViewById(R.id.tvNUM2);
        tvRESULT = (MaterialTextView)findViewById(R.id.tvRESULT);
        btnGetFirstNumber = (MaterialButton)findViewById(R.id.btnGetFirstNumber);
        btnGetOperation = (MaterialButton)findViewById(R.id.btnGetOperation);
        btnGetSecondNumber = (MaterialButton)findViewById(R.id.btnGetSecondNumber);
        btnGetResult = (MaterialButton)findViewById(R.id.btnGetResult);
        btnCheck = (MaterialButton)findViewById(R.id.btnCheck);
        etMin = (TextInputEditText)findViewById(R.id.etMin);
        etMax = (TextInputEditText)findViewById(R.id.etMax);

        initializeObjects();
    }

    private void initializeObjects() {
        parametersStorage = new ParametersStorage();
    }

    private void initializeOnClickListeners() {
        btnGetFirstNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Objects.requireNonNull(etMin.getText()).toString().isEmpty() &&
                        !Objects.requireNonNull(etMax.getText()).toString().isEmpty()){
                    Intent i = new Intent(MainActivity.this, GetOperatorsActivity.class);
                    i.putExtra("MIN", Integer.parseInt(etMin.getText().toString()));
                    i.putExtra("MAX", Integer.parseInt(etMax.getText().toString()));
                    i.putExtra("NUMBER_NO", 1);
                    i.putExtra("TypeInput", 1); // 1-number
                    getParametersLauncher.launch(i);
                    // clearing min max field
                    etMin.setText("");
                    etMax.setText("");
                    // disable / not visibility to make it more logical when getting operation
                    etMin.setVisibility(View.INVISIBLE);
                    etMax.setVisibility(View.INVISIBLE);
                }
                else{
                    Toast.makeText(MainActivity.this, "missing", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnGetSecondNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Objects.requireNonNull(etMin.getText()).toString().isEmpty() &&
                        !Objects.requireNonNull(etMax.getText()).toString().isEmpty()){
                    Intent i = new Intent(MainActivity.this, GetOperatorsActivity.class);
                    i.putExtra("MIN", Integer.parseInt(etMin.getText().toString()));
                    i.putExtra("MAX", Integer.parseInt(etMax.getText().toString()));
                    i.putExtra("NUMBER_NO", 2);
                    i.putExtra("TypeInput", 1); // 1-number
                    getParametersLauncher.launch(i);
                    // clearing min max field
                    etMin.setText("");
                    etMax.setText("");
                }
                else{
                    Toast.makeText(MainActivity.this, "missing", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnGetOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, GetOperatorsActivity.class);
                i.putExtra("TypeInput", 0); // 0-character(operation)
                getParametersLauncher.launch(i);
                // clearing min max field
                etMin.setText("");
                etMax.setText("");
                // enable / yes visibility to make it more logical when getting operation
                etMin.setVisibility(View.VISIBLE);
                etMax.setVisibility(View.VISIBLE);
            }
        });
        btnGetResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvNUM1.getText().toString().isEmpty() && !tvNUM2.getText().toString().isEmpty() &&
                !tvOPERATOR.getText().toString().isEmpty()){
                    Intent i = new Intent(MainActivity.this, GetResultActivity.class);
                    i.putExtra("NUM1", parametersStorage.getNumber1());
                    i.putExtra("NUM2", parametersStorage.getNumber2());
                    i.putExtra("OPERATION", parametersStorage.getOperation());
                    getResultLauncher.launch(i);
                }
                else{
                    Toast.makeText(MainActivity.this, "missing", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvRESULT.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "missing", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent(MainActivity.this, CheckResultActivity.class);
                    i.putExtra("NUM1", parametersStorage.getNumber1());
                    i.putExtra("NUM2", parametersStorage.getNumber2());
                    i.putExtra("OPERATION", parametersStorage.getOperation());
                    i.putExtra("RESULT", parametersStorage.getResult());
                    getCheckedResultLauncher.launch(i);
                }
            }
        });
    }
}