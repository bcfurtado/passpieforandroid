package io.github.bcfurtado.passpieforandroid.intro;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class NotImplementedClickListener implements View.OnClickListener {

    private Context context;

    public NotImplementedClickListener(Context context) {
        this.context = context;

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, "Not implemented yet.", Toast.LENGTH_SHORT).show();
    }
}
