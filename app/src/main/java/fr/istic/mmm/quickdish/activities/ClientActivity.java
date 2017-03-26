package fr.istic.mmm.quickdish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import fr.istic.mmm.quickdish.R;

public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {

//            Intent myIntent = new Intent(SplashActivity.this, MainActivity.class);
//            startActivity(myIntent);
            return true;
        }
        if (id == R.id.action_clients) {


            return true;
        }
        if (id == R.id.action_menu) {
            Intent myIntent = new Intent(ClientActivity.this, MainActivity.class);
            startActivity(myIntent);

            return true;
        }
        if (id == R.id.action_orders) {

            Intent myIntent = new Intent(ClientActivity.this, TheCommandActivity.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
