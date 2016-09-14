package com.cs175.ed.memorygame;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private long mLastClick = 0;
    private int score = 0;
    private int turn = 1;
    private ImageAdapter adapter;
    private View previousView;
    private ArrayList<Integer> clearedList;
    private boolean gameOver = false;

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences settings = getSharedPreferences("pref", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("score", score);
        String positions = "";
        if (!gameOver) {
            for (int i : adapter.getIDs()) {
                positions += i + " ";
            }
        }
        positions.trim();
        editor.putString("positions", positions);
        String cleared = "";
        for (int i : clearedList) {
            cleared += i + " ";
        }
        cleared.trim();
        editor.putString("cleared", cleared);
        if (previousView != null) {
            editor.putString("previousTag", previousView.getTag().toString());
            editor.putInt("previousId", previousView.getId());
        } else {
            editor.putString("previousTag", "");
            editor.putInt("previousId", -1);
        }
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clearedList = new ArrayList<Integer>();
        SharedPreferences settings = getSharedPreferences("pref", 0);
        String savedPositions = settings.getString("positions", "-1");
        String savedPreviousTag = settings.getString("previousTag", "-1");
        int savedPreviousId = settings.getInt("previousId", -1);
        String savedClears = settings.getString("cleared", "-1");
        int savedScore = settings.getInt("score", -1);
        setContentView(R.layout.activity_game);
        if (savedPositions.equals("")) {
            startGame();
        } else {
            score = savedScore;
            updateScore();
            if (savedPreviousId > 0) {
                ImageView savedPrev = new ImageView(this);
                savedPrev.setImageResource(savedPreviousId);
                savedPrev.setTag(savedPreviousTag);
                previousView = savedPrev;
            }
            List<String> items = Arrays.asList(savedClears.split(" "));
            for (String item : items) {
                try {
                    clearedList.add(Integer.parseInt(item));
                } catch (Exception e) {
                }
            }
            resumeGame(savedPositions);
        }
    }

    private void resumeGame(String positions) {
        List<String> items = Arrays.asList(positions.split(" "));
        ArrayList<Integer> positionsList = new ArrayList<Integer>();
        for (String item : items) {
            positionsList.add(Integer.parseInt(item));
        }
        GridView gridview = (GridView) findViewById(R.id.gridview);
        adapter = new ImageAdapter(this, positionsList, clearedList);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (SystemClock.elapsedRealtime() - mLastClick < 350) {
                    return;
                }
                mLastClick = SystemClock.elapsedRealtime();
                if (turn == 2) {
                    revealTile(v);
                    checkMove(v);
                    turn = 1;
                } else {
                    revealTile(v);
                    previousView = v;
                    turn++;
                }
            }
        });
    }

    private void startGame() {
        GridView gridview = (GridView) findViewById(R.id.gridview);
        adapter = new ImageAdapter(this);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (turn == 2) {
                    revealTile(v);
                    checkMove(v);
                    turn = 1;
                } else {
                    revealTile(v);
                    previousView = v;
                    turn++;
                }
            }
        });
    }

    private void checkMove(final View v) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!previousView.getTag().equals(v.getTag().toString())) {
                    if (previousView.getTag().toString().replace("1", "").equals(v.getTag().toString().replace("1", ""))) {
                        Toast.makeText(GameActivity.this, "Nice!",
                                Toast.LENGTH_SHORT).show();
                        ImageView img = (ImageView) v;
                        img.setImageResource(R.drawable.checked);
                        img.setOnClickListener(null);
                        ImageView img2 = (ImageView) previousView;
                        img2.setImageResource(R.drawable.checked);
                        img2.setOnClickListener(null);
                        clearedList.add(previousView.getId());
                        clearedList.add(v.getId());
                        score++;
                        updateScore();
                        checkScore();
                    } else {
                        resetMarks(v);
                    }
                } else {
                    resetMarks(v);
                }
            }
        }, 200);
    }

    private void resetMarks(final View v) {
        Toast.makeText(GameActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView img = (ImageView) v;
                img.setImageResource(R.drawable.question);
                ImageView img2 = (ImageView) previousView;
                img2.setImageResource(R.drawable.question);
            }
        }, 200);
    }

    private void updateScore() {
        TextView fortuneText = (TextView) findViewById(R.id.pointField);
        fortuneText.setText(score + "");
    }

    private void checkScore() {
        if (score >= adapter.getIDs().size() / 2) {
            Toast.makeText(GameActivity.this, "Congrats! Play again by going back!", Toast.LENGTH_LONG).show();
            gameOver = true;
        }
    }

    private void revealTile(View v) {
        ImageView img = (ImageView) v;
        Drawable d = ((ImageView) v).getDrawable();
        int id = this.getResources().getIdentifier(v.getTag().toString(), "drawable", this.getPackageName());
        img.setImageResource(id);
    }
}