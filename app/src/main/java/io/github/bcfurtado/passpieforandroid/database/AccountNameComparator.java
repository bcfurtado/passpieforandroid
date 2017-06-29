package io.github.bcfurtado.passpieforandroid.database;

import java.util.Comparator;


public class AccountNameComparator implements Comparator<Account> {

    @Override
    public int compare(Account account1, Account account2) {
        return account1.getFullname().compareTo(account2.getFullname());
    }

}
