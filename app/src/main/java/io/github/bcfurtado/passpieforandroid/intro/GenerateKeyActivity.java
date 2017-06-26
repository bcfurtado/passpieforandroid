package io.github.bcfurtado.passpieforandroid.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.github.bcfurtado.passpieforandroid.R;

public class GenerateKeyActivity extends AppCompatActivity {

    public Button generateSshKeysButton;
    public Button chooseRepository;
    public Button createRepository;
    private String loginGitHub;
    private String passwordGitHub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_key_activity);

        Intent intent = getIntent();

        loginGitHub = intent.getStringExtra(LoginActivity.LOGIN_KEY);
        passwordGitHub = intent.getStringExtra(LoginActivity.PASSWORD_KEY);

        generateSshKeysButton = (Button) findViewById(R.id.generate_ssh_keys_for_github);
        chooseRepository = (Button) findViewById(R.id.choose_repository);
        createRepository = (Button) findViewById(R.id.create_repository);

        generateSshKeysButton.setEnabled(true);
        chooseRepository.setEnabled(false);
        createRepository.setEnabled(false);

        generateSshKeysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddGithubSshKeyTask task = new AddGithubSshKeyTask(loginGitHub, passwordGitHub, GenerateKeyActivity.this);
                task.execute();
            }
        });

        chooseRepository.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(GenerateKeyActivity.this, ChooseRepositoryActivity.class);
                it.putExtra(ChooseRepositoryActivity.LOGIN_KEY, loginGitHub);
                it.putExtra(ChooseRepositoryActivity.PASSWORD_KEY, passwordGitHub);
                startActivity(it);
            }
        });

        createRepository.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GenerateKeyActivity.this, "Not implemented yet.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
