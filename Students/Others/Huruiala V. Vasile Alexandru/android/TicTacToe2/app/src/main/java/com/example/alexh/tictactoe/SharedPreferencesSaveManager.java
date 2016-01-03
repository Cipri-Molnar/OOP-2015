package com.example.alexh.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alexh on 20.11.2015.
 */
public class SharedPreferencesSaveManager implements GameStateSaveManager {
    private Activity activity;
    private GameBoard gameBoard;

    public SharedPreferencesSaveManager(Activity activity, GameBoard gameBoard) {
        this.activity = activity;
        this.gameBoard = gameBoard;
    }

    @Override
    public void save() {
        SharedPreferences sharedPreferences = this.activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(this.activity.getString(R.string.button_data_key), this.gameBoard.gridButtonPanel.toString());
        editor.putString(this.activity.getString(R.string.score_data_key), this.gameBoard.score.toString());
        editor.putInt(this.activity.getString(R.string.turn_data_key), this.gameBoard.getTurn());
        // I used int because there is a third state, an undefined state (when the user starts the app the first time)
        int aiStatus = 0;
        if (this.gameBoard.ai.isAiOn()) {
            aiStatus = 1;
        }
        editor.putInt(this.activity.getString(R.string.ai_data_key), aiStatus);

        editor.apply();
    }

    @Override
    public void load() {
        try {
            SharedPreferences sharedPreferences = this.activity.getPreferences(Context.MODE_PRIVATE);

            // Button loading
            String buttonData = sharedPreferences.getString(this.activity.getString(R.string.button_data_key), this.activity.getString(R.string.default_button_values));
            this.gameBoard.gridButtonPanel.setValue(buttonData);

            // Score loading
            String scoreData = sharedPreferences.getString(this.activity.getString(R.string.score_data_key), this.activity.getString(R.string.default_score_value));
            String[] scoreValues = scoreData.split(" - ");
            this.gameBoard.score.set(Integer.parseInt(scoreValues[0]), Integer.parseInt(scoreValues[1]), Integer.parseInt(scoreValues[2]));

            // Turn loading
            int turnData = sharedPreferences.getInt(this.activity.getString(R.string.turn_data_key), 0);
            this.gameBoard.setTurn(turnData);

            // AI loading
            boolean aiData;
            int aiStatus = sharedPreferences.getInt(this.activity.getString(R.string.ai_data_key), -1);
            if (aiStatus == -1) {
                aiData = this.gameBoard.ai.aiNewGameAlertDialog();
            } else if (aiStatus == 0) {
                aiData = false;
            } else if (aiStatus == 1) {
                aiData = true;
            } else {
                throw new Exception("Failed to load preferences. Please reinstall windows.");
            }
            this.gameBoard.ai.setAiOn(aiData);
        } catch (Exception e) {
            this.gameBoard.ai.aiNewGameAlertDialog();
        }
    }
}
