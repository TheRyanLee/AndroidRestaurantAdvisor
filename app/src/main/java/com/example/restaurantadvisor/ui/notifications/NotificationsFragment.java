package com.example.restaurantadvisor.ui.notifications;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.restaurantadvisor.R;
import com.example.restaurantadvisor.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String sp = requireActivity().getPackageName() + "prefs";
        SharedPreferences sps = requireActivity().getSharedPreferences(sp,0);
        String username = sps.getString("username","unknown");

        TextView tv = (TextView) root.findViewById(R.id.displayReviews);


        try {
            String dbPath = requireActivity().getDatabasePath("restaurantreviews.sqlite").getPath();
            SQLiteDatabase myDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);

            String tableName = username.replaceAll("[^a-zA-Z0-9_]", "_");

            String query = "SELECT * FROM " + tableName + ";";
            Cursor crs = myDB.rawQuery(query, null);

            String reviews = "";

            if (crs.moveToFirst())
                do {
                    reviews += "Reviewed " + crs.getString(0) + ": " + crs.getString(2) + " " + crs.getInt(1) + " Stars\n\n";
                } while (crs.moveToNext());

            crs.close();
            myDB.close();

            tv.setText(reviews);

        } catch (Exception e) {
            tv.setText("No Reviews Left");
        }

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}