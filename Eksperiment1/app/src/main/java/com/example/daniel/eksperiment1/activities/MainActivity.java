package com.example.daniel.eksperiment1.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.daniel.eksperiment1.R;

public class MainActivity extends Activity {
    public static final String ERROR = "error";
    public static final String DEBUG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button get = (Button) findViewById(R.id.get);
        Button post = (Button) findViewById(R.id.post);
        registerForContextMenu(get);
        registerForContextMenu(post);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        switch (v.getId()) {
            case R.id.get:
                menu.setHeaderIcon(android.R.drawable.ic_dialog_info);
                menu.setHeaderTitle("GET-menu");
                menu.add(0, 0, 0, "Nyhed");
                break;
            case R.id.post:
                menu.setHeaderIcon(android.R.drawable.ic_dialog_info);
                menu.setHeaderTitle("POST-menu");
                menu.add(0, 1, 0, "Blog");
                menu.add(0, 2, 0, "Facebook");
                menu.add(0, 3, 0, "Fronter");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                startActivity(new Intent(this, KategoriActivity.class));
                return true;
            case 1:
                startActivity(new Intent(this, BlogActivity.class));
                return true;
            case 2:
                startActivity(new Intent(this, FacebookActivity.class));
                return true;
            case 3:
                startActivity(new Intent(this, FronterLoginActivity.class));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}
