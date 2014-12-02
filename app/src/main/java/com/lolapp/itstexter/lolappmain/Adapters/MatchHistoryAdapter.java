package com.lolapp.itstexter.lolappmain.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lolapp.itstexter.lolappmain.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import dto.MatchHistory.Participant;


/**
 * Created by ItsTexter on 12/1/2014.
 */
public class MatchHistoryAdapter extends ArrayAdapter<Participant> {

    public MatchHistoryAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        ViewHolder viewHolder;

        if (v == null) {

            v = LayoutInflater.from(getContext()).inflate(R.layout.list_item_match_history, null);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        Participant participant = getItem(position);

        viewHolder.championName.setText("Champion Id: " + participant.getChampionId());

        // Kda Stuff
        long kills = participant.getStats().getKills();
        long deaths = participant.getStats().getDeaths();
        long assists = participant.getStats().getAssists();

        viewHolder.kda.setText(kills + " / " + deaths + " / " + assists);
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.HALF_EVEN);
        double kdaRaw = ((double) kills + (double) assists) / (double) deaths;
        String kda = df.format(kdaRaw);
        viewHolder.kdaRatio.setText(kda + " kda");

        if (participant.getStats().isWinner()) {
            viewHolder.container.setBackgroundColor(Color.parseColor("#0088cb"));
        } else {
            viewHolder.container.setBackgroundColor(Color.parseColor("#B22222"));
        }

        return v;
    }

    private static class ViewHolder {
        public RelativeLayout container;
        public TextView championName;
        public TextView kda;
        public TextView kdaRatio;

        public ViewHolder(View view) {
            container = (RelativeLayout) view.findViewById(R.id.container);
            championName = (TextView) view.findViewById(R.id.champion_name);
            kda = (TextView) view.findViewById(R.id.kda);
            kdaRatio = (TextView) view.findViewById(R.id.kda_ratio);
        }
    }
}
