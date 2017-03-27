package fr.istic.mmm.quickdish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import fr.istic.mmm.quickdish.R;
import fr.istic.mmm.quickdish.bo.Dish;

public class AddDish extends AppCompatActivity {

    private TextView description;
    private TextView nbpoint;
    private Dish dish;
    private TextView title;
    private TextView price;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        final Spinner spinnerRegion = (Spinner) findViewById(R.id.spinnerType);
        String[] lRegion = {"Entrées", "Plats", "Desserts"};
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lRegion);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(dataAdapterR);

        Button btnAdd = (Button) findViewById(R.id.btn_add);

        dish = new Dish("100", "", "", "", 0, "");
        title = (TextView) findViewById(R.id.titleAdd);
        price = (TextView) findViewById(R.id.priceAdd);
        description = (TextView) findViewById(R.id.descriptionAdd);
        nbpoint = (TextView) findViewById(R.id.nbPointAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dish.setTitle(title.getText().toString());
                dish.setPrice(price.getText().toString());
                dish.setDescription(description.getText().toString());
                dish.setNumberOfPoint(Integer.parseInt((nbpoint.getText().toString())));

                spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        type = String.valueOf(spinnerRegion.getSelectedItem());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }

                });

                // TODO : Spinner return Null
                System.out.println(" TYPE : " + type);

                final DatabaseReference menuRef = FirebaseDatabase.getInstance().getReference("100").child("menu");
                DatabaseReference databaseReference = menuRef.push();
                String key = databaseReference.getKey();
                dish.setId(key);
                databaseReference.setValue(dish);


                Toast.makeText(AddDish.this, "The add was well done", Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(AddDish.this, MainActivity.class);
                setResult(10, myIntent);
                finish();
                startActivity(myIntent);
            }
        });

    }
}
