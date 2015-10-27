package com.hockeyschedule15.app;

import java.util.ArrayList;

/**
 * Created by Scott on 9/17/2015.
 */
public class TeamNames {

    public ArrayList<String> getTeamNames() {
        return teamNames;
    }

    public void setTeamNames(ArrayList<String> teamNames) {
        this.teamNames = teamNames;
    }

    private ArrayList<String> teamNames = new ArrayList<String>();

    public TeamNames() {

        teamNames.add("Anaheim Ducks");
        teamNames.add("Arizona Coyotes");
        teamNames.add("Boston Bruins");
        teamNames.add("Buffalo Sabres");
        teamNames.add("Calgary Flames");
        teamNames.add("Carolina Hurricanes");
        teamNames.add("Chicago Blackhawks");
        teamNames.add("Colorado Avalanche");
        teamNames.add("Columbus Blue Jackets");
        teamNames.add("Dallas Stars");
        teamNames.add("Detroit Red Wings");
        teamNames.add("Edmonton Oilers");
        teamNames.add("Florida Panthers");
        teamNames.add("Los Angeles Kings");
        teamNames.add("Minnesota Wild");
        teamNames.add("Montreal Canadiens");
        teamNames.add("Nashville Predators");
        teamNames.add("New Jersey Devils");
        teamNames.add("New York Islanders");
        teamNames.add("New York Rangers");
        teamNames.add("Ottawa Senators");
        teamNames.add("Philadelphia Flyers");
        teamNames.add("Pittsburgh Penguins");
        teamNames.add("San Jose Sharks");
        teamNames.add("St. Louis Blues");
        teamNames.add("Tampa Bay Lightning");
        teamNames.add("Toronto Maple Leafs");
        teamNames.add("Vancouver Canucks");
        teamNames.add("Washington Capitals");
        teamNames.add("Winnipeg Jets");
    }

    public int getIndexOfObject(String name){
        return teamNames.indexOf(name);
    }


}
