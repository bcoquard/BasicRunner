package com.basicrunner.basicgames.basicrunner;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;


public class GameActivity extends Activity {
    GameView _gameLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature((Window.FEATURE_NO_TITLE));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _gameLogic = new GameView(this);
        setContentView(_gameLogic);
    }

    @Override
    protected void onPause() {
        _gameLogic.stopGame();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        _gameLogic.stopGame();
        super.onBackPressed();
    }
}