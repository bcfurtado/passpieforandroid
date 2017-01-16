package io.github.bcfurtado.passpieforandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.bouncycastle.openpgp.PGPException;

import java.io.IOException;

import io.github.bcfurtado.passpieforandroid.database.Account;
import io.github.bcfurtado.passpieforandroid.database.AccountsAdapter;
import io.github.bcfurtado.passpieforandroid.utils.DecryptorHelper;

public class AccountConfirmationDialog extends DialogFragment {


    public static final String ENCRYPTED_PASSWORD = "ENCRYPTED_PASSWORD";

    public static AccountConfirmationDialog newInstance(String encryptedPassword) {
        AccountConfirmationDialog dialog = new AccountConfirmationDialog();

        Bundle arguments = new Bundle();
        arguments.putString(AccountConfirmationDialog.ENCRYPTED_PASSWORD, encryptedPassword);
        dialog.setArguments(arguments);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle arguments = getArguments();
        final String encryptedPassword = arguments.getString(ENCRYPTED_PASSWORD);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layoutDialog = inflater.inflate(R.layout.account_confirmation_layout, null);
        final EditText passphraseEditText = (EditText) layoutDialog.findViewById(R.id.editTextPassphrase);

        builder.setTitle("Passphrase")
                .setMessage("Insert your passphrase. Your passphrase will not be stored anywhere.")
                .setView(layoutDialog)
                .setPositiveButton("Copy Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String decryptedPassword  = DecryptorHelper.decryptPassword(getActivity(), encryptedPassword, passphraseEditText.getText().toString());

                            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("your password", decryptedPassword);
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(getActivity(), "Password copied to your clipboard.", Toast.LENGTH_SHORT).show();

                        } catch (IOException | PGPException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Was not possible decrypt your account.", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNeutralButton("Show Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String decryptedPassword = DecryptorHelper.decryptPassword(getActivity(), encryptedPassword, passphraseEditText.getText().toString());
                            Toast.makeText(getActivity(), String.format("Your password is:\r\n%s", decryptedPassword), Toast.LENGTH_LONG).show();
                        } catch (IOException | PGPException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Was not possible decrypt your account.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return builder.create();
    }

}
