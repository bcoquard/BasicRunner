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
    private final String TAG = getClass().getSimpleName();

    private final IGameScene _gameScene;
    private IPoint _screenSize;
    private float _tileSize;

    // Paints.
    private final Paint _playerPaint;
    private final Paint _obstaclePaint;
    private final Paint _textPaint;
    private final Paint _debugPaint;

    public GameDrawer(IGameScene gameScene) {
        _gameScene = gameScene;
        _screenSize = new Point();
        _playerPaint = new Paint();
        _playerPaint.setColor(Color.WHITE);
        _obstaclePaint = new Paint();
        _obstaclePaint.setColor(Color.BLUE);
        _textPaint = new Paint();
        _textPaint.setColor(Color.RED);
        _debugPaint = new Paint();
        _debugPaint.setColor(Color.BLACK);
        _debugPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void init(IPoint screenSize) {
        _screenSize = screenSize;
        _tileSize = _screenSize.X() / _gameScene.getSize().X();
        _playerPaint.setStrokeWidth(_tileSize / 10);
        _obstaclePaint.setStrokeWidth(_tileSize / 10);
        _textPaint.setStrokeWidth(_tileSize);
        _debugPaint.setTextSize(_tileSize / 4);
        _debugPaint.setStrokeWidth(_tileSize / 20);
    }

    public boolean initialized() {
        return (_screenSize != null && _tileSize > 0);
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
        canvas.drawRect(pos.X(), pos.Y(), pos.X() + size.X(), pos.Y() + size.Y(), _obstaclePaint);
    }

    private void drawDead(Canvas canvas) {
        canvas.drawText("DEAD", 100, 100, _textPaint);
    }

    public void drawFPS(Canvas canvas, int fps) {
        canvas.drawText("FPS=" + fps, _screenSize.X() / 2, _tileSize / 3, _debugPaint);
    }
}
