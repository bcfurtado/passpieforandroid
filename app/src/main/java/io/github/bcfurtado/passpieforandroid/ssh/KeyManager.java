package io.github.bcfurtado.passpieforandroid.ssh;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.github.bcfurtado.passpieforandroid.database.PreferenceManager;

public class KeyManager {

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

        PreferenceManager preferenceManager = new PreferenceManager(context);

        FileOutputStream outputStream = new FileOutputStream(privateKeyFile);
        outputStream.write(preferenceManager.getPrivateKey().getBytes());
        outputStream.close();

        return privateKeyFile;
    }

}
