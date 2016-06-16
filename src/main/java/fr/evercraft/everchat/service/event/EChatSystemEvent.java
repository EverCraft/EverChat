package fr.evercraft.everchat.service.event;

import org.spongepowered.api.event.cause.Cause;

import fr.evercraft.everapi.event.ChatSystemEvent;

public class EChatSystemEvent implements ChatSystemEvent {

    private final Cause cause;
    private final Action action;

    public EChatSystemEvent(final Cause cause, final Action action) {
    	this.cause = cause;
        this.action = action;
    }
    
    public Action getAction() {
        return this.action;
    }

	@Override
	public Cause getCause() {
		return this.cause;
	}
}
