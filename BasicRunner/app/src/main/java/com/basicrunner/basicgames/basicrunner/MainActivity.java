package com.basicrunner.basicgames.basicrunner;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.basicrunner.basicgames.basicrunner.Game.GameView;
import com.basicrunner.basicgames.basicrunner.Menu.MenuView;

public class MainActivity extends Activity
{
    private enum CurrentView
    {
        MenuView,
        GameView
    }

    private GameView _gameView;
    private MenuView _menuView;
    private CurrentView _currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature((Window.FEATURE_NO_TITLE));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _currentView = CurrentView.MenuView;
        setView();
    }

    public void startGame()
    {
        if (_currentView == CurrentView.GameView)
            return;
        _currentView = CurrentView.GameView;
        setView();
    }

    public void endGame()
    {
        if (_currentView == CurrentView.MenuView)
            return;
        _currentView = CurrentView.MenuView;
        setView();
    }

    public void setView()
    {
        // TODO: Find a way to initialize objects without recreating them.
        if (_currentView == CurrentView.MenuView)
        {
            _menuView = new MenuView(MainActivity.this);
            setContentView(_menuView);
        }
        else
        {
            _gameView = new GameView(MainActivity.this);
            setContentView(_gameView);
        }
    }

    @Override
    protected void onPause()
    {
        if (_currentView == CurrentView.GameView)
            _gameView.stopGame();
        super.onPause();
    }

    @Override
    public void onBackPressed()
    {
        if (_currentView == CurrentView.GameView)
            endGame();
        else
            super.onBackPressed();
    }
}
