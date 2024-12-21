package michael.co.mymathcallbackinstanceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

public class GetResultActivity extends AppCompatActivity {
    private MaterialTextView    tvGetResultEquationProject;
    private TextInputEditText   etYourAnswer;
    private MaterialButton btnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
    }

    private void initializeViews() {
        initialization();
        getExtra();
        initializeOnClickListeners();
    }

    private void initializeOnClickListeners() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.requireNonNull(etYourAnswer.getText()).toString().isEmpty()){
                    Toast.makeText(GetResultActivity.this, "missing result", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i = new Intent();
                    int result = Integer.parseInt(etYourAnswer.getText().toString());
                    i.putExtra("RESULT", result);
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });
    }

    private void getExtra() {
        Intent i = getIntent();
        String str = "";
        int number1 = i.getIntExtra("NUM1", 0);
        str += String.valueOf(number1) + " ";
        char operation = i.getCharExtra("OPERATION", '+');
        str += String.valueOf(operation) + " ";
        int number2 = i.getIntExtra("NUM2", 0);
        str += String.valueOf(number2) + " =";
        tvGetResultEquationProject.setText(str);
    }

    private void initialization() {
        tvGetResultEquationProject = (MaterialTextView)findViewById(R.id.tvGetResultEquationProject);
        etYourAnswer = (TextInputEditText)findViewById(R.id.etYourAnswer);
        btnReturn = (MaterialButton)findViewById(R.id.btnReturn);
    }
}