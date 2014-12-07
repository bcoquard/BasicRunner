package com.basicgames.basicrunner.Game;

public class StatisticManager
{
    private int totalTime;
    private long lastTime;
    private int secondsCount;
    private int frameCount;
    private int fps;

    public StatisticManager()
    {
    }

    public void init()
    {
        lastTime = System.currentTimeMillis();
        totalTime = 0;
        secondsCount = 0;
        frameCount = 0;
        fps = 0;
    }

    public int getTotalTime()
    {
        return totalTime;
    }

    public int getFps()
    {
        return fps;
    }

    public int update()
    {
        int timePassed = (int) (System.currentTimeMillis() - lastTime);
        // To solve a small freeze, time passed is reset if it's to large.
        // Arbitrary value. (1 fps <=> timePassed = 1000)
        if (timePassed > 500)
            timePassed = 0;
        lastTime = System.currentTimeMillis();
        totalTime += timePassed;
        frameCount++;
        secondsCount += timePassed;
        if (secondsCount > 1000)
        {
            fps = frameCount;
            frameCount = 0;
            secondsCount = 0;
        }
        return timePassed;
    }
}
