package io.github.bcfurtado.passpieforandroid.preference;


import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import io.github.bcfurtado.passpieforandroid.R;
import io.github.bcfurtado.passpieforandroid.database.PreferenceManager;

public class PrivateKeyPreference extends DialogPreference {

    private PreferenceManager preferenceManager;

    public PrivateKeyPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        preferenceManager = new PreferenceManager(context);

        setDialogLayoutResource(R.layout.private_key_preference);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(null);

    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        TextView repositoryTextView = (TextView) view.findViewById(R.id.private_key_preference);
        repositoryTextView.setText(preferenceManager.getPrivateKey());
    }
}
