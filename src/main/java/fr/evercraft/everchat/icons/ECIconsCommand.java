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
package fr.evercraft.everchat.icons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.java.UtilsList;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.ECommand;
import fr.evercraft.everchat.ECMessage.ECMessages;
import fr.evercraft.everchat.EverChat;
import fr.evercraft.everchat.service.EChatService;

public class ECIconsCommand extends ECommand<EverChat> {
	private final static int MAX_CARACTER = 35;
	

	public ECIconsCommand(final EverChat plugin) {
		super(plugin, "icon", "icons");
    }
	
	@Override
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(this.plugin.getPermissions().get("ICON_COMMAND"));
	}
	
	@Override
	public Text description(final CommandSource source) {
		return ECMessages.ICON_DESCRIPTION.getText();
	}

	@Override
	public Text help(final CommandSource source) {
		return Text.builder("/icon [" + this.plugin.getEverAPI().getMessages().getArg("arguments") + "]")
				.onClick(TextActions.suggestCommand("/icon "))
				.color(TextColors.RED)
				.build();
	}

	@Override
	public List<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		return new ArrayList<String>();
	}
	
	@Override
	public boolean execute(final CommandSource source, final List<String> args) throws CommandException {
		boolean resultat = false;
		if(args.size() == 0) {
			commandList(source);
		} else {
			resultat = commandSearch(source, args);
		}
		return resultat;
	}
	
	/*
	 * List
	 */

	private boolean commandList(CommandSource player) {
		List<Text> lists = new ArrayList<Text>();
		
		Text line = Text.of();
		int cpt = 0;

		// Pour tous les icones
		for(Entry<String, String> icon : (new TreeMap<String, String>(this.plugin.getService().getIcons())).entrySet()) {
			// La ligne est compléte
			if(cpt == MAX_CARACTER) {
				lists.add(line);
				cpt = 0;
				line = Text.of();
			}
			line = line.concat(getButtom(icon.getKey(), icon.getValue()));
			cpt++;
		}
		
		// Il y a des icones dans la ligne temporairé
		if(cpt != 0) {
			lists.add(line);
		}
		
		// Il n'y a aucune ligne
		if(lists.isEmpty()) {
			lists.add(ECMessages.ICON_LIST_EMPTY.getText());
		}
		
		this.plugin.getEverAPI().getManagerService().getEPagination().sendTo(
				ECMessages.ICON_LIST_TITLE.getText().toBuilder()
					.onClick(TextActions.runCommand("/icon"))
					.build(), 
				lists, player);
		return true;
	}
	
	/*
	 * Search
	 */
	
	private boolean commandSearch(final CommandSource player, List<String> args) {
		args = UtilsList.toLowerCase(args);
		List<Text> lists = new ArrayList<Text>();
		
		Text line = Text.of();
		int cpt = 0;

		// Pour tous les icones
		for(Entry<String, String> icon : (new TreeMap<String, String>(this.plugin.getService().getIcons())).entrySet()) {
			// La ligne est compléte
			if(cpt == MAX_CARACTER) {
				lists.add(line);
				cpt = 0;
				line = Text.of();
			}
			
			// L'icone correspond à la recherche
			if(this.contains(icon.getKey().toLowerCase(), args)) {
				line = line.concat(getButtom(icon.getKey(), icon.getValue()));
				cpt++;
			}
		}
		
		// Il y a des icones dans la ligne temporairé
		if(cpt != 0) {
			lists.add(line);
		}
				
		// Il n'y a aucune ligne
		if(lists.isEmpty()) {
			lists.add(ECMessages.ICON_SEARCH_EMPTY.getText());
		}
		
		this.plugin.getEverAPI().getManagerService().getEPagination().sendTo(
				ECMessages.ICON_SEARCH_TITLE.getText().toBuilder()
					.onClick(TextActions.runCommand("/icon " + String.join(" ", args)))
					.build(), 
				lists, player);
		return true;
	}
	
	public boolean contains(final String message, final List<String> args) {
		if(!args.isEmpty()) {
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
					.onHover(TextActions.showText(EChat.of(ECMessages.ICON_HOVER.get()
							.replaceAll("<id>", id)
							.replaceAll("<icon>", icon)
							.replaceAll("<name>", name))))
					.onClick(TextActions.suggestCommand(icon))
					.onShiftClick(TextActions.insertText(icon))
					.build();
	}
	
	public Integer getID(final String icon){
		char[] caractere = icon.toCharArray();
		if(caractere.length >= 1) {
			return ((int) caractere[0]) - EChatService.CHARACTER;
		}
		return -1;
	}
}
