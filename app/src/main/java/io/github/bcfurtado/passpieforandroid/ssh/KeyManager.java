package io.github.bcfurtado.passpieforandroid.ssh;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.github.bcfurtado.passpieforandroid.RepositoryConfigurationFragment;

public class KeyManager {

    private static final String PRIVATE_KEY_PREF = "PRIVATE_KEY_PREF";
    private Context context;

    public KeyManager(Context context) {
        this.context = context;
    }

    public File getPrivateKeyFile() throws IOException {
        String privateKeyFileName = "privateKey";
        File privateKeyFile = new File(context.getFilesDir(), privateKeyFileName);

        if (privateKeyFile.exists()) {
            privateKeyFile.delete();
        }
        privateKeyFile.createNewFile();

        FileOutputStream outputStream = new FileOutputStream(privateKeyFile);
        outputStream.write(getPrivateKey().getBytes());
        outputStream.close();

        return privateKeyFile;
    }

    private String getPrivateKey() {
        SharedPreferences preferences = context.getSharedPreferences(RepositoryConfigurationFragment.APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(PRIVATE_KEY_PREF, "");
    }
}
