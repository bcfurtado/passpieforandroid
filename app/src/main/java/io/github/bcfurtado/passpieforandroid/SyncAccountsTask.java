package io.github.bcfurtado.passpieforandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
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
    private View view;
    private BaseAdapter baseAdapter;
    private Exception exception;


    public SyncAccountsTask(Context context, String repositoryUrl, View view, BaseAdapter baseAdapter) {
        this.context = context;
        this.passpieSshSessionFactory = new PasspieSshSessionFactory(context);
        this.repositoryUrl = repositoryUrl;
        this.view = view;
        this.baseAdapter = baseAdapter;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            Log.d(SyncAccountsTask.class.getSimpleName(), "Starting clone...");

            File gitDir = getRepositoryFolder();

            CloneCommand cloneCommand = getSyncRepositoryCommand(gitDir);
            cloneCommand.call();

        } catch (InvalidRemoteException e) {
            e.printStackTrace();
            this.exception = e;
        } catch (GitAPIException e) {
            e.printStackTrace();
            this.exception = e;
        } catch (IOException e) {
            e.printStackTrace();
            this.exception = e;
        }
        Log.d(SyncAccountsTask.class.getSimpleName(), "Clone Finished.");
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        if (this.exception == null) {
            Snackbar.make(view, "Sync completed.", Snackbar.LENGTH_LONG).show();
            this.baseAdapter.notifyDataSetChanged();
        } else {
            String message = String.format("%s: %s", "We got a error!", this.exception.getMessage());
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
        }

    }

    private CloneCommand getSyncRepositoryCommand(File gitDir) throws GitAPIException, IOException {
        final SshSessionFactory sshSessionFactory = passpieSshSessionFactory.getSessionFactory();

        TransportConfigCallback transportConfigCallback = new TransportConfigCallback() {
            @Override
            public void configure(Transport transport) {
                SshTransport sshTransport = (SshTransport)transport;
                sshTransport.setSshSessionFactory(sshSessionFactory);
            }
        };

        return Git.cloneRepository()
                .setURI(repositoryUrl)
                .setDirectory(gitDir)
                .setCloneAllBranches(false)
                .setCloneSubmodules(false)
                .setTransportConfigCallback(transportConfigCallback);
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
