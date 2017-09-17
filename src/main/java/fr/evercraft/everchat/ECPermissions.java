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

import fr.evercraft.everapi.plugin.EnumPermission;
import fr.evercraft.everapi.plugin.file.EnumMessage;
import fr.evercraft.everchat.ECMessage.ECMessages;

public enum ECPermissions implements EnumPermission {
	EVERCHAT("commands.execute", ECMessages.PERMISSIONS_COMMANDS_EXECUTE),
	HELP("commands.help", ECMessages.PERMISSIONS_COMMANDS_HELP),
	RELOAD("commands.reload", ECMessages.PERMISSIONS_COMMANDS_RELOAD),
	
	CLEAR("commands.clear.execute", ECMessages.PERMISSIONS_COMMANDS_CLEAR_EXECUTE),
	CLEAR_OTHERS("commands.clear.others", ECMessages.PERMISSIONS_COMMANDS_CLEAR_OTHERS),
	
	ICONS_COMMAND("commands.icons.execute", ECMessages.PERMISSIONS_COMMANDS_ICONS_EXECUTE, true),
	
	COLOR("replaces.color", ECMessages.PERMISSIONS_REPLACES_COLOR),
	FORMAT("replaces.format", ECMessages.PERMISSIONS_REPLACES_FORMAT),
	MAGIC("replaces.magic", ECMessages.PERMISSIONS_REPLACES_MAGIC),
	CHARACTER("replaces.character", ECMessages.PERMISSIONS_REPLACES_CHARACTER),
	COMMAND("replaces.command", ECMessages.PERMISSIONS_REPLACES_COMMAND),
	ICONS("replaces.icons", ECMessages.PERMISSIONS_REPLACES_ICONS),
	URL("replaces.url", ECMessages.PERMISSIONS_REPLACES_URL);
	
	private static final String PREFIX = "everchat";
	
	private final String permission;
	private final EnumMessage message;
	private final boolean value;
    
    private ECPermissions(final String permission, final EnumMessage message) {
    	this(permission, message, false);
    }
    
    private ECPermissions(final String permission, final EnumMessage message, final boolean value) {   	    	
    	this.permission = PREFIX + "." + permission;
    	this.message = message;
    	this.value = value;
    }

    @Override
    public String get() {
    	return this.permission;
	}

	@Override
	public boolean getDefault() {
		return this.value;
	}

	@Override
	public EnumMessage getMessage() {
		return this.message;
	}
}
