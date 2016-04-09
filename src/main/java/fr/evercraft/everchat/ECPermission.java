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
package fr.evercraft.everchat;

import fr.evercraft.everapi.plugin.EPermission;
import fr.evercraft.everapi.plugin.EPlugin;

public class ECPermission extends EPermission {

	public ECPermission(final EPlugin plugin) {
		super(plugin);
	}

	@Override
	protected void load() {
		add("EVERCHAT", "command");
		
		add("HELP", "help");
		add("RELOAD", "reload");
		
		add("COLOR_CHAT", "color.chat");
		add("COLOR_SIGN", "color.sign");
		
		add("ICON_COMMAND", "icon.command");
	}
}
