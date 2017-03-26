package fr.istic.mmm.quickdish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.istic.mmm.quickdish.R;
import fr.istic.mmm.quickdish.bo.Dish;

public class UpdateDish extends AppCompatActivity {

    private Dish dish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dish);

        Button btnUpdate = (Button) findViewById(R.id.btn_update);

        dish = (Dish) getIntent().getSerializableExtra("dish");
        final TextView title = (TextView) findViewById(R.id.txtTitle);
        final TextView price = (TextView) findViewById(R.id.txtPrice);
        TextView description = (TextView) findViewById(R.id.txtDescription);
        TextView nbpoint = (TextView) findViewById(R.id.txtNbPoint);
        title.setText(dish.getTitle());
        price.setText(dish.getPrice());
        description.setText(dish.getDescription());
        nbpoint.setText(String.valueOf(dish.getNumberOfPoint()));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dish.setTitle(title.getText().toString());
                dish.setPrice(price.getText().toString());
                dish.setTitle(title.getText().toString());

            }
        });
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

            Intent myIntent = new Intent(UpdateDish.this, ClientActivity.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.action_menu) {

            Intent myIntent = new Intent(UpdateDish.this, MainActivity.class);
            startActivity(myIntent);

            return true;
        }
        if (id == R.id.action_orders) {

            Intent myIntent = new Intent(UpdateDish.this, TheCommandActivity.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
