package fr.istic.mmm.quickdish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.istic.mmm.quickdish.R;
import fr.istic.mmm.quickdish.bo.Order;

public class ValidateCommand extends AppCompatActivity {

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_command);

        order = (Order) getIntent().getSerializableExtra("order");
        String orderString = order.dishsToString();
        TextView resumeCommand = (TextView) findViewById(R.id.resumeCommand);
        resumeCommand.setText(orderString);

    }


    public void passOrder(View view) {

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("100").child("order");
        order.setValidation(true);
        database.child(order.getId()).setValue(order);

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

            Intent myIntent = new Intent(ValidateCommand.this, ClientActivity.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.action_menu) {
            Intent myIntent = new Intent(ValidateCommand.this, MainActivity.class);
            startActivity(myIntent);

            return true;
        }
        if (id == R.id.action_orders) {

            Intent myIntent = new Intent(ValidateCommand.this, TheCommandActivity.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
