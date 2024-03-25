package com.example.foddapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button Add, SubmitButton;
    EditText FoodNameInput,GramInput;

    private RecyclerView recyclerView;

    private MyAdapter adapter;
    private List<FoodItem> itemList;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Add = findViewById(R.id.add_button);
        SubmitButton = findViewById(R.id.submint_button);
        FoodNameInput = findViewById(R.id.Food_Input);
        GramInput = findViewById(R.id.Gram_input);
        recyclerView = findViewById(R.id.selected_foods_recyclerview);
        itemList = new ArrayList<>();

        adapter = new MyAdapter(itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        databaseHelper = new DatabaseHelper(this);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataToRecyclerView();
            }
        });

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeRecyclerViewDataInDatabase();
            }
        });


    }

    private void addDataToRecyclerView() {
        // Get the text from your text field or wherever you have it
        String FoodName =FoodNameInput.getText().toString().trim();
        String Grams = GramInput.getText().toString().trim();
        // Check if the text is not empty
        if (!FoodName.isEmpty() && !Grams.isEmpty() ) {
            // Create a new item based on your data model
            FoodItem newData = new FoodItem(FoodName,Integer.parseInt(Grams));

            // Add the new data to your adapter
            adapter.addData(newData);

            // Notify the adapter that the data set has changed
            adapter.notifyDataSetChanged();

            // Optionally, scroll to the bottom of the RecyclerView to show the newly added item
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);

            // Clear the text field for the next input
            FoodNameInput.setText("");
            GramInput.setText("");

        } else {
            // Display a message or handle the case where the text is empty
            Toast.makeText(this, "Please enter Both Name and Grams", Toast.LENGTH_SHORT).show();
        }
    }

    private void storeRecyclerViewDataInDatabase() {
        for (FoodItem item : itemList) {
            databaseHelper.addFoodItem(item);
        }
        Toast.makeText(MainActivity.this, "Data stored in database", Toast.LENGTH_SHORT).show();
    }
}