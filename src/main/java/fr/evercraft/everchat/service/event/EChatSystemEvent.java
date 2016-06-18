/**
 * This file is part of EverChat.
 *
 * EverChat is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EverChat is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EverChat.  If not, see <http://www.gnu.org/licenses/>.
 */
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