package com.example.myapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRewardsFragment extends Fragment {


    public MyRewardsFragment() {
        // Required empty public constructor
    }


    private RecyclerView rewardsRecyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate( R.layout.fragment_my_rewards, container, false );

       rewardsRecyclerview = view.findViewById( R.id.rv_my_rewards );
        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add( new RewardModel( "CashBack","till 2nd june 2023","GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "Discount","till 2nd june 2023","GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "BUY 1 Get 2 Free ","till 2nd june 2023","GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "CashBack","till 2nd june 2023","GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "Discount","till 2nd june 2023","GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "BUY 1 Get 2 Free ","till 2nd june 2023","GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "CashBack","till 2nd june 2023","GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "Discount","till 2nd june 2023","GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "BUY 1 Get 2 Free ","till 2nd june 2023","GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );

        MyRewardAdapter myRewardAdapter = new MyRewardAdapter( rewardModelList,false );
        rewardsRecyclerview.setAdapter( myRewardAdapter );
        myRewardAdapter.notifyDataSetChanged();


        return view;
    }
}