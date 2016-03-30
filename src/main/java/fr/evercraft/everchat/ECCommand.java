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

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.ECommand;

public class ECCommand extends ECommand<EverChat> {

	public ECCommand(final EverChat plugin) {
		super(plugin, "everchat");
    }
	
	@Override
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(this.plugin.getPermissions().get("EVERCHAT"));
	}
	
	@Override
	public Text description(final CommandSource source) {
		return this.plugin.getMessages().getText("DESCRIPTION");
	}

	@Override
	public Text help(final CommandSource source) {
		if(source.hasPermission(this.plugin.getPermissions().get("HELP"))){
			return Text.builder("/" + this.getName() + " reload")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " reload"))
					.color(TextColors.RED)
					.build();
		}
		return Text.builder("/" + this.getName())
				.color(TextColors.RED)
				.build();
	}

	@Override
	public List<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		List<String> suggests = new ArrayList<String>();
		if(args.size() == 1) {
			if(source.hasPermission(this.plugin.getPermissions().get("RELOAD"))) {
				suggests.add("reload");
			}
		}
		return suggests;
	}
	
	public boolean execute(final CommandSource source, final List<String> args) throws CommandException {
		boolean resultat = false;
		if(args.size() == 1) {
			if(args.get(0).equalsIgnoreCase("reload")) {
				if(source.hasPermission(this.plugin.getPermissions().get("RELOAD"))) {
					resultat = commandReload(source);
				} else {
					source.sendMessage(this.plugin.getPermissions().noPermission());
				}
			} else {
				source.sendMessage(help(source));
			}
		}
		return resultat;
	}

	private boolean commandReload(CommandSource player) {
		this.plugin.reload();
		player.sendMessage(EChat.of(this.plugin.getMessages().getMessage("PREFIX") + this.plugin.getEverAPI().getMessages().getMessage("RELOAD_COMMAND")));
		return true;
	}
}
