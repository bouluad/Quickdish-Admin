package fr.istic.mmm.quickdish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.istic.mmm.quickdish.R;
import fr.istic.mmm.quickdish.bo.Order;
import fr.istic.mmm.quickdish.firebase.DataBase;

public class TheCommandActivity extends AppCompatActivity {


    android.widget.ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> itemList;
    HashMap<String, List<Order>> listDataChild;

    DataBase database;

    ListView mListView;

    List<Order> orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_command);


        // initialize database
        database = new DataBase();

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.commandList);

        // preparing list data
        itemList = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Order>>();

        listAdapter = new ExpandableListAdapterCommand(this, itemList, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Order o = listDataChild.get(itemList.get(groupPosition)).get(childPosition);
                o.setValidation(true);
                DatabaseReference myRef = database.getDatabase().getReference().child("order");


                Intent myIntent = new Intent(TheCommandActivity.this, ValidateCommand.class);

                // passe the order to next activity
                myIntent.putExtra("order", listDataChild.get(itemList.get(groupPosition)).get(childPosition));
                setResult(10, myIntent);
                finish();
                startActivity(myIntent);
                return false;
            }
        });


        // initialise the menu list display
        itemList.clear();
        itemList.add("Commands");

        showMenuList("100");
    }


    public void showMenuList(String barcode) {
        // show the menu
        final List<Order> orders = new ArrayList<Order>();

        database.getOrdersByRestoId(barcode, new DataBase.Command() {
            @Override
            public void exec(Object o) {
                Order order = ((Order) o);
                if (!order.isValidation()) {
                    orders.add(order);
                }
            }
        });

        listDataChild.put(itemList.get(0), orders);
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

            Intent myIntent = new Intent(TheCommandActivity.this, ClientActivity.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.action_menu) {
            Intent myIntent = new Intent(TheCommandActivity.this, MainActivity.class);
            startActivity(myIntent);

            return true;
        }
        if (id == R.id.action_orders) {

//            Intent myIntent = new Intent(TheCommandActivity.this, TheCommandActivity.class);
//            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
