package com.example.restaurantadvisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        TextView tv = findViewById(R.id.dispalyRestaurantName);

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