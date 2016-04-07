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
package fr.evercraft.everchat.command;

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
import fr.evercraft.everchat.EverChat;
import fr.evercraft.everchat.service.EChatService;

public class ECIcons extends ECommand<EverChat> {

	public ECIcons(final EverChat plugin) {
		super(plugin, "icon", "icons");
    }
	
	@Override
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(this.plugin.getPermissions().get("ICONS"));
	}
	
	@Override
	public Text description(final CommandSource source) {
		return this.plugin.getMessages().getText("LIST_DESCRIPTION");
	}

	@Override
	public Text help(final CommandSource source) {
		return Text.builder("/icon")
				.onClick(TextActions.suggestCommand("/icon"))
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
			if(args.get(0).equalsIgnoreCase("list")) {
				resultat = commandList(source);
			} else if(args.get(0).equalsIgnoreCase("all")) {
				resultat = commandAll(source);
			} else {
				source.sendMessage(help(source));
			}
		} else if(args.size() >= 2 && args.get(0).equalsIgnoreCase("search")) {
			args.remove(0);
			resultat = commandSearch(source, args);
		} else {
			source.sendMessage(help(source));
		}
		return resultat;
	}

	private boolean commandAll(CommandSource player) {
		List<Text> lists = new ArrayList<Text>();
		
		Text line = Text.of();
		int cpt = 0;

		for(Entry<String, String> icon : (new TreeMap<String, String>(this.plugin.getService().getIcons())).entrySet()) {
			if(cpt == 35) {
				lists.add(line);
				cpt = 0;
				line = Text.of();
			}
			line = line.concat(getButtomAll(icon.getKey(), icon.getValue()));
			cpt++;
		}
		
		if(cpt != 0) {
			lists.add(line);
		}
		
		if(lists.isEmpty()) {
			lists.add(this.plugin.getMessages().getText("ALL_EMPTY"));
		}
		
		this.plugin.getEverAPI().getManagerService().getEPagination().sendTo(
				this.plugin.getMessages().getText("ALL_TITLE").toBuilder()
					.onClick(TextActions.runCommand("/icon all")).build(), 
				lists, player);
		return true;
	}
	
	public Text getButtomAll(final String name, final String icon){
		String id = getID(icon).toString();
		return Text.builder(icon)
					.onHover(TextActions.showText(EChat.of(this.plugin.getMessages().getMessage("LIST_HOVER")
							.replaceAll("<id>", id)
							.replaceAll("<icon>", icon)
							.replaceAll("<name>", name))))
					.onClick(TextActions.suggestCommand(icon))
					.onShiftClick(TextActions.insertText(icon))
					.build();
	}

	private boolean commandList(final CommandSource player) {
		List<Text> lists = new ArrayList<Text>();

		for(Entry<String, String> icon : (new TreeMap<String, String>(this.plugin.getService().getIcons())).entrySet()) {
			lists.add(getButtomList(icon.getKey(), icon.getValue()));
		}
		
		if(lists.isEmpty()) {
			lists.add(this.plugin.getMessages().getText("LIST_EMPTY"));
		}
		
		this.plugin.getEverAPI().getManagerService().getEPagination().sendTo(
				this.plugin.getMessages().getText("LIST_TITLE").toBuilder()
					.onClick(TextActions.runCommand("/icon")).build(), 
				lists, player);
		return true;
	}
	
	public Text getButtomList(final String name, final String icon){
		String id = getID(icon).toString();
		return EChat.of(this.plugin.getMessages().getMessage("LIST_LINE")
						.replaceAll("<id>", id)
						.replaceAll("<icon>", icon)
						.replaceAll("<name>", name))
						.toBuilder()
					.onHover(TextActions.showText(EChat.of(this.plugin.getMessages().getMessage("LIST_HOVER")
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
	
	private boolean commandSearch(final CommandSource player, List<String> args) {
		args = UtilsList.toLowerCase(args);
		List<Text> lists = new ArrayList<Text>();

		for(Entry<String, String> icon : (new TreeMap<String, String>(this.plugin.getService().getIcons())).entrySet()) {
			String name = icon.getKey().toLowerCase();
			boolean contains = name.contains(args.get(0));
			int cpt = 1;
			while(contains && cpt < args.size()) {
				contains = name.contains(args.get(cpt));
				cpt++;
			}
			if(contains) {
				lists.add(getButtomSearch(icon.getKey(), icon.getValue()));
			}
		}
		
		if(lists.isEmpty()) {
			lists.add(this.plugin.getMessages().getText("SEARCH_EMPTY"));
		}
		
		this.plugin.getEverAPI().getManagerService().getEPagination().sendTo(
				this.plugin.getMessages().getText("SEARCH_TITLE").toBuilder()
					.onClick(TextActions.runCommand("/icon " + String.join(" ", args))).build(), 
				lists, player);
		return true;
	}
	
	public Text getButtomSearch(final String name, final String icon){
		String id = getID(icon).toString();
		return EChat.of(this.plugin.getMessages().getMessage("SEARCH_LINE")
						.replaceAll("<id>", id)
						.replaceAll("<icon>", icon)
						.replaceAll("<name>", name))
						.toBuilder()
					.onHover(TextActions.showText(EChat.of(this.plugin.getMessages().getMessage("SEARCH_HOVER")
							.replaceAll("<id>", id)
							.replaceAll("<icon>", icon)
							.replaceAll("<name>", name))))
					.onClick(TextActions.suggestCommand(icon))
					.onShiftClick(TextActions.insertText(icon))
					.build();
	}
}
