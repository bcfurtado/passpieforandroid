package io.github.bcfurtado.passpieforandroid.intro;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jcraft.jsch.JSchException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.github.bcfurtado.passpieforandroid.database.PreferenceManager;
import io.github.bcfurtado.passpieforandroid.utils.GenerateSshKeysUtil;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

class AddGithubSshKeyTask extends AsyncTask {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private String loginGitHub;
    private String passwordGitHub;
    private GenerateKeyActivity generateKeyActivity;

    private Response response;
    private Exception exception;
    private GenerateSshKeysUtil generateSshKeysUtil;

    public AddGithubSshKeyTask(String loginGitHub, String passwordGitHub, GenerateKeyActivity generateKeyActivity) {
        this.loginGitHub = loginGitHub;
        this.passwordGitHub = passwordGitHub;
        this.generateKeyActivity = generateKeyActivity;
        this.generateSshKeysUtil = new GenerateSshKeysUtil();
    }


    @Override
    protected Object doInBackground(Object[] params) {
        try {
            generateSshKeysUtil.generate();

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode requestBody = mapper.createObjectNode();
            requestBody.put("title", "android@passpie");
            requestBody.put("key", generateSshKeysUtil.getPublicKey() );

            RequestBody body = RequestBody.create(JSON, requestBody.toString());

            OkHttpClient client = new OkHttpClient.Builder()
                    .authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            String credentials = Credentials.basic(loginGitHub, passwordGitHub);
                            return response.request().newBuilder().header("Authorization", credentials).build();
                        }
                    })
                    .build();

            Request request = new Request.Builder()
                    .url("https://api.github.com/user/keys")
                    .post(body)
                    .build();

            response = client.newCall(request).execute();

        } catch (IOException | JSchException e) {
            this.exception = e;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

        if (exception == null && response.isSuccessful()) {
            generateKeyActivity.generateSshKeysButton.setEnabled(false);
            generateKeyActivity.chooseRepository.setEnabled(true);
            generateKeyActivity.createRepository.setEnabled(true);

            PreferenceManager preferenceManager = new PreferenceManager(generateKeyActivity);
            preferenceManager.setPrivateKey(generateSshKeysUtil.getPrivateKey());

            Toast.makeText(generateKeyActivity, "Keys generated and saved.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(generateKeyActivity, "Something wrong happened.", Toast.LENGTH_SHORT).show();
        }



    }
}
