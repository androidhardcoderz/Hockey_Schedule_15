package com.hockeyschedule15.app;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Scott on 10/21/2015.
 */
public class TeamFetchTask {

    Context context;

    public TeamFetchTask(Context context){
        this.context = context;
    }

    public ArrayList<Game> getGames(String name) throws IOException {
        return parseResult(convertInputStreamToString(name));
    }

    private String convertInputStreamToString(String name)
            throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(context.getAssets().open("team_files/"+ name + ".txt")));
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        return result;
    }

    private ArrayList<Game> parseResult(String result) {

        ArrayList<Game> games = new ArrayList<>();

        TeamFonts.readFileContents(context);

        Log.i("TAG",result);

        try {
            // create main JSON object from stream and convert to array
            JSONArray gamesLists = new JSONObject(result).getJSONArray("games");
            Log.i("TAG",gamesLists.length() + "");
            for (int g = 0; g < gamesLists.length(); g++) {
                //loop through each array index
                Game game = new Game();
                JSONObject gameObject = gamesLists.getJSONObject(g);
                game.setScheduled(gameObject.getString("scheduled"));

                if(new FormatGameStartTime().isGameUpcoming(game.getScheduled()) == -1){
                    continue;
                }

                game.setId(gameObject.getString("id"));
                game.setTime(new FormatGameStartTime().getTimeOfGame(game.getScheduled()));
                game.setDate(new FormatGameStartTime().getDateOfGame(game.getScheduled()));
                game.sethTeam(gameObject.getString("home"));
                game.setvTeam(gameObject.getString("away"));

                Log.i("TAG", game.getvTeam() + " " + game.gethTeam());


                game.setAwayColor(ContextCompat.getColor(context, new TeamColors(context).getColors().get(new TeamNames().getIndexOfObject(game.getvTeam()))));
                game.setHomeColor(ContextCompat.getColor(context, new TeamColors(context).getColors().get(new TeamNames().getIndexOfObject(game.gethTeam()))));
                game.setAwayIndex(new TeamNames().getIndexOfObject(game.getvTeam()));
                game.setHomeIndex(new TeamNames().getIndexOfObject(game.gethTeam()));

                games.add(game);
            }
        } catch (JSONException jse) {
            jse.printStackTrace();
        }

        return games;
    }


}
