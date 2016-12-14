package io.github.bcfurtado.passpieforandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import io.github.bcfurtado.passpieforandroid.database.AccountsAdapter;

public class AccountsListFragment extends Fragment {

    public BaseAdapter baseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.accounts_list_fragment, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Syncing your account...", Snackbar.LENGTH_LONG).show();
                String repositoryUrl = "";
                SyncAccountsTask task = new SyncAccountsTask(getContext(), repositoryUrl, view);
                task.execute();
            }
        });

        baseAdapter = new AccountsAdapter(rootView.getContext());

        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(baseAdapter);

        return rootView;

    }
}
