package faraji.ir.a3dprinter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;

    Button loginButton, signUp;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUp:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.loginButton:
                if (checkDataEntered()){
                    signIn();
                }
                else Toast.makeText(getApplicationContext() , "Please Enter Login Information" , Toast.LENGTH_SHORT).show();


        }


    }

    private void signIn() {
        String email = this.username.getText().toString();
        String password = this.password.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("SignIn", "Sign In Successful");
                    Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(LoginActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public boolean checkDataEntered(){
        String usernameCheck = username.getText().toString();
        String passwordCheck = password.getText().toString();
        if (usernameCheck.isEmpty()) {
            username.setError("Please enter username");
            return false;
        }
        else if (passwordCheck.isEmpty()){
            password.setError("Please Enter Password");
            return false;
        }
        return true;
    }

}