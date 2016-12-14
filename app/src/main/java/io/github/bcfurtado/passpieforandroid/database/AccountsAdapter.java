package io.github.bcfurtado.passpieforandroid.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AccountsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private PasspieDatabase passpieDatabase;
    private List<Account> accounts;

    public AccountsAdapter(Context context) {
        this.passpieDatabase = new PasspieDatabase(context);
        this.accounts = passpieDatabase.getAccounts();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public Account getItem(int index) {
        return accounts.get(index);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            Account account = getItem(i);

            view = this.inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);

            TextView text = (TextView) view.findViewById(android.R.id.text1);
            text.setText(account.getFullname());
        }
        return view;
    }
}
