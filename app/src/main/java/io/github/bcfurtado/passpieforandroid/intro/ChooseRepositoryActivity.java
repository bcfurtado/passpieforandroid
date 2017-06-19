package io.github.bcfurtado.passpieforandroid.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Arrays;

import io.github.bcfurtado.passpieforandroid.MainActivity;
import io.github.bcfurtado.passpieforandroid.R;

public class ChooseRepositoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_repository_layout);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListAdapter listAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                Arrays.asList(
                        "my-username/my-public-repository-1",
                        "my-username/my-public-repository-2",
                        "my-username/my-public-repository-3",
                        "my-username/my-private-repository-1",
                        "my-username/my-private-repository-2",
                        "my-organization/my-private-repository-1"
                )
        );
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }
}
