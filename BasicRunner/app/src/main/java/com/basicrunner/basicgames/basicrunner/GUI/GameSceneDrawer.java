package com.basicrunner.basicgames.basicrunner.GUI;

import android.graphics.Canvas;

import com.basicrunner.basicgames.basicrunner.Models.Interface.IGameScene;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IMovableObject;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;

public class GameSceneDrawer
{
    private final static int tileSize = 10;
    public static void draw(Canvas canvas, IGameScene gameScene)
    {
        // TODO: Draw!
        final IMovableObject player = gameScene.getPlayer();
        final IPoint position = player.getPosition();
        final IPoint size = player.getSize();
        canvas.drawRect((int) position.X(), (int) position.Y(),
                (int) (position.X() + size.X() * tileSize),
                (int) (position.Y() + size.Y() * tileSize), null);
    }
}
