package com.basicrunner.basicgames.basicrunner.Models.Interface;

import android.graphics.RectF;

public interface IMovableObject
{
    public IPoint getPosition();
    public RectF getBox();
    public IPoint getSize();
    public boolean isAlive();
}
