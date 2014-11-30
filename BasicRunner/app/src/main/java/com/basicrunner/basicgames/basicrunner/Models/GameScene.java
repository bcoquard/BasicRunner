package com.basicrunner.basicgames.basicrunner.Models;

import android.graphics.RectF;
import android.view.MotionEvent;

import com.basicrunner.basicgames.basicrunner.Models.Interface.IGameScene;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IMovableObject;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;

import java.util.ArrayList;
import java.util.List;

public class GameScene implements IGameScene
{
    private final String TAG = "GAME SCENE";

    private final Point _size = new Point(10, 20);
    private final List<Obstacle> _obstacles;
    private final Player _player;

    private IPoint _touchedPoint;

    public GameScene()
    {
        _player = new Player();
        _obstacles = new ArrayList<Obstacle>();
        _obstacles.add(new Obstacle(new Point(5, 20)));
        _touchedPoint = null;
    }

    public void init()
    {
        _player.init(new Point(5, 1));
        _obstacles.clear();
    }

    @Override
    public IPoint getSize()
    {
        return _size;
    }

    @Override
    public List<? extends IMovableObject> getObstacles()
    {
        return _obstacles;
    }

    @Override
    public IMovableObject getPlayer()
    {
        return _player;
    }

    public void update()
    {
        _player.update(_touchedPoint);
        for (Obstacle obstacle : _obstacles)
        {
            obstacle.update();
            if (collide(_player.getBox(), obstacle.getBox()))
                _player.kill();
        }
    }

    private boolean collide(RectF object1, RectF object2)
    {
        return object1.intersect(object2);
    }

    public void touchEvent(IPoint position, int action)
    {
        if (action == MotionEvent.ACTION_MOVE)
            _touchedPoint = position;
        else if (action == MotionEvent.ACTION_UP)
            _touchedPoint = null;
    }
}
