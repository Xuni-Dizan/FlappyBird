package com.xuandai.flappybird.event;

public class GameEvent {
    public static final int COLLISION = 1;
    public static final int SCORE     = 2;
    public final int type;
    public final int value;
    public GameEvent(int type, int value) { this.type = type; this.value = value; }
}
