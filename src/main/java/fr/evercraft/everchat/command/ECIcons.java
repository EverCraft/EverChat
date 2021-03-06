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
package fr.evercraft.everchat.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.java.UtilsList;
import fr.evercraft.everapi.plugin.command.ECommand;
import fr.evercraft.everchat.ECMessage.ECMessages;
import fr.evercraft.everchat.ECPermissions;
import fr.evercraft.everchat.EverChat;
import fr.evercraft.everchat.icons.UtilsIcons;

public class ECIcons extends ECommand<EverChat> {
	private final static int MAX_CARACTER = 35;
	

	public ECIcons(final EverChat plugin) {
		super(plugin, "icons", "icon");
    }
	
	@Override
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(ECPermissions.ICONS_COMMAND.get());
	}
	
	@Override
	public Text description(final CommandSource source) {
		return ECMessages.ICON_DESCRIPTION.getText();
	}

	@Override
	public Text help(final CommandSource source) {
		return Text.builder("/" + this.getName() + " [" + EAMessages.ARGS_ARGUMENTS.getString() + "]")
				.onClick(TextActions.suggestCommand("/" + this.getName() + " "))
				.color(TextColors.RED)
				.build();
	}

	@Override
	public Collection<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		return Arrays.asList();
	}
	
	@Override
	public CompletableFuture<Boolean> execute(final CommandSource source, final List<String> args) throws CommandException {
		if (args.size() == 0) {
			return this.commandList(source);
		} else {
			return this.commandSearch(source, args);
		}
	}
	
	/*
	 * List
	 */

	private CompletableFuture<Boolean> commandList(CommandSource player) {
		List<Text> lists = new ArrayList<Text>();
		
		Text line = Text.of();
		int cpt = 0;

		// Pour tous les icones
		for (Entry<String, String> icon : (new TreeMap<String, String>(this.plugin.getService().getIcons())).entrySet()) {
			// La ligne est compléte
			if (cpt == MAX_CARACTER) {
				lists.add(line);
				cpt = 0;
				line = Text.of();
			}
			line = line.concat(getButtom(icon.getKey(), icon.getValue()));
			cpt++;
		}
		
		// Il y a des icones dans la ligne temporairé
		if (cpt != 0) {
			lists.add(line);
		}
		
		// Il n'y a aucune ligne
		if (lists.isEmpty()) {
			lists.add(ECMessages.ICON_LIST_EMPTY.getText());
		}
		
		this.plugin.getEverAPI().getManagerService().getEPagination().sendTo(
				ECMessages.ICON_LIST_TITLE.getText().toBuilder()
					.onClick(TextActions.runCommand("/icon"))
					.build(), 
				lists, player);
		return CompletableFuture.completedFuture(true);
	}
	
	/*
	 * Search
	 */
	
	private CompletableFuture<Boolean> commandSearch(final CommandSource player, List<String> args) {
		args = UtilsList.toLowerCase(args);
		List<Text> lists = new ArrayList<Text>();
		
		Text line = Text.of();
		int cpt = 0;

		// Pour tous les icones
		for (Entry<String, String> icon : (new TreeMap<String, String>(this.plugin.getService().getIcons())).entrySet()) {
			// La ligne est compléte
			if (cpt == MAX_CARACTER) {
				lists.add(line);
				cpt = 0;
				line = Text.of();
			}
			
			// L'icone correspond à la recherche
			if (this.contains(icon.getKey().toLowerCase(), args)) {
				line = line.concat(getButtom(icon.getKey(), icon.getValue()));
				cpt++;
			}
		}
		
		// Il y a des icones dans la ligne temporairé
		if (cpt != 0) {
			lists.add(line);
		}
				
		// Il n'y a aucune ligne
		if (lists.isEmpty()) {
			lists.add(ECMessages.ICON_SEARCH_EMPTY.getText());
		}
		
		this.plugin.getEverAPI().getManagerService().getEPagination().sendTo(
				ECMessages.ICON_SEARCH_TITLE.getText().toBuilder()
					.onClick(TextActions.runCommand("/icon " + String.join(" ", args)))
					.build(), 
				lists, player);
		return CompletableFuture.completedFuture(true);
	}
	
	public boolean contains(final String message, final List<String> args) {
		if (!args.isEmpty()) {
			boolean contains = message.contains(args.get(0));
			int cpt = 1;
			while(contains && cpt < args.size()) {
				contains = message.contains(args.get(cpt));
				cpt++;
			}
			return contains;
		}
		return false;
	}
	
	/*
	 * Fonctions
	 */
	
	public Text getButtom(final String name, final String icon){
		String id = getID(icon).toString();
		return Text.builder(icon)
					.onHover(TextActions.showText(ECMessages.ICON_HOVER.getFormat().toText(
							"{id}", id,
							"{icon}", icon,
							"{name}", name)))
					.onClick(TextActions.suggestCommand(icon))
					.onShiftClick(TextActions.insertText(icon))
					.build();
	}
	
	public Integer getID(final String icon){
		char[] caractere = icon.toCharArray();
		if (caractere.length >= 1) {
			return ((int) caractere[0]) - UtilsIcons.CHARACTER;
		}
		return -1;
	}
}
