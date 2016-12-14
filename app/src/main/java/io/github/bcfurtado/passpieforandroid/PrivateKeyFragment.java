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

public class PrivateKeyFragment extends Fragment {

    public final static String APPLICATION_PREFERENCES = "APPLICATION_PREFERENCES";
    public final static String PRIVATE_KEY_PREF = "PRIVATE_KEY_PREF";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.private_key_fragment, container, false);

        final EditText privateKeyEditText;
        privateKeyEditText = (EditText) rootView.findViewById(R.id.privateKeyEditText);

        privateKeyEditText.setText(getPrivateKey());

        privateKeyEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    setPrivateKey(privateKeyEditText.getText().toString());
                }
            }
        });

        return rootView;
    }


    public String getPrivateKey() {
        SharedPreferences preferences = getActivity().getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getString(PRIVATE_KEY_PREF, "");
    }

    public void setPrivateKey(String privateKey) {
        SharedPreferences preferences = getActivity().getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PRIVATE_KEY_PREF, privateKey);
        editor.commit();
    }
}
