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
    public final static Random random = new Random();
    private final int MAX_OBSTACLES = 20;
    private final float OBSTACLE_PROB = 0.08f;
    private final Player _player;
    private List<Obstacle> _obstacles;
    private IPoint _touchedPoint;

    public GameScene()
    {
        _player = new Player();
        _obstacles = new ArrayList<Obstacle>();
    }

    public void init()
    {
        _player.init(new Point(5, 4));
        _obstacles.clear();
        _touchedPoint = null;
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

    public void touchEvent(IPoint position, int action)
    {
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE)
            _touchedPoint = position;
        else if (action == MotionEvent.ACTION_UP)
            _touchedPoint = null;
    }

    /**
     * @param timePassed ms since the last update
     */
    public void update(int timePassed)
    {
        _player.update(timePassed, _touchedPoint, _size);
        obstaclePhysics(timePassed);
        addObstacle();
    }

    /**
     * Updates the obstacles and checks for collisions.
     * Recycle dead obstacles.
     */
    private void obstaclePhysics(int timePassed)
    {
        final RectF playerBox = getRect(_player);
        for (Obstacle obstacle : _obstacles)
        {
            if (!obstacle.isAlive())
                obstacle.init(new Point(random.nextInt((int) _size.x), 20));
            final RectF obstacleBox = getRect(obstacle);
            if (collide(playerBox, obstacleBox))
            {
                _player.onCollision(obstacle);
                break;
            }
            // Update at the end so the player actually sees the objects intersect.
            obstacle.update(timePassed);
        }
    }

    /**
     * Checks if the two rectangles collide.
     *
     * @return true if the objects collide
     */
    private boolean collide(RectF object1, RectF object2)
    {
        return object1.intersect(object2);
    }

    /**
     * Add new obstacles if needed.
     */
    private void addObstacle()
    {
        if (_obstacles.size() < MAX_OBSTACLES && random.nextFloat() < OBSTACLE_PROB)
        {
            Obstacle obstacle = new Obstacle(Obstacle.Type.Wall);
            obstacle.init(new Point(random.nextInt((int) _size.x), 20));
            _obstacles.add(obstacle);
        }
    }

    private RectF getRect(IMovableObject movableObject)
    {
        final float posX = movableObject.getPosition().X();
        final float posY = movableObject.getPosition().Y();
        final float sizeX = movableObject.getSize().X();
        final float sizeY = movableObject.getSize().Y();
        return new RectF(posX, posY, posX + sizeX, posY + sizeY);
    }
}
