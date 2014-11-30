package com.basicrunner.basicgames.basicrunner.GUI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.basicrunner.basicgames.basicrunner.Models.Interface.IGameScene;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IMovableObject;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;
import com.basicrunner.basicgames.basicrunner.Models.Obstacle;
import com.basicrunner.basicgames.basicrunner.Models.Player;

import java.util.ArrayList;
import java.util.List;

public class GameSceneDrawer
{
    private static String TAG = "Game Scene Drawer";

    public static void draw(Canvas canvas, IGameScene gameScene)
    {
        // TODO: Draw!
        final Player player = (Player)gameScene.getPlayer();
        if (!player.isAlive()) {
            drawDead(canvas);
            return;
        }
        drawPlayer(canvas, player);

        List<? extends IMovableObject> obstacles = gameScene.getObstacles();
        drawObstacles(canvas, obstacles);
    }

    public static void drawPlayer(Canvas canvas, IMovableObject player)
    {
        final IPoint position = player.getPosition();
        final IPoint size = player.getSize();

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3);

        canvas.drawRect((int) position.X(), (int) position.Y(),
                (int) (position.X() + size.X()),
                (int) (position.Y() + size.Y()), paint);
    }

    public static void drawObstacles(Canvas canvas,  List<? extends IMovableObject> obstacles)
    {
       for (IMovableObject obstacle : obstacles)
           drawObstacle(canvas, obstacle);
    }

    public static void drawObstacle(Canvas canvas, IMovableObject obstacle)
    {
        final IPoint position = obstacle.getPosition();
        final IPoint size = obstacle.getSize();

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(3);

        canvas.drawRect((int) position.X(), (int) position.Y(),
                (int) (position.X() + size.X()),
                (int) (position.Y() + size.Y()), paint);
    }

    public static void drawDead(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(30);

        canvas.drawText("DEAD", 100, 100, paint);
    }
}
