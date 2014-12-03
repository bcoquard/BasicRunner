package com.basicrunner.basicgames.basicrunner.Game.Interface;

import java.util.List;

public interface IGameScene
{
    public IPoint getSize();
    public List<? extends IMovableObject> getObstacles();
    public IMovableObject getPlayer();
}
