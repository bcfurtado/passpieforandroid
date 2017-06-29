package io.github.bcfurtado.passpieforandroid.database;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    public final static String APPLICATION_PREFERENCES = "APPLICATION_PREFERENCES";
    public final static String REPOSITORY_URI_PREF = "REPOSITORY_URI";
    public final static String PRIVATE_KEY_PREF = "PRIVATE_KEY_PREF";

    private Context context;

    public PreferenceManager(Context context) {
        this.context = context;
    }

    public String getRepositoryUri() {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(REPOSITORY_URI_PREF, "");
    }

    public void setRepositoryUri(String repositoryUri) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(REPOSITORY_URI_PREF, repositoryUri);
        editor.commit();
    }

    public boolean isRepositorySetup() {
        return getRepositoryUri() !=  "";
    }


    public String getPrivateKey() {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(PRIVATE_KEY_PREF, "");
    }

    public void setPrivateKey(String privateKey) {
        SharedPreferences preferences = context.getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PRIVATE_KEY_PREF, privateKey);
        editor.commit();
    }

    public boolean isPrivateKeySetup() {
        return getPrivateKey() != "";
    }
}
