package io.github.bcfurtado.passpieforandroid.intro;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


class GetGitHubRepositoriesTask extends AsyncTask {

    private ChooseRepositoryActivity chooseRepositoryActivity;
    private String loginGitHub;
    private String passwordGitHub;
    private ChooseRepositoryActivity activity;

    private Exception exception;
    private Map<String, GHRepository> repositories;

    public GetGitHubRepositoriesTask(ChooseRepositoryActivity chooseRepositoryActivity, String loginGithub, String passwordGithub, ChooseRepositoryActivity activity) {
        this.chooseRepositoryActivity = chooseRepositoryActivity;
        this.loginGitHub = loginGithub;
        this.passwordGitHub = passwordGithub;
        this.activity = activity;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            GitHub github = GitHub.connectUsingPassword(loginGitHub, passwordGitHub);
            repositories = github.getMyself().getRepositories();

        } catch (IOException e) {
            this.exception = e;
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

        List<String> repositoriesString = new ArrayList<>();
        for (GHRepository repo : repositories.values()) {
            repositoriesString.add(repo.getFullName());
        }
        Collections.sort(repositoriesString);

        if (exception == null) {
            ListAdapter listAdapter = new ArrayAdapter<String>(chooseRepositoryActivity,
                    android.R.layout.simple_list_item_1,
                    repositoriesString
            );
            activity.listView.setAdapter(listAdapter);

        } else {
            Toast.makeText(activity, "Something wrong happened.", Toast.LENGTH_SHORT).show();
        }
    }
}
