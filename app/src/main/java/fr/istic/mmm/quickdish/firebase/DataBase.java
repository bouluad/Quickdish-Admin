package fr.istic.mmm.quickdish.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import fr.istic.mmm.quickdish.bo.Client;
import fr.istic.mmm.quickdish.bo.Dish;
import fr.istic.mmm.quickdish.bo.Order;

/**
 * Created by bouluad on 26/03/17.
 */
public class DataBase {

    FirebaseDatabase database;

    public DataBase() {

        // Connect to the Firebase database
        database = FirebaseDatabase.getInstance();

    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void getDishsByRestoId(String id, final Command c) {

        // Get a reference to the todoItems child items it the database
        final DatabaseReference myRef = database.getReference(id).child("menu");


        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Dish dish = dataSnapshot.getValue(Dish.class);
                if (dish != null) {
                    c.exec(dish);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saveOrders(Order order) {

        final DatabaseReference orderRef = database.getReference(order.getTableNumber().substring(0, 3)).child("order").child(String.valueOf(order.getId()));

        DatabaseReference databaseReference = orderRef.push();
        databaseReference.setValue(order);

    }

    public void getOrdersByRestoId(String id, final Command c) {

        // Get a reference to the todoItems child items it the database
        final DatabaseReference myRef = database.getReference(id).child("order");


        myRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Order order = dataSnapshot.getValue(Order.class);
                if (order != null) {
                    c.exec(order);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saveClient(Client client, String qrCode) {

        final DatabaseReference orderRef = database.getReference(qrCode.substring(0, 3)).child("clients");

        DatabaseReference databaseReference = orderRef.push();
        databaseReference.setValue(client);

    }


    public void getLastIdOrder(final Command c) {
        final DatabaseReference databaseReference = database.getReference("100");
        Query lastQuery = databaseReference.child("order").orderByKey().limitToLast(1);

        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren() != null) {
                    DataSnapshot dataSnapshot1 = dataSnapshot.getChildren().iterator().next();//.getValue(Order.class);
                    Order order = dataSnapshot1.getValue(Order.class);
                    //System.out.println(dataSnapshot.child("-Kg5UA8l6gztUkdEtyN1").getValue().toString());
                    System.out.println("last dish id ----> " + order.getId());

                    c.exec(order.getId());
                    //String message = dataSnapshot.child("message").getValue(Order).toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public interface Command {
        public void exec(Object o);
    }


}
