package io.github.bcfurtado.passpieforandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class RepositoryConfigurationFragment extends Fragment {

    public final static String APPLICATION_PREFERENCES = "APPLICATION_PREFERENCES";
    public final static String REPOSITORY_URI_PREF = "REPOSITORY_URI";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.repository_configuration_fragment, container, false);

        final EditText remoteRepository;
        remoteRepository = (EditText) rootView.findViewById(R.id.remoteDatabaseConfigurationEditText);

        remoteRepository.setText(getRepositoryUri());

        remoteRepository.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    setRepositoryUri(remoteRepository.getText().toString());
                }
            }
        });


        return rootView;
    }

    public String getRepositoryUri() {
        SharedPreferences preferences = getActivity().getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(REPOSITORY_URI_PREF, "");
    }

    public void setRepositoryUri(String repositoryUri) {
        SharedPreferences preferences = getActivity().getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(REPOSITORY_URI_PREF, repositoryUri);
        editor.commit();
    }
}
