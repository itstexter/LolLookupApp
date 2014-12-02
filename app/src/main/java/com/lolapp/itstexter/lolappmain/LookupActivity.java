package com.lolapp.itstexter.lolappmain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import constant.Region;
import dto.Summoner.Summoner;
import main.java.riotapi.RiotApi;
import main.java.riotapi.RiotApiException;

import com.lolapp.itstexter.lolappmain.Interfaces.LookupCallback;

import java.util.Map;


public class LookupActivity extends Activity implements LookupCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText input = (EditText) findViewById(R.id.input);

        findViewById(R.id.button_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String summonerName = input.getText().toString();

                LookupSummonerTask task = new LookupSummonerTask(summonerName, LookupActivity.this);

                task.execute();

            }
        });


    }

    public class LookupSummonerTask extends AsyncTask<Void, Void, Summoner> {

        private String summonerName;
        public LookupCallback lookupCallback;

        private LookupSummonerTask(String summonerName, LookupCallback lookupCallback) {
            this.summonerName = summonerName;
            this.lookupCallback = lookupCallback;
        }

        @Override
        protected Summoner doInBackground(Void... params) {
            Map<String, Summoner> summonerMap = null;

            try {
                summonerMap = RiotApiHookup.RIOT_API.getSummonerByName(Region.NA, summonerName);
            } catch (RiotApiException e) {
                e.printStackTrace();
            }

            if (summonerMap == null) {
                return null;
            }

            return summonerMap.get(summonerName);
        }

        @Override
        protected void onPostExecute(Summoner summoner) {
            super.onPostExecute(summoner);

            lookupCallback.lookupReturned(summoner);
        }
    }

    @Override
    public void lookupReturned(Summoner summoner) {
        if (summoner == null) {
            Toast.makeText(getApplicationContext(), "Sorry, no name found", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(LookupActivity.this, ShowSummonerActivity.class);
            intent.putExtra("summonerId", summoner.getId());
            intent.putExtra("summonerName", summoner.getName());
            LookupActivity.this.startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
