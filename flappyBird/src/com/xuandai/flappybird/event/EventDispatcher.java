package com.xuandai.flappybird.event;

import java.util.*;
public class EventDispatcher {
    private final List<GameListener> listeners = new ArrayList<GameListener>();
    public void add(GameListener l){ listeners.add(l); }
    public void dispatch(GameEvent e){ for(GameListener l: listeners) l.onEvent(e); }
}
