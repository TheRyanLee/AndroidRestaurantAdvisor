package com.example.restaurantadvisor;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.Map;

public class restaurantinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restaurantinfo);

        String restaurantName = getIntent().getStringExtra("restaurantname");
        String cRestaurantName = restaurantName.substring(0,1).toUpperCase() + restaurantName.substring(1);

        TextView tv = (TextView) findViewById(R.id.dispalyRestaurantName);

        tv.setText(cRestaurantName);

        Map<String, Integer> restaurantImages = new HashMap<>();
        restaurantImages.put("culvers", R.drawable.culvers);
        restaurantImages.put("arbys", R.drawable.arby);
        restaurantImages.put("dairyqueen", R.drawable.dairyqueen);
        restaurantImages.put("wendys", R.drawable.wendy);
        restaurantImages.put("tacobell", R.drawable.tacobell);
        restaurantImages.put("subway", R.drawable.subway);
        restaurantImages.put("pizzaranch", R.drawable.pizzaranch);
        restaurantImages.put("papajohns", R.drawable.papajohns);
        restaurantImages.put("mcdonalds", R.drawable.mcdonalds);
        restaurantImages.put("littlecaesars", R.drawable.littlecaesars);
        restaurantImages.put("kfc", R.drawable.kfc);

        ImageView restaurantImage = findViewById(R.id.displayRestaurantLogo);

        if (restaurantImages.containsKey(restaurantName)) {
            restaurantImage.setImageResource(restaurantImages.get(restaurantName));
        }

        TextView tv2 = (TextView) findViewById(R.id.displayInfo);


        copyDatabaseIfNeeded();
        String dbPath = getDatabasePath("restaurantinfo.sqlite").getPath();
        SQLiteDatabase myDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        String tablename = "restaurants";


        String query = "SELECT address, hours, type_of_dining, type_of_cuisine FROM " + tablename + " WHERE restaurant_name LIKE '" + restaurantName + "';";
        Cursor crs = myDB.rawQuery(query, null);


        String rInfo = "";

        if(crs.moveToFirst())
        {
            rInfo = crs.getString(0) + "\n" + crs.getString(1) + "\n" + crs.getString(2) + "\n" + crs.getString(3);
        }

        tv2.setText(rInfo);

        crs.close();
        myDB.close();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void copyDatabaseIfNeeded() {
        String dbName = "restaurantinfo.sqlite";
        File dbFile = getDatabasePath(dbName);

        if (dbFile.exists()) {
            return;
        }

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



    public void goBack (View v)
    {
        Intent nextAct = new Intent(this, MainActivity.class);
        startActivity(nextAct);
    }

    public void moveToCarryout (View v)
    {
        Intent nextAct = new Intent(this, carryout.class);
        startActivity(nextAct);
    }

    public void moveToReviews (View v)
    {
        Intent nextAct = new Intent(this, reviews.class);
        startActivity(nextAct);
    }
}