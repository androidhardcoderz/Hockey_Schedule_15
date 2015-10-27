package com.hockeyschedule15.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.appbrain.AppBrainBanner;

/**
 * Created by Scott on 10/24/2015.
 */
public class TodayFragment extends Fragment {

    LinearLayout gamesLayout;
    Schedule schedule;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view;

        if(schedule.getGames().size() == 0){
            view = inflater.inflate(R.layout.no_games_fragment,container,false);
        }else{
            view = inflater.inflate(R.layout.today_fragment,container,false);
            gamesLayout = (LinearLayout) view.findViewById(R.id.gamesLinearLayout);

            addBanner(gamesLayout);

            for(Game game: schedule.getGames()){
                gamesLayout.addView(new GameLayout(getActivity(),game));
            }
        }

        return view;
    }

    public void addBanner(ViewGroup parent) {
        AppBrainBanner banner = new AppBrainBanner(getContext());
        parent.addView(banner);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        schedule = getArguments().getParcelable("GAMES");

    }
}
