package com.lolapp.itstexter.lolappmain.ApiTasks;

import android.os.AsyncTask;

import com.lolapp.itstexter.lolappmain.RiotApiHookup;

import dto.MatchHistory.PlayerHistory;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;

/**
 * Created by ItsTexter on 12/1/2014.
 */
public class PlayerHistoryTask extends AsyncTask<Void, Void, PlayerHistory> {

    private MatchSummaryCallback callback;
    private long summonerId;

    public interface MatchSummaryCallback {
        void playerHistoryReturned(PlayerHistory playerHistory);
    }

    public PlayerHistoryTask(long summonerId, MatchSummaryCallback callback) {
        this.summonerId = summonerId;
        this.callback = callback;
    }

    @Override
    protected PlayerHistory doInBackground(Void... params) {
        PlayerHistory playerHistory = null;

        try {
            playerHistory = RiotApiHookup.RIOT_API.getMatchHistory(summonerId);
        } catch (RiotApiException e) {
            e.printStackTrace();
        }

        if (playerHistory == null) {
            return null;
        }

        return playerHistory;
    }

    @Override
    protected void onPostExecute(PlayerHistory playerHistory) {
        super.onPostExecute(playerHistory);

        callback.playerHistoryReturned(playerHistory);
    }
}
