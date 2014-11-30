package com.basicrunner.basicgames.basicrunner.Models;

import com.basicrunner.basicgames.basicrunner.Models.Interface.IGameScene;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IMovableObject;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;

import java.util.ArrayList;
import java.util.List;

public class GameScene implements IGameScene
{
    private final Point _size = new Point(10, 20);
    private final List<Obstacle> _obstacles;
    private final Player _player;

    public GameScene()
    {
        _obstacles = new ArrayList<Obstacle>();
        _player = new Player(new Point());
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

    public void update(int timePassed)
    {
        _player.update(timePassed);
        for (Obstacle obstacle : _obstacles)
        {
            obstacle.update(timePassed);
        }

    }

    public void handleTouchEvent(float x, float y)
    {
        // TODO: handle?
    }
}
