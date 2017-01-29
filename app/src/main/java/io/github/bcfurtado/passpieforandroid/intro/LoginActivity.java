package io.github.bcfurtado.passpieforandroid.intro;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.github.bcfurtado.passpieforandroid.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView autoCompleteTextView;
    private EditText passwordEditText;
    private Button signInButton;
    private Button signInWithoutAccount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.login_input);
        passwordEditText = (EditText) findViewById(R.id.password_input);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        signInWithoutAccount = (Button) findViewById(R.id.sign_in_without_account);

        signInButton.setOnClickListener(this);
        signInWithoutAccount.setOnClickListener(new NotImplementedClickListener(this));
    }

    @Override
    public void onClick(View view) {
        String login = autoCompleteTextView.getText().toString();
        String password = passwordEditText.getText().toString();

        if (isValidLogin(login) && isValidPassword(password)) {
            Intent it = new Intent(this, ChooseRepository.class);
            startActivity(it);
        } else {
            Toast.makeText(this, "Inval }id login or password.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 7;
    }

    private boolean isValidLogin(String login) {
        return login.length() >= 1;
    }

}
