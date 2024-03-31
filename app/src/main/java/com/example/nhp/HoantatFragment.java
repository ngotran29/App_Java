package com.example.nhp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HoantatFragment extends Fragment {
    Button trangchu2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hoantat, container, false);
        // Find the button in the inflated layout
        trangchu2 = rootView.findViewById(R.id.trangchu2);

        // Set onClickListener for the button
        trangchu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), VeFragment.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}