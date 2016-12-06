package io.github.bcfurtado.passpieforandroid.ssh;

import android.content.Context;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.util.FS;

import java.io.File;
import java.io.IOException;

import io.github.bcfurtado.passpieforandroid.SyncAccountsTask;

public class PasspieSshSessionFactory {

    private KeyManager keyManager;

    public PasspieSshSessionFactory(Context context) {
        this.keyManager = new KeyManager(context);
    }

    public SshSessionFactory getSessionFactory() throws IOException {

        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host hc, Session session) {
                session.setConfig("StrictHostKeyChecking", "no");
            }


            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch defaultJSch = new JSch();
                try {
                    File privateKeyFile = keyManager.getPrivateKeyFile();
                    defaultJSch.addIdentity(privateKeyFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return defaultJSch;
            }
        };
        return sshSessionFactory;
    }
}