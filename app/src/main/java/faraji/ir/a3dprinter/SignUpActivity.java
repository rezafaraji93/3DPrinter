package faraji.ir.a3dprinter;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    EditText firstName, lastName, email, password, confirmPassword;
    RadioButton printerOwner, customer;
    Button submit;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();

        printerOwner = findViewById(R.id.printerOwner);
        customer = findViewById(R.id.customer);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.emailAddress);

        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        confirmPassword.addTextChangedListener(this);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);



    }


    private void registerUser() {
        String signUpEmail = email.getText().toString().trim();
        String signUpPassword = password.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(signUpEmail, signUpPassword).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User registered Successfully", Toast.LENGTH_SHORT).show();
//                    Log.d("tag", "signup success");
                    Intent intent = new Intent(SignUpActivity.this , LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (checkDataEntered()){
                    registerUser();
                }
                break;
        }
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (TextUtils.isEmpty(email));
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return (TextUtils.isEmpty(str));
    }


    boolean checkDataEntered() {
        if (!printerOwner.isChecked() && !customer.isChecked()) {
            Toast.makeText(this, "Please select a User type", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (isEmpty(firstName)) {
            firstName.setError("Please enter First Name");
            return false;
        }
        if (isEmpty(lastName)) {
            lastName.setError("Please enter Last Name");
            return false;
        }
        if (isEmail(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return false;
        }
        if (!ValidEmail.isValidEmailAddress(email.getText().toString())) {
            email.setError("Please enter valid email address");
            return false;
        }
        return true;

    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (password.getText().toString().equals(confirmPassword.getText().toString())) {
            return;
        } else {
            confirmPassword.setError("Passwords do not match");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


}