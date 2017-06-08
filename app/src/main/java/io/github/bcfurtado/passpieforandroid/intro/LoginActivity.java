package io.github.bcfurtado.passpieforandroid.intro;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.kohsuke.github.GitHub;

import java.io.IOException;

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
            validateCredentials(login, password);
        } else {
            Toast.makeText(this, "Invalid login or password.", Toast.LENGTH_LONG).show();
        }
    }

    private void validateCredentials(String login, String password) {
        ValidateCredentials va = new ValidateCredentials(this, login, password);
        va.execute();
    }

    public void goToGenerateKeyActivity() {
        Intent it = new Intent(this, GenerateKeyActivity.class);
        startActivity(it);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 7;
    }

    private boolean isValidLogin(String login) {
        return login.length() >= 1;
    }

    public class ValidateCredentials extends AsyncTask {

        private Context context;
        private final String login;
        private final String password;
        private Boolean isCredentialValid;

        private Exception exception;

        public ValidateCredentials(Context context, String login, String password) {
            this.context = context;
            this.login = login;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Object[] objects) {
            try {
                Log.d(ValidateCredentials.class.getSimpleName(), "Log in starting");
                GitHub github = GitHub.connectUsingPassword(login, password);
                isCredentialValid = github.isCredentialValid();
                Log.d(ValidateCredentials.class.getSimpleName(), "Log in: isCredentialValid: " + isCredentialValid);

            } catch (IOException e) {
                e.printStackTrace();
                this.exception = e;
            }
            Log.d(ValidateCredentials.class.getSimpleName(), "Log in finished.");
            return null;
        }

        @Override
        protected void onPostExecute(Object object) {
            if (this.exception == null && isCredentialValid) {
                Toast.makeText(context, "Valid credentials.", Toast.LENGTH_SHORT).show();
                goToGenerateKeyActivity();
            } else {
                Toast.makeText(context, "Invalid credentials.", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
