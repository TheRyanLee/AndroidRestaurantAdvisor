package com.example.restaurantadvisor;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class reviews extends AppCompatActivity {

    private String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reviews);

        restaurantName = getIntent().getStringExtra("restaurantname");

        TextView tvReviews = (TextView) findViewById(R.id.dispalyReviews);
        TextView tvStars = (TextView) findViewById(R.id.displayStars);


        copyDatabaseIfNeeded();
        String dbPath = getDatabasePath("restaurantreviews.sqlite").getPath();
        SQLiteDatabase myDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        String tablename = restaurantName;

        String query = "SELECT * FROM " + tablename + ";";
        Cursor crs = myDB.rawQuery(query, null);

        String sReviews = "";
        double stars = 0;
        int count = 0;

        if (crs.moveToFirst())
            do {
                sReviews += crs.getString(1) + "\n\n";
                stars += crs.getInt(0);
                count++;
            } while(crs.moveToNext());

        crs.close();
        myDB.close();

        String starsText = "RATED \n" + String.format("%.1f", (stars/count)) + " Stars out of 5 Stars";

        tvStars.setText(starsText);
        tvReviews.setText(sReviews);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void goBackOne (View v)
    {
        Intent nextAct = new Intent(this, MainActivity.class);
        startActivity(nextAct);
    }


    public void addReview (View v)
    {
        Intent nextAct = new Intent(this, addReview.class);
        nextAct.putExtra("restaurantname", restaurantName);
        startActivity(nextAct);
    }


    private void copyDatabaseIfNeeded() {
        String dbName = "restaurantreviews.sqlite";
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



}
