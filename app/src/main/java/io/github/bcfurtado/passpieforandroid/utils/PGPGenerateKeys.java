package io.github.bcfurtado.passpieforandroid.utils;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.PGPDigestCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPDigestCalculatorProviderBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyPair;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyEncryptorBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.*;
import java.util.Date;


public class PGPGenerateKeys {

    private static void exportKeyPair(
            OutputStream    secretOut,
            OutputStream    publicOut,
            KeyPair         pair,
            String          identity,
            char[]          passPhrase,
            boolean         armor)
            throws IOException, InvalidKeyException, NoSuchProviderException, SignatureException, PGPException
    {
        if (armor)
        {
            secretOut = new ArmoredOutputStream(secretOut);
        }

        PGPDigestCalculator sha1Calc = new JcaPGPDigestCalculatorProviderBuilder().build().get(HashAlgorithmTags.SHA1);
        PGPKeyPair          keyPair = new JcaPGPKeyPair(PGPPublicKey.RSA_GENERAL, pair, new Date());
        PGPSecretKey        secretKey = new PGPSecretKey(PGPSignature.DEFAULT_CERTIFICATION, keyPair, identity, sha1Calc, null, null, new JcaPGPContentSignerBuilder(keyPair.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA1), new JcePBESecretKeyEncryptorBuilder(PGPEncryptedData.CAST5, sha1Calc).setProvider("BC").build(passPhrase));

        secretKey.encode(secretOut);

        secretOut.close();

        if (armor)
        {
            publicOut = new ArmoredOutputStream(publicOut);
        }

        PGPPublicKey key = secretKey.getPublicKey();

        key.encode(publicOut);

        publicOut.close();
    }

    public static void main(String[] args) throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");

        kpg.initialize(2048);

        KeyPair kp = kpg.generateKeyPair();

        args = new String[]{"-a", "passpie@android", "Voxy"};

        if (args.length < 2)
        {
            System.out.println("RSAKeyPairGenerator [-a] identity passPhrase");
            System.exit(0);
        }

        if (args[0].equals("-a"))
        {
            if (args.length < 3)
            {
                System.out.println("RSAKeyPairGenerator [-a] identity passPhrase");
                System.exit(0);
            }

            FileOutputStream    out1 = new FileOutputStream("secret.asc");
            FileOutputStream    out2 = new FileOutputStream("pub.asc");

            exportKeyPair(out1, out2, kp, args[1], args[2].toCharArray(), true);
        }
        else
        {
            FileOutputStream    out1 = new FileOutputStream("secret.bpg");
            FileOutputStream    out2 = new FileOutputStream("pub.bpg");

            exportKeyPair(out1, out2, kp, args[0], args[1].toCharArray(), false);
        }
    }

}
