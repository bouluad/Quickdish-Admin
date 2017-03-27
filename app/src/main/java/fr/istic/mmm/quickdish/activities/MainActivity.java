package fr.istic.mmm.quickdish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.istic.mmm.quickdish.R;
import fr.istic.mmm.quickdish.bo.Dish;
import fr.istic.mmm.quickdish.bo.Order;
import fr.istic.mmm.quickdish.firebase.DataBase;

public class MainActivity extends AppCompatActivity {

    //Menu list
    android.widget.ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> itemList;
    HashMap<String, List<Dish>> listDataChild;
    //Save menu list in JSONObject
    JSONObject jsonResult;
    //Database
    DataBase database;
    List<Dish> dishes = new ArrayList<Dish>();
    private TextView scanResults;
    //Order
    private Order order;
    //save qrcode
    private String qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize database
        database = new DataBase();

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        itemList = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Dish>>();

        listAdapter = new ExpandableListAdapter(this, itemList, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                dishes.add(listDataChild.get(itemList.get(groupPosition)).get(childPosition));

                database.getLastIdOrder(new DataBase.Command() {
                    @Override
                    public void exec(Object o) {

                        //   order = new Order((Integer.valueOf(String.valueOf(o)) + 1), dishes, 1, false, qrCode);
                    }
                });


                Intent myIntent = new Intent(MainActivity.this, UpdateDish.class);

                // passe the order to next activity
                myIntent.putExtra("dish", listDataChild.get(itemList.get(groupPosition)).get(childPosition));
                setResult(10, myIntent);
                finish();
                startActivity(myIntent);
                return false;
            }
        });


        // initialise the menu list display
        itemList.clear();
        itemList.add("Entrées");
        itemList.add("Plats");
        itemList.add("Desserts");

        showMenuList("100");

        Button buttonAdd = (Button) findViewById(R.id.btnAddNew);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, AddDish.class);
                setResult(10, myIntent);
                finish();
                startActivity(myIntent);
            }
        });

    }

    //parse result (json file) and add elements to menu list
    public void showMenuList(String barcode) {
        // show the menu
        final List<Dish> entrees = new ArrayList<Dish>();
        final List<Dish> plats = new ArrayList<Dish>();
        final List<Dish> desserts = new ArrayList<Dish>();

        database.getDishsByRestoId(barcode, new DataBase.Command() {
            @Override
            public void exec(Object o) {
                Dish dish = ((Dish) o);
                String type = dish.getType();
                switch (type) {
                    case "entrees":
                        entrees.add(dish);
                        break;
                    case "plats":
                        plats.add(dish);
                        break;
                    case "desserts":
                        desserts.add(dish);
                        break;
                    default:
                        break;
                }
            }
        });

        listDataChild.put(itemList.get(0), entrees);
        listDataChild.put(itemList.get(1), plats);
        listDataChild.put(itemList.get(2), desserts);

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

            Intent myIntent = new Intent(MainActivity.this, ClientActivity.class);
            startActivity(myIntent);
            return true;
        }
        if (id == R.id.action_menu) {

            return true;
        }
        if (id == R.id.action_orders) {

            Intent myIntent = new Intent(MainActivity.this, TheCommandActivity.class);
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
