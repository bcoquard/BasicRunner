package com.basicgames.basicrunner.Game.Models;

import com.basicgames.basicrunner.Game.Interface.IMovableObject;
import com.basicgames.basicrunner.Game.Interface.IPoint;

public class Obstacle implements IMovableObject
{
    private final String TAG = getClass().getSimpleName();

    private Type _type;
    private Point _position;
    private boolean _alive;
    private float _velocity;

    public Obstacle(Type type)
    {
        _type = type;
        _position = new Point();
        _velocity = 0.005f;
        _alive = false;
    }

    public void init(Point position)
    {
        _position.setLocation(position);
        _alive = true;
    }

    @Override
    public IPoint getPosition()
    {
        return _position;
    }

    @Override
    public IPoint getSize()
    {
        return _type.size;
    }

    public Type getType()
    {
        return _type;
    }

    @Override
    public boolean isAlive()
    {
        return _alive;
    }

    /**
     * Moves the obstacle vertically according to it's velocity.
     * Destroy the obstacle if it's vertical position is under 0;
     *
     * @param timePassed ms since the last update
     */
    public void update(int timePassed)
    {
        _position.y -= _velocity * timePassed;
        if (_position.Y() < 0)
            _alive = false;
    }

    public enum Type
    {
        Wall(new Point(0.5f, 1));

        public final IPoint size;

        Type(IPoint size)
        {
            this.size = size;
        }
    }
}
