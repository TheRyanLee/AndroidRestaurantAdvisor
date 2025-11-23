package com.example.restaurantadvisor.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurantadvisor.R;
import com.example.restaurantadvisor.databinding.FragmentHomeBinding;
import com.example.restaurantadvisor.restaurantinfo;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.culvers.setOnClickListener(this::selectedRestaurant);
        binding.arbys.setOnClickListener(this::selectedRestaurant);
        binding.dairyqueen.setOnClickListener(this::selectedRestaurant);
        binding.wendys.setOnClickListener(this::selectedRestaurant);
        binding.tacobell.setOnClickListener(this::selectedRestaurant);
        binding.subway.setOnClickListener(this::selectedRestaurant);
        binding.pizzaranch.setOnClickListener(this::selectedRestaurant);
        binding.papajohns.setOnClickListener(this::selectedRestaurant);
        binding.mcdonalds.setOnClickListener(this::selectedRestaurant);
        binding.littlecaesars.setOnClickListener(this::selectedRestaurant);
        binding.kfc.setOnClickListener(this::selectedRestaurant);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void selectedRestaurant(View view) {
        int id = view.getId();

        String restaurantName = "";


        if (id == binding.culvers.getId()) {
            restaurantName = "culvers";
        } else if (id == binding.arbys.getId()) {
            restaurantName = "arbys";
        } else if (id == binding.dairyqueen.getId()) {
            restaurantName = "dairyqueen";
        } else if (id == binding.wendys.getId()) {
            restaurantName = "wendys";
        } else if (id == binding.tacobell.getId()) {
            restaurantName = "tacobell";
        } else if (id == binding.subway.getId()) {
            restaurantName = "subway";
        } else if (id == binding.pizzaranch.getId()) {
            restaurantName = "pizzaranch";
        } else if (id == binding.papajohns.getId()) {
            restaurantName = "papajohns";
        } else if (id == binding.mcdonalds.getId()) {
            restaurantName = "mcdonalds";
        } else if (id == binding.littlecaesars.getId()) {
            restaurantName = "littlecaesars";
        } else if (id == binding.kfc.getId()) {
            restaurantName = "kfc";
        }

        if(!restaurantName.isEmpty()){
            Intent newAct = new Intent(getActivity(), restaurantinfo.class);
            newAct.putExtra("restaurantname", restaurantName);
            startActivity(newAct);
        }

    }



}