package com.hockeyschedule15.app;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Scott on 9/18/2015.
 */
public class TeamColors {

    public static int current_team_color;

    ArrayList<Integer> colors = new ArrayList<Integer>();

    public TeamColors(Context context) {

        colors.add(R.color.Anaheim_Ducks);
        colors.add(R.color.Arizona_Coyotes);
        colors.add(R.color.Boston_Bruins);
        colors.add(R.color.Buffalo_Sabres);
        colors.add(R.color.Calgary_Flames);
        colors.add(R.color.Carolina_Hurricanes);
        colors.add(R.color.Chicago_Blackhawks);
        colors.add(R.color.Colorado_Avalanche);
        colors.add(R.color.Columbus_Blue_Jackets);
        colors.add(R.color.Dallas_Stars);
        colors.add(R.color.Detroit_Red_Wings);
        colors.add(R.color.Edmonton_Oilers);
        colors.add(R.color.Florida_Panthers);
        colors.add(R.color.Los_Angeles_Kings);
        colors.add(R.color.Minnestoa_Wild);
        colors.add(R.color.Montreal_Canadiens);
        colors.add(R.color.Nashville_Predators);
        colors.add(R.color.New_Jersey_Devils);
        colors.add(R.color.New_York_Islanders);
        colors.add(R.color.New_York_Rangers);
        colors.add(R.color.Ottawa_Senators);
        colors.add(R.color.Philadelphia_Flyers);
        colors.add(R.color.Pittsburg_Penguins);
        colors.add(R.color.San_Jose_Sharks);
        colors.add(R.color.St_Louis_Blues);
        colors.add(R.color.Tampa_Bay_Lightning);
        colors.add(R.color.Toronto_Maple_Leafs);
        colors.add(R.color.Vancouver_Canucks);
        colors.add(R.color.Washington_Capitals);
        colors.add(R.color.Winnipeg_Jets);

    }

    public ArrayList<Integer> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Integer> colors) {
        this.colors = colors;
    }


}
