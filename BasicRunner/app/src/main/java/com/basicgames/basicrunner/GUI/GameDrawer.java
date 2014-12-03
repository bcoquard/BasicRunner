package com.basicgames.basicrunner.GUI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.basicgames.basicrunner.Game.Interface.IGameScene;
import com.basicgames.basicrunner.Game.Interface.IMovableObject;
import com.basicgames.basicrunner.Game.Interface.IPoint;
import com.basicgames.basicrunner.Game.Models.Player;
import com.basicgames.basicrunner.Game.Models.Point;

public class GameDrawer {
    // Game object paints.
    private static Paint _playerPaint;
    private static Paint _obstaclePaint;
    private static Paint _textPaint;
    private final String TAG = getClass().getSimpleName();
    private final IGameScene _gameScene;
    private IPoint _screenSize;
    private float _tileSize;

    public GameDrawer(IGameScene gameScene) {
        _gameScene = gameScene;
        _screenSize = new Point();
        _playerPaint = new Paint();
        _playerPaint.setColor(Color.WHITE);
        _obstaclePaint = new Paint();
        _obstaclePaint.setColor(Color.BLUE);
        _textPaint = new Paint();
        _textPaint.setColor(Color.RED);
    }

    public void init(IPoint screenSize) {
        _screenSize = screenSize;
        _tileSize = screenSize.X() / _gameScene.getSize().X();
        _playerPaint.setStrokeWidth(_tileSize / 10);
        _obstaclePaint.setStrokeWidth(_tileSize / 10);
        _textPaint.setStrokeWidth(_tileSize);
    }

    public boolean initialized() {
        return (_screenSize != null);
    }

    public void draw(Canvas canvas) {
        // If drawer isn't initialized, the draw effects are unspecified.
        if (!initialized()) throw new InternalError();
        // Start by drawing background.
        canvas.drawColor(Color.GREEN);
        final Player player = (Player) _gameScene.getPlayer();
        if (!player.isAlive()) {
            drawDead(canvas);
            return;
        }
        drawPlayer(canvas, player);
        for (IMovableObject obstacle : _gameScene.getObstacles())
            drawObstacle(canvas, obstacle);
    }

    public Point getScreenPos(IPoint indexPosition) {
        final float x = _tileSize * indexPosition.X();
        final float y = _screenSize.Y() - _tileSize * indexPosition.Y();
        return new Point(x, y);
    }

    public Point getIndexPos(IPoint screenPos) {
        final float x = screenPos.X() / _tileSize;
        final float y = (_screenSize.Y() - screenPos.Y()) / _tileSize;
        return new Point(x, y);
    }

    public Point getScreenSize(IPoint indexSize) {
        final float x = _tileSize * indexSize.X();
        final float y = _tileSize * indexSize.Y();
        return new Point(x, y);
    }

    private void drawPlayer(Canvas canvas, IMovableObject player) {
        final IPoint pos = getScreenPos(player.getPosition());
        final IPoint size = getScreenSize(player.getSize());
        canvas.drawRect(pos.X(), pos.Y(), pos.X() + size.X(), pos.Y() + size.Y(), _playerPaint);
    }

    private void drawObstacle(Canvas canvas, IMovableObject obstacle) {
        final IPoint pos = getScreenPos(obstacle.getPosition());
        final IPoint size = getScreenSize(obstacle.getSize());
        Log.d(TAG, obstacle.getPosition().toString() + " -> " + pos.toString() + " : " + _tileSize);
        canvas.drawRect(pos.X(), pos.Y(), pos.X() + size.X(), pos.Y() + size.Y(), _obstaclePaint);
    }

    private void drawDead(Canvas canvas) {
        canvas.drawText("DEAD", 100, 100, _textPaint);
    }

    public void drawFPS(Canvas canvas, String fps) {
        Paint fpsPaint = new Paint();
        fpsPaint.setColor(Color.BLACK);
        fpsPaint.setTextSize(20);
        fpsPaint.setStrokeWidth(2);
        canvas.drawText("FPS; " + fps, 20, 20, fpsPaint);
        Log.d(TAG, "Average FPS:" + fps);
    }
}
