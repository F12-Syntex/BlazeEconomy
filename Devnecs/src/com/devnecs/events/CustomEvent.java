package com.devnecs.events;

import org.bukkit.event.Event;

public abstract class CustomEvent extends Event{

    public abstract String name();
    public abstract String description();
	
}
