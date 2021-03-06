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
import org.spongepowered.api.text.Text;

import fr.evercraft.everapi.plugin.command.EParentCommand;
import fr.evercraft.everchat.ECMessage.ECMessages;

public class ECCommand extends EParentCommand<EverChat> {
	
	public ECCommand(final EverChat plugin) {
        super(plugin, "everchat", "chat");
    }
	
	@Override
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(ECPermissions.EVERCHAT.get());
	}

	@Override
	public Text description(final CommandSource source) {
		return ECMessages.DESCRIPTION.getText();
	}

	@Override
	public boolean testPermissionHelp(final CommandSource source) {
		return source.hasPermission(ECPermissions.HELP.get());
	}
}
