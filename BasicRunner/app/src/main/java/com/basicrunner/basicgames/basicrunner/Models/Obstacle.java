package com.basicrunner.basicgames.basicrunner.Models;

import com.basicrunner.basicgames.basicrunner.Models.Interface.IMovableObject;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;

public class Obstacle implements IMovableObject
{
    private final String TAG = getClass().getSimpleName();

    private Type _type;
    private Point _position;
    private boolean _alive;
    private float _velocity;

    public Obstacle(Type type, Point position)
    {
        _type = type;
        _position = position;
        _velocity = 0.2f;
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

    public void update()
    {
        _position.y -= _velocity;
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
