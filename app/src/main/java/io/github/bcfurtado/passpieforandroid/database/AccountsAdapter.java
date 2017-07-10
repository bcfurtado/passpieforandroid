package io.github.bcfurtado.passpieforandroid.database;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.github.bcfurtado.passpieforandroid.AccountConfirmationDialog;


public class AccountsAdapter extends BaseAdapter {

    private final Context context;
    private LayoutInflater inflater;
    private PasspieDatabase passpieDatabase;
    private List<Account> accounts;

    public AccountsAdapter(Context context) {
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view;
        AccountHolder holder;
        final Account account = getItem(position);

        if (convertView == null) {
            view = this.inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            holder = new AccountHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (AccountHolder) view.getTag();
        }
        holder.name.setText(account.getFullname());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                AccountConfirmationDialog.newInstance(account.getPassword()).show(fm, "dialog");
            }
        });

        return view;
    }


    public void updateData() {
        this.accounts = passpieDatabase.getAccountsSortedByName();
        this.notifyDataSetChanged();
    }

    public class AccountHolder {

        final TextView name;

        public AccountHolder(View view) {
            this.name = (TextView) view.findViewById(android.R.id.text1);
        }

    }

}
