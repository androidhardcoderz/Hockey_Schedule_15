package com.hockeyschedule15.app;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.ResultReceiver;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DownloadService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "DownloadService";


    public DownloadService() {
        super(DownloadService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();


            /* Update UI: Download Service is Running */
        receiver.send(STATUS_RUNNING, Bundle.EMPTY);

        try {


            /* Sending result back to activity */

            /*
            String totalResponse;
            totalResponse = convertInputStreamToString();
            TeamNames names = new TeamNames();
            mCreateAndSaveFile(names.getTeamNames().get(17),parseForSpecificTeam(names.getTeamNames().get(17),totalResponse));

            */

            bundle.putParcelable("SCHEDULE", parseResult(convertInputStreamToString()));
            receiver.send(STATUS_FINISHED, bundle);

        } catch (Exception e) {
                /* Sending error message back to activity */
            bundle.putString(Intent.EXTRA_TEXT, e.toString());
            receiver.send(STATUS_ERROR, bundle);
        }

        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }

 /*   private Standings downloadData(String requestUrl) throws IOException,
            DownloadException {

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

		*//* forming th java.net.URL object *//*
        URL url = new URL(requestUrl);
        urlConnection = (HttpURLConnection) url.openConnection();

		*//* optional request header *//*
        urlConnection.setRequestProperty("Content-Type", "application/json");

		*//* optional request header *//*
        urlConnection.setRequestProperty("Accept", "application/json");

		*//* for Get request *//*
        urlConnection.setRequestMethod("GET");
        int statusCode = urlConnection.getResponseCode();

		*//* 200 represents HTTP OK *//*
        if (statusCode == 200) {
            inputStream = new BufferedInputStream(
                    urlConnection.getInputStream());
            String response = convertInputStreamToString(inputStream);
            //System.out.println(response);
            return parseResult(response);
        } else {
            throw new DownloadException("Failed to fetch data!!");
        }
    }*/


    private String convertInputStreamToString()
            throws IOException {

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(getApplicationContext().getAssets().open(getString(R.string.schedule_file))));
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }



        return result;
    }

    private String parseForSpecificTeam(String name,String result) {

        String response = "";
        JSONObject mainObject = new JSONObject();
        JSONArray array = new JSONArray();

        try {
            // create main JSON object from stream and convert to array
            JSONArray gamesLists = new JSONObject(result).getJSONArray("games");
            for (int g = 0; g < gamesLists.length(); g++) {
                //loop through each array index
                Game game = new Game();
                JSONObject gameObject = gamesLists.getJSONObject(g);
                game.setId(gameObject.getString("id"));
                game.setScheduled(gameObject.getString("scheduled"));
                game.sethTeam(gameObject.getJSONObject("home").getString("name"));
                game.setvTeam(gameObject.getJSONObject("away").getString("name"));

                if (game.gethTeam().equals(name) || game.getvTeam().equals(name)) {
                    JSONObject teamObject = new JSONObject();
                    teamObject.put("id",game.getId());
                    teamObject.put("scheduled",game.getScheduled());
                    teamObject.put("home",game.gethTeam());
                    teamObject.put("away",game.getvTeam());

                    array.put(teamObject);
                }

                continue;
            }

            mainObject.putOpt("games", array);


        } catch (JSONException jse) {
            jse.printStackTrace();
        }

        return mainObject.toString();
    }


    //used to build json files for each team from main schedule file
    public void mCreateAndSaveFile(String fileName, String mJsonResponse) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName + ".txt");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(mJsonResponse);
            fileWriter.flush();
            fileWriter.close();
            Log.i(TAG, "file written for: " + fileName + " " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Schedule parseResult(String result) {

        Schedule schedule = new Schedule();

        try {
            // create main JSON object from stream and convert to array
            JSONArray gamesLists = new JSONObject(result).getJSONArray("games");
            for (int g = 0; g < gamesLists.length(); g++) {
                //loop through each array index
                Game game = new Game();
                JSONObject gameObject = gamesLists.getJSONObject(g);

                game.setScheduled(gameObject.getString("scheduled"));

                if(new FormatGameStartTime().isGameToday(game.getScheduled()) == false){
                    continue;
                }


                game.setId(gameObject.getString("id"));
                game.sethTeam(gameObject.getJSONObject("home").getString("name"));
                game.setvTeam(gameObject.getJSONObject("away").getString("name"));
                game.setTime(new FormatGameStartTime().getTimeOfGame(game.getScheduled()));
                game.setDate(new FormatGameStartTime().getDateOfGame(game.getScheduled()));
                game.setAwayColor(ContextCompat.getColor(this, new TeamColors(this).getColors().get(new TeamNames().getIndexOfObject(game.getvTeam()))));
                game.setHomeColor(ContextCompat.getColor(this, new TeamColors(this).getColors().get(new TeamNames().getIndexOfObject(game.gethTeam()))));
                game.setAwayIndex(new TeamNames().getIndexOfObject(game.getvTeam()));
                game.setHomeIndex(new TeamNames().getIndexOfObject(game.gethTeam()));

                Log.i("TAG", game.getDate() + " " + game.getTime());

                schedule.getGames().add(game);
            }
        } catch (JSONException jse) {
            jse.printStackTrace();
        }
        return schedule;
    }

    public String convertInputStreamToString(String filename,String s)
            throws IOException {

        InputStreamReader isr = new InputStreamReader(getApplicationContext().getAssets().open("team_files/" + filename));
        BufferedReader bufferedReader = new BufferedReader(isr);
        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
            Log.i(TAG, line);

        }

		/* Close Stream */
        if (null != isr) {
            isr.close();
        }

        return result;
    }

    public ArrayList<Game> parseSpecificTeamData(String jsonString) throws JSONException {

        ArrayList<Game> games = new ArrayList<>();
        JSONArray gamesArray = new JSONObject(jsonString).getJSONArray("games");


        Log.i(TAG, "STARTING PARSING!");
        Log.i(TAG, gamesArray.length() + " LENGTH OF ARRAY FOR TEAM");

        for (int i = 0; i < gamesArray.length(); i++) {

            JSONObject gameObject = gamesArray.getJSONObject(i);

            if(new FormatGameStartTime().isGameUpcoming(gameObject.getString("scheduled")) == -1){
                continue;
            }

            Game game = new Game();
            game.setId(gameObject.getString("id"));
            game.setScheduled(gameObject.getString("scheduled"));
            game.sethTeam(gameObject.getJSONObject("home").getString("name"));
            game.setvTeam(gameObject.getJSONObject("away").getString("name"));
            games.add(game);
        }

        return games;
    }

    public class DownloadException extends Exception {

        public DownloadException(String message) {
            super(message);
        }

        public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
