package io.github.bcfurtado.passpieforandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.github.bcfurtado.passpieforandroid.database.Account;
import io.github.bcfurtado.passpieforandroid.database.AccountsAdapter;
import io.github.bcfurtado.passpieforandroid.utils.DecryptorHelper;

public class AccountConfirmationOnClickListener implements View.OnClickListener {

    private Account account;

    public AccountConfirmationOnClickListener(Account account) {
        this.account = account;
    }

    @Override
    public void onClick(final View view) {
        Context context = view.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layoutDialog = layoutInflater.inflate(R.layout.account_confirmation_layout, null);
        final EditText passphraseEditText = (EditText) layoutDialog.findViewById(R.id.editTextPassphrase);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getContext());
        alertBuilder.setTitle("Passphrase")
                .setMessage("Insert your passphrase. Your passphrase will not be stored anywhere.")
                .setView(layoutDialog)
                .setPositiveButton("Copy Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(view.getContext(), "Not implemented yet.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("Show Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String decryptedPassword = getUnencryptedPassword(view.getContext(), account.getPassword(), passphraseEditText.getText().toString());
                        Toast.makeText(view.getContext(), String.format("Your password is:\r\n%s", decryptedPassword), Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    private String getUnencryptedPassword(Context context, String encryptedPassword, String passphrase) {
        Log.d(AccountsAdapter.class.getSimpleName(), String.format("Encrypted Password: %s", encryptedPassword));
        return DecryptorHelper.decryptPassword(context, encryptedPassword, passphrase);
    }
}
