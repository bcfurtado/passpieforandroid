package io.github.bcfurtado.passpieforandroid.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import io.github.bcfurtado.passpieforandroid.MainActivity;
import io.github.bcfurtado.passpieforandroid.R;
import io.github.bcfurtado.passpieforandroid.database.PreferenceManager;


public class ChooseRepositoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String LOGIN_KEY = "login_key";
    public static final String PASSWORD_KEY = "password_key";

    public ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_repository_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);

        String login = getIntent().getStringExtra(LOGIN_KEY);
        String password = getIntent().getStringExtra(PASSWORD_KEY);

        GetGitHubRepositoriesTask task = new GetGitHubRepositoriesTask(this, login, password, this);
        task.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView element = (TextView) view;

        String repositoryURI = "git@github.com:" + element.getText().toString();

        PreferenceManager preferenceManager = new PreferenceManager(this);
        preferenceManager.setRepositoryUri(repositoryURI);

        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

}
