package com.basicrunner.basicgames.basicrunner.Models;

import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.basicrunner.basicgames.basicrunner.Models.Interface.IGameScene;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IMovableObject;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class GameScene implements IGameScene {
    private final String TAG = "GAME SCENE";

    private final Point _size = new Point(10, 20);
    private final List<Obstacle> _obstacles;
    private final Player _player;

    public GameScene() {
        _player = new Player(new Point(100, 50));

        _obstacles = new ArrayList<Obstacle>();
        _obstacles.add(new Obstacle(new Point(50, 50)));
    }

    @Override
    public IPoint getSize() {
        return _size;
    }

    @Override
    public List<? extends IMovableObject> getObstacles() {
        return _obstacles;
    }

    @Override
    public IMovableObject getPlayer() {
        return _player;
    }

    public boolean isInCollision(RectF object1, RectF object2)
    {

        if (object1.intersect(object2))
            return true;
        return false;
    }

    public void update(int timePassed) {
        _player.update(timePassed);

        for (Obstacle obstacle : _obstacles) {
            if (isInCollision(_player.getBox(), obstacle.getBox()))
                _player.kill();
            else
                obstacle.update(timePassed);
        }

        Log.d(TAG, "Alive " + _player.isAlive());

    }

    public boolean handleTouchEvent(MotionEvent event) {
        _player.handleTouchEvent(event);

        return true;
    }
}
