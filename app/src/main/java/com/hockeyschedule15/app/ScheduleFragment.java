package com.hockeyschedule15.app;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appbrain.AppBrainBanner;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Scott on 9/18/2015.
 */
public class ScheduleFragment extends Fragment {

    RelativeLayout headerLayout;
    String teamName;
    int teamIndex;
    TextView teamnameTextView;
    LinearLayout gamesLayout;
    ValueAnimator colorAnimation;
    ProgressBar bar;
    private LoadTeamGames loadTeamGames;
    private final String TAG = "Schedule Fragment";



    public ScheduleFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.schedule_fragment, container,false);

        gamesLayout = (LinearLayout) view.findViewById(R.id.gamesLinearLayout);
        teamnameTextView = (TextView) view.findViewById(R.id.teamnameTextView);
        headerLayout = (RelativeLayout) view.findViewById(R.id.teamHeaderRelativeLayout);
        bar = (ProgressBar) view.findViewById(R.id.progressBar);
        teamnameTextView.setText(teamName);

        TeamFonts.setCustomTeamFont(teamnameTextView,teamIndex);

        setUpdateColorAnimator();

        return view;
    }

    private void addGamesToLayout(ArrayList<Game> games) {
        bar.setVisibility(View.INVISIBLE); //remove loading bar if present

        addBanner(gamesLayout);

        for(Game game: games){

            GameLayout gRow = new GameLayout(getActivity(),game);
            gRow.setAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left));
            Animation anim = gRow.getAnimation();
            anim.setDuration(1000 + (games.indexOf(game) * 25));
            gamesLayout.addView(gRow, gamesLayout.getChildCount());
            gRow.startAnimation(anim);

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        colorAnimation.start();

        setLoadTeamGames(new LoadTeamGames());
        getLoadTeamGames().execute(teamName);
    }

    private void setUpdateColorAnimator() {

        Integer colorFrom = (Color.WHITE);
        Integer colorTo = getActivity().getResources().getColor(new TeamColors(getActivity()).getColors().get(teamIndex));
        colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(2500);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                headerLayout.setBackgroundColor((Integer) animator.getAnimatedValue());

            }

        });

    }

    public void addBanner(ViewGroup parent) {
        AppBrainBanner banner = new AppBrainBanner(getContext());
        parent.addView(banner);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        teamIndex = getArguments().getInt("INDEX");
        teamName = getArguments().getString("TEAM");

        TeamFonts.team_fonts_index = teamIndex;
        Log.i(TAG,teamName + " " + teamIndex);
    }

    public LoadTeamGames getLoadTeamGames() {
        return loadTeamGames;
    }

    public void setLoadTeamGames(LoadTeamGames loadTeamGames) {
        this.loadTeamGames = loadTeamGames;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(getLoadTeamGames() != null && getLoadTeamGames().getStatus() == AsyncTask.Status.RUNNING){
            getLoadTeamGames().cancel(true);
        }
    }

    class LoadTeamGames extends AsyncTask<String,Void,ArrayList<Game>>{


        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected ArrayList<Game> doInBackground(String... params) {

            try {
                return new TeamFetchTask(getActivity()).getGames(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(ArrayList<Game> games) {
            super.onPostExecute(games);
            Log.i(TAG, games.size() + " ");
            addGamesToLayout(games);
        }
    }
}
