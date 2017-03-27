package fr.istic.mmm.quickdish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

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

                Intent myIntent = new Intent(TheCommandActivity.this, ValidateCommand.class);

                // passe the order to next activity
                myIntent.putExtra("order", listDataChild.get(itemList.get(groupPosition)).get(childPosition));
                setResult(10, myIntent);
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
                orders.add(order);
            }
        });

        listDataChild.put(itemList.get(0), orders);
    }
}
