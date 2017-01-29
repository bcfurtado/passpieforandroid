package io.github.bcfurtado.passpieforandroid.utils;


import android.content.Context;

import org.bouncycastle.openpgp.PGPException;
import org.c02e.jpgpj.Decryptor;
import org.c02e.jpgpj.Key;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class DecryptorHelper {

    public static final String PRIVATE_KEY_START_SECTION = "-----BEGIN PGP PRIVATE KEY BLOCK-----";

    public static String decryptPassword(Context context, String encryptedPassword, String passphrase) throws IOException, PGPException {
        Decryptor decryptor;

        File keyFile = getKeysFile(context);
        String keysAsString = FileUtils.readFile(keyFile);
        String privateKeyAsString = getPrivatekey(keysAsString);

        Key key = new Key(privateKeyAsString, passphrase);
        decryptor = new Decryptor(key);
        decryptor.setVerificationRequired(false);

        InputStream isEncryptedPassword = new ByteArrayInputStream(encryptedPassword.getBytes());
        ByteArrayOutputStream osDecryptedPassword = new ByteArrayOutputStream();
        decryptor.decrypt(isEncryptedPassword, osDecryptedPassword);
        return new String(osDecryptedPassword.toByteArray());

    }

    private static String getPrivatekey(String file) {
        int indexPrivateKeyBegins = file.indexOf(PRIVATE_KEY_START_SECTION);
        String privateKey = file.substring(indexPrivateKeyBegins);
        return privateKey;
    }

    private static File getKeysFile(Context context) {
        String keyFilename = ".keys";
        String gitRepoDirectory = String.format("%s/gitrepo", context.getFilesDir().getAbsolutePath());
        File privateKeyFile = FileUtils.findFile(new File(gitRepoDirectory), keyFilename);
        return privateKeyFile;
    }

}