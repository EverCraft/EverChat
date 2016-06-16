package fr.evercraft.everchat.service.event;

import org.spongepowered.api.event.cause.Cause;

public class EReloadChatSystemEvent extends EChatSystemEvent {
	
    public EReloadChatSystemEvent(final Cause cause) {
    	super(cause, Action.RELOADED);
    }
}
