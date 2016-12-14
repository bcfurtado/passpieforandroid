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

import io.github.bcfurtado.passpieforandroid.database.PreferenceManager;

public class PrivateKeyFragment extends Fragment {

    private PreferenceManager preferenceManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.private_key_fragment, container, false);

        preferenceManager = new PreferenceManager(getContext());

        final EditText privateKeyEditText;
        privateKeyEditText = (EditText) rootView.findViewById(R.id.privateKeyEditText);

        privateKeyEditText.setText(preferenceManager.getPrivateKey());

        privateKeyEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    preferenceManager.setPrivateKey(privateKeyEditText.getText().toString());
                }
            }
        });

        return rootView;
    }



}
