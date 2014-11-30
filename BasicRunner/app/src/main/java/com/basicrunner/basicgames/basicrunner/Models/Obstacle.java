package com.basicrunner.basicgames.basicrunner.Models;

import com.basicrunner.basicgames.basicrunner.Models.Interface.IMovableObject;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;

public class Obstacle implements IMovableObject
{
    @Override
    public IPoint getPosition()
    {
        return null;
    }

    @Override
    public IPoint getSize()
    {
        return null;
    }

    public void update(int timePassed)
    {
        // TODO: move obstacle.
    }
}
