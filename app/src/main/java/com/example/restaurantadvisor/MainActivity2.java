package com.example.restaurantadvisor;

import android.content.Intent;
import android.database.Cursor;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    // This should make it so that you don't have to put the sqlite file into the device manager each time.

    private void copyDatabaseIfNeeded() {
        String dbName = "accounts.sqlite";
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


    //create account feature, checks if e-mail exists in database, if not stores new info in database
    public void createAccount (View v){

        copyDatabaseIfNeeded();

        String dbPath = getDatabasePath("accounts.sqlite").getPath();
        SQLiteDatabase myDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        String tablename = "users";

        TextView tv = (TextView) findViewById(R.id.dispalyResult);

        EditText iEmail = (EditText) findViewById(R.id.enterEmail);
        EditText iPassword = (EditText) findViewById(R.id.enterPassword);

        String email = iEmail.getText().toString();
        String password = iPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            tv.setText("Email and password cannot be empty!");
            return;
        }

        String query = "SELECT * FROM " + tablename +  " WHERE email = ?";
        Cursor crs = myDB.rawQuery(query, new String[]{email});
        if(crs.moveToFirst()){
            tv.setText("Account Already Exists! \nPlease click Sign in");

        }
        else {
            String insertsql = "INSERT INTO users (email, password) VALUES (?, ?)";
            myDB.execSQL(insertsql, new Object[]{email, password});
            tv.setText("Account Created! \nPlease click sign in");
        }
        crs.close();
        myDB.close();

    }



    //sign in feature, checks if email exists in database, if does, checks if password matches, if so signs into nextScreen.
    public void signIn (View v){

        copyDatabaseIfNeeded();

        String dbPath = getDatabasePath("accounts.sqlite").getPath();
        SQLiteDatabase myDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
        String tablename = "users";

        TextView tv = (TextView) findViewById(R.id.dispalyResult);

        EditText iEmail = (EditText) findViewById(R.id.enterEmail);
        EditText iPassword = (EditText) findViewById(R.id.enterPassword);

        String email = iEmail.getText().toString();
        String password = iPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            tv.setText("Email and password cannot be empty!");
            return;
        }

        String query = "SELECT * FROM " + tablename +  " WHERE email = ?";
        Cursor crs = myDB.rawQuery(query, new String[]{email});
        if(crs.moveToFirst()){
            Intent nextAct = new Intent(this, MainActivity.class);
            startActivity(nextAct);
        }
        else {
            tv.setText("Account Does Not Exist! \nPlease click Create Account");
        }
        crs.close();
        myDB.close();
    }
}