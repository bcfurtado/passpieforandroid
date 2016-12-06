package io.github.bcfurtado.passpieforandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;

import java.io.File;
import java.io.IOException;

import io.github.bcfurtado.passpieforandroid.ssh.PasspieSshSessionFactory;
import io.github.bcfurtado.passpieforandroid.utils.FileUtils;

public class SyncAccountsTask extends AsyncTask {

    private static final String GIT_REPO_FOLDER = "gitrepo";

    private Context context;
    private PasspieSshSessionFactory passpieSshSessionFactory;
    private String repositoryUrl;

    public SyncAccountsTask(Context context, String repositoryUrl) {
        this.context = context;
        this.passpieSshSessionFactory = new PasspieSshSessionFactory(context);
        this.repositoryUrl = repositoryUrl;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            File gitDir = getRepositoryFolder();

            syncRepository(gitDir);

        } catch (GitAPIException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void syncRepository(File gitDir) throws GitAPIException, IOException {
        final SshSessionFactory sshSessionFactory = passpieSshSessionFactory.getSessionFactory();

        TransportConfigCallback transportConfigCallback = new TransportConfigCallback() {
            @Override
            public void configure(Transport transport) {
                SshTransport sshTransport = (SshTransport)transport;
                sshTransport.setSshSessionFactory(sshSessionFactory);
            }
        };
        Log.d(SyncAccountsTask.class.getSimpleName(), "Starting clone...");


        Git git = Git.cloneRepository()
                .setURI(repositoryUrl)
                .setDirectory(gitDir)
                .setCloneAllBranches(false)
                .setCloneSubmodules(false)
                .setTransportConfigCallback(transportConfigCallback)
                .call();

        Log.d(SyncAccountsTask.class.getSimpleName(), "Clone Finished.");
    }

    private File getRepositoryFolder() {
        String dirPath = getRepoURI();
        File gitDir = new File(dirPath);

        if (gitDir.exists()) {
            FileUtils.deleteDir(gitDir);
        }
        gitDir.mkdirs();
        return gitDir;
    }


    private String getRepoURI() {
        return context.getFilesDir().getAbsolutePath() + File.separator + GIT_REPO_FOLDER;
    }

}
