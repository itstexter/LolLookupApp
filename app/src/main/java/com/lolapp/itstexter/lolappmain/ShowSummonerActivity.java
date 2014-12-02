package com.lolapp.itstexter.lolappmain;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lolapp.itstexter.lolappmain.Adapters.MatchHistoryAdapter;
import com.lolapp.itstexter.lolappmain.ApiTasks.PlayerHistoryTask;

import java.util.ArrayList;
import java.util.List;

import dto.MatchHistory.MatchSummary;
import dto.MatchHistory.Participant;
import dto.MatchHistory.ParticipantIdentity;
import dto.MatchHistory.PlayerHistory;

/**
 * Created by ItsTexter on 11/30/2014.
 */
public class ShowSummonerActivity extends ListActivity implements PlayerHistoryTask.MatchSummaryCallback {

    private long summonerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_summoner);

        Intent intent = getIntent();
        summonerId = intent.getLongExtra("summonerId", 0);

        TextView v = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.header_match_history, null);
        v.setText(intent.getStringExtra("summonerName"));
        getListView().addHeaderView(v);

        PlayerHistoryTask task = new PlayerHistoryTask(summonerId, this);

        task.execute();
    }

    @Override
    public void playerHistoryReturned(PlayerHistory playerHistory) {

        List<Participant> playerParticipants = new ArrayList<Participant>();

        for (MatchSummary matchSummary : playerHistory.getMatches()) {
            int participantId = -1;

            for (ParticipantIdentity identity : matchSummary.getParticipantIdentities()) {
                if (identity.getPlayer().getSummonerId() == summonerId) {
                    participantId = identity.getParticipantId();
                    break;
                }
            }


            for (Participant participant : matchSummary.getParticipants()) {
                if (participant.getParticipantId() == participantId) {
                    playerParticipants.add(participant);
                }
            }
        }

        MatchHistoryAdapter adapter = new MatchHistoryAdapter(getApplicationContext(), 0);
        adapter.addAll(playerParticipants);
        setListAdapter(adapter);
    }
}
