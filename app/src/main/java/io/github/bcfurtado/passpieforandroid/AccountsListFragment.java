package io.github.bcfurtado.passpieforandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.Arrays;

import io.github.bcfurtado.passpieforandroid.database.PasspieDatabase;

public class AccountsListFragment extends Fragment {

    public BaseAdapter baseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.accounts_list_fragment, container, false);

        PasspieDatabase passpieDatabase = new PasspieDatabase(getContext());

        baseAdapter = new ArrayAdapter<String>(rootView.getContext(),
            android.R.layout.simple_list_item_1, passpieDatabase.getAccountsAsString()
        );
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(baseAdapter);

        return rootView;

    }
}
