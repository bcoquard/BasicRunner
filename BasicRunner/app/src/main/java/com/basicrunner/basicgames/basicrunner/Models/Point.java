package com.basicrunner.basicgames.basicrunner.Models;

import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;

public class Point implements IPoint
{
    public float x;
    public float y;

    public Point()
    {
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Point(Point src)
    {
        this.x = src.x;
        this.y = src.y;
    }

    @Override
    public float X()
    {
        return x;
    }

    @Override
    public float Y()
    {
        return y;
    }

    public Point setLocation(float x, float y)
    {
        this.x = x;
        this.y = y;
        return this;
    }

    public final Point offset(float dx, float dy)
    {
        x += dx;
        y += dy;
        return this;
    }

    @Override
    public String toString()
    {
        return "Point(" + x + ", " + y + ")";
    }

    public final boolean equals(float x, float y)
    {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        if (y != point.y) return false;

        return true;
    }
}
