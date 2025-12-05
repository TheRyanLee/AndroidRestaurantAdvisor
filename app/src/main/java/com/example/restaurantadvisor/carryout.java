package com.example.restaurantadvisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carryout);


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
            return insets;
        });
    }

    public void goBack (View v)
    {
        Intent nextAct = new Intent(this, MainActivity.class);
        startActivity(nextAct);
    }


}