package com.example.restaurantadvisor;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class carryout extends AppCompatActivity {
    private String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carryout);

        restaurantName = getIntent().getStringExtra("restaurantname");

        String dbName = "restaurantmenus.sqlite";
        File dbFile = getDatabasePath(dbName);

        if (dbFile.exists()) {

        }
        else {
            dbFile.getParentFile().mkdirs();

            try {
                InputStream is = getAssets().open("databases/" + dbName);
                OutputStream os = new FileOutputStream(dbFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error copying preloaded database");
            }
        }




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            getRecords(v);
            return insets;
        });
    }

    public void goBack (View v)
    {
        Intent nextAct = new Intent(this, MainActivity.class);
        startActivity(nextAct);
    }

    public void getRecords(View v){
        TextView tvA = findViewById(R.id.displayInfo3);
        TextView tvB = findViewById(R.id.displayInfo4);
        TextView tvC = findViewById(R.id.displayInfo5);
        try {
            SQLiteDatabase myDB = openOrCreateDatabase("/data/data/" + getPackageName() +
                    "/databases/restaurantmenus.sqlite", MODE_PRIVATE, null) ;
            // This enables to use an SQL CREATE if we so choose
            String getorderNumbers = "SELECT ItemId FROM " + restaurantName;
            Cursor crs = myDB.rawQuery(getorderNumbers,null);
            String orderNumString = "";
            if (crs.moveToFirst())
                do {
                    orderNumString += crs.getString(0) + "\n\n";
                } while(crs.moveToNext());
            else {
                orderNumString = "No records in the database"; //crs.moveToFirst failed
                //tv.setText(minutesString);
            }
            crs.close();
            tvA.setText(orderNumString);
            String getItems = "SELECT ItemName FROM " + restaurantName;
            Cursor crs2 = myDB.rawQuery(getItems,null);
            String minutesString = "";
            if (crs2.moveToFirst())
                do {
                    minutesString += crs2.getString(0) + "\n";
                } while(crs2.moveToNext());
            else {
                minutesString = "No records in the database"; //crs.moveToFirst failed
                //tv.setText(minutesString);
            }
            tvB.setText(minutesString);
            crs2.close();
            String getPrice = "SELECT Price FROM " + restaurantName;
            Cursor crs3 = myDB.rawQuery(getPrice,null);
            String priceString = "";
            if (crs3.moveToFirst())
                do {
                    priceString += crs3.getString(0) + "\n\n";
                } while(crs3.moveToNext());
            else {
                priceString = "No records in the database"; //crs.moveToFirst failed
                //tv.setText(minutesString);
            }
            tvC.setText(priceString);
            crs3.close();
            myDB.close();

        }
        catch( SQLiteException e) {
            tvC.setText(e.getMessage());
        }

    }

    public void takeOrder(View v){
        TextView tvA = findViewById(R.id.textView8);
        EditText orderInput = findViewById(R.id.orderInput);
        String orderInputString = orderInput.getText().toString();
        Integer itemNum = Integer.parseInt(orderInputString);

        try {
            SQLiteDatabase myDB = openOrCreateDatabase("/data/data/" + getPackageName() +
                    "/databases/restaurantmenus.sqlite", MODE_PRIVATE, null) ;
            String getItems = "SELECT ItemName FROM " + restaurantName + " WHERE ItemId = '" + itemNum + "'";
            Cursor crs2 = myDB.rawQuery(getItems,null);
            String minutesString = "";
            if (crs2.moveToFirst())
                do {
                    minutesString += crs2.getString(0) + "\n";
                } while(crs2.moveToNext());
            else {
                minutesString = "No records in the database"; //crs.moveToFirst failed
                //tv.setText(minutesString);
            }
            crs2.close();
            String getPrice = "SELECT Price FROM " + restaurantName + " WHERE ItemId = '" + itemNum + "'";
            Cursor crs3 = myDB.rawQuery(getPrice,null);
            String priceString = "";
            if (crs3.moveToFirst())
                do {
                    priceString += crs3.getString(0) + "\n\n";
                } while(crs3.moveToNext());
            else {
                priceString = "No records in the database"; //crs.moveToFirst failed
                //tv.setText(minutesString);
            }
            tvA.setText("You Ordered " + minutesString + "\n Cost: "+ priceString);
            crs3.close();
            myDB.close();

        }
        catch( SQLiteException e) {
            tvA.setText(e.getMessage());
        }
    }


}