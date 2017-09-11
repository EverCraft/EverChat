/*
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
package fr.evercraft.everchat;

import org.spongepowered.api.command.CommandSource;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.plugin.EnumPermission;

public enum ECPermissions implements EnumPermission {
	EVERCHAT("commands.execute"),
	HELP("commands.help"),
	RELOAD("commands.reload"),
	
	CLEAR("commands.clear.execute"),
	CLEAR_OTHERS("commands.clear.others"),
	
	ICONS_COMMAND("commands.icons.execute"),
	
	COLOR("replaces.color"),
	FORMAT("replaces.format"),
	MAGIC("replaces.magic"),
	CHARACTER("replaces.character"),
	COMMAND("replaces.command"),
	ICONS("replaces.icons"),
	URL("replaces.url");
	
	private final static String prefix = "everchat";
	
	private final String permission;
    
    private ECPermissions(final String permission) {   	
    	Preconditions.checkNotNull(permission, "La permission '" + this.name() + "' n'est pas définit");
    	
    	this.permission = permission;
    }

    public String get() {
		return ECPermissions.prefix + "." + this.permission;
	}
    
    public boolean has(CommandSource player) {
    	return player.hasPermission(this.get());
    }
}
