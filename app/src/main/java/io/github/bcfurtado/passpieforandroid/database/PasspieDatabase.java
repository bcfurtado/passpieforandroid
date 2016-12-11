package io.github.bcfurtado.passpieforandroid.database;


import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PasspieDatabase {

    private static final String GIT_REPO_FOLDER = "gitrepo";

    private Context context;

    public PasspieDatabase(Context context) {
        this.context = context;
    }

    public List<Account> getAccounts() {
        File repositoryFolder = new File(getRepoURI());
        List<Account> accounts = new ArrayList<>();
        if (repositoryFolder.exists()) {
            for (File file: repositoryFolder.listFiles()) {
                if (isAccountFolder(file)) {

                    try {
                        List<Account> accountsFolder = getAccountsFromFolder(file);
                        accounts.addAll(accountsFolder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
        return accounts;
    }

    private List<Account> getAccountsFromFolder(File folder) throws IOException {
        List<Account> accounts = new ArrayList<>();
        for (File file : folder.listFiles()) {
            Account account = getAccount(file);
            account.setFullname(folder.getName());
            accounts.add(account);
            Log.d(PasspieDatabase.class.getSimpleName(), String.format("Account Fullname: %s", account.getFullname()));
        }
        return accounts;
    }

    private boolean isAccountFolder(File file) {
        return file.isDirectory() && !file.getName().equals(".git");
    }

    public Account getAccount(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Account account = mapper.readValue(file, Account.class);
        return account;
    }

    public List<String> getAccountsAsString() {
        List<String> strings = new ArrayList<>();
        for (Account account: getAccounts()) {
            strings.add(account.getFullname());
        }
        return strings;
    }

    private String getRepoURI() {
        return context.getFilesDir().getAbsolutePath() + File.separator + GIT_REPO_FOLDER;
    }


}
