package com.example.daniel.eksperiment1.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.daniel.eksperiment1.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button button = (Button) findViewById(R.id.post);
        registerForContextMenu(button);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderIcon(android.R.drawable.ic_dialog_info);
        menu.setHeaderTitle("POST menu");
        menu.add(0, 0, 0, "Blog");
        menu.add(0, 1, 1, "Facebook");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent(this, BlogActivity.class);
                startActivity(intent);
                return true;
            case 1:
                Intent intent1 = new Intent(this, FacebookActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void get(View view) {
        Intent intent = new Intent(this, KategoriActivity.class);
        startActivity(intent);
    }
}
