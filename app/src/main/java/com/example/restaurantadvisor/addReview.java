package com.example.restaurantadvisor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class addReview extends AppCompatActivity {
    private String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_review);

        restaurantName = getIntent().getStringExtra("restaurantname");


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void newReview(View v)
    {
        // Have to add the code to add a review to a database

        TextView tv = (TextView) findViewById(R.id.displayDebug);

        EditText iReview = (EditText) findViewById(R.id.enterReview);
        EditText iStar = (EditText) findViewById(R.id.enterStar);



        String review = iReview.getText().toString();
        String starText = iStar.getText().toString();
        if (starText.isEmpty()) {
            tv.setText("Invalid Entries");
            return;
        }

        int stars = Integer.parseInt(starText);

        String sp = getPackageName() + "prefs";
        SharedPreferences sps = getSharedPreferences(sp,MODE_PRIVATE);
        String username = sps.getString("username","unknown");



        if(review.isEmpty() || stars > 5 || stars < 1){
            tv.setText("Invalid Entries");
        }
        else{

            String dbPath = getDatabasePath("restaurantreviews.sqlite").getPath();
            SQLiteDatabase myDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);

            String tableName = username.replaceAll("[^a-zA-Z0-9_]", "_");

            // myDB.execSQL("DROP TABLE IF EXISTS + " tableName); // This is for deleting a table that already exists sort of resets it

            String createTable = "CREATE TABLE IF NOT EXISTS " + tableName + " (restaurantName TEXT, stars INTEGER, customerReviews TEXT)";
            myDB.execSQL(createTable);

            String insertSql = "INSERT INTO " + tableName + " (restaurantName, stars, customerReviews) VALUES (?, ?, ?)";
            myDB.execSQL(insertSql, new Object[]{restaurantName, stars, review});

            myDB.close();

            Intent nextAct = new Intent(this, MainActivity.class);
            startActivity(nextAct);
        }
    }

    public void goBack1 (View v)
    {
        Intent nextAct = new Intent(this, reviews.class);
        nextAct.putExtra("restaurantname", restaurantName);
        startActivity(nextAct);
    }

}