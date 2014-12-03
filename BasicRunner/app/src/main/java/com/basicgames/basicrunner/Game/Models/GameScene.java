package com.basicgames.basicrunner.Game.Models;

import android.graphics.RectF;
import android.view.MotionEvent;

import com.basicgames.basicrunner.Game.Interface.IGameScene;
import com.basicgames.basicrunner.Game.Interface.IMovableObject;
import com.basicgames.basicrunner.Game.Interface.IPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScene implements IGameScene
{
    private final String TAG = getClass().getName();

    public static final Point _size = new Point(10, 20);
    private final int MAX_OBSTACLES = 30;
    private final float OBSTACLE_PROB = 0.1f;
    public final static Random random = new Random();

    private List<Obstacle> _obstacles;
    private final Player _player;

    private IPoint _touchedPoint;

    public GameScene()
    {
        _player = new Player();
        _obstacles = new ArrayList<Obstacle>();
        _touchedPoint = null;
    }

    public void init()
    {
        _player.init(new Point(5, 2));
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
        final RectF playerBox = getRect(_player);
        // Update obstacles and check collisions.
        for (int i = 0; i < _obstacles.size(); i++)
        {
            Obstacle obstacle = _obstacles.get(i);
            if (obstacle.getPosition().Y() < 0)
            {
                _obstacles.remove(obstacle);
                i--;
                continue;
            }
            final RectF obstacleBox = getRect(obstacle);
            if (collide(playerBox, obstacleBox))
            {
                _player.onCollision(obstacle);
                break;
            }
            obstacle.update();
        }
        // Add new obstacles if needed.
        if (_obstacles.size() < MAX_OBSTACLES && random.nextFloat() < OBSTACLE_PROB)
        {
            final Point position = new Point(random.nextInt((int) _size.x), 20);
            _obstacles.add(new Obstacle(Obstacle.Type.Wall, position));
        }
        // TODO: Check player death and define behavior.
    }

    private boolean collide(RectF object1, RectF object2)
    {
        return object1.intersect(object2);
    }

    private RectF getRect(IMovableObject movableObject)
    {
        final float posX = movableObject.getPosition().X();
        final float posY = movableObject.getPosition().Y();
        final float sizeX = movableObject.getSize().X();
        final float sizeY = movableObject.getSize().Y();
        return new RectF(posX, posY, posX + sizeX, posY + sizeY);
    }

    public void touchEvent(IPoint position, int action)
    {
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE)
            _touchedPoint = position;
        else if (action == MotionEvent.ACTION_UP)
            _touchedPoint = null;
    }
}
