package io.github.bcfurtado.passpieforandroid.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.KeyPair;

import java.io.ByteArrayOutputStream;

public class GenerateSshKeysUtil {

    private String publicKey;
    private String privateKey;

    public void generate() throws JSchException {
        JSch jSch = new JSch();
        KeyPair keyPair = KeyPair.genKeyPair(jSch, KeyPair.RSA);

        ByteArrayOutputStream outPublicKey = new ByteArrayOutputStream();
        keyPair.writePublicKey(outPublicKey, "PasspieForAndroid");
        publicKey = new String(outPublicKey.toByteArray());


        ByteArrayOutputStream outPrivateKey = new ByteArrayOutputStream();
        keyPair.writePrivateKey(outPrivateKey);
        privateKey = new String(outPrivateKey.toByteArray());

        keyPair.dispose();
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
