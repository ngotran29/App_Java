package com.example.nhp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TabLayout tablayout;
    private ViewPager viewpager;
    private View mview;


    // TODO: Rename and change types of parameters


    public VeFragment() {
        // Required empty public constructor
    }


    public static VeFragment newInstance(String param1, String param2) {
        VeFragment fragment = new VeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mview = inflater.inflate((R.layout.fragment_ve), container, false);
        tablayout = mview.findViewById(R.id.tablayout);
        viewpager = mview.findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        return mview;
    }
}