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

import fr.evercraft.everapi.plugin.file.EMessage;

public class ECMessage extends EMessage {

	public ECMessage(final EverChat plugin) {
		super(plugin);
	}

	@Override
	public void loadDefault() {
		// Prefix
		addDefault("prefix", "[&4Ever&6&lIcons&f] ");
		addDefault("description", "Gestion du tchat");
		
		addDefault("list.description", "Affiche la liste des icônes");
		addDefault("list.title", "&aLa liste des icônes");
		addDefault("list.line", "    <icon> &6: &7<name>");
		addDefault("list.hover", "&7Numéro : <id>");
		addDefault("list.empty", "&7Aucun icônes");
		
		addDefault("all.description", "Affiche tous les icônes");
		addDefault("all.title", "&aTous les icônes");
		addDefault("all.hover", "&7Numéro : <id>");
		addDefault("all.empty", "&7Aucun icônes");
		
		addDefault("search.description", "&aRecherche des icônes");
		addDefault("search.title", "&aRecherche d'icônes");
		addDefault("search.line", "    <icon> &6: &7<name>");
		addDefault("search.hover", "&7Numéro : <id>");
		addDefault("search.empty", "&7Aucun icônes");
	}

	@Override
	public void loadConfig() {
		// Prefix
		addMessage("PREFIX", "prefix");
		addMessage("DESCRIPTION", "description");
		
		addMessage("LIST_DESCRIPTION", "list.description");
		addMessage("LIST_TITLE", "list.title");
		addMessage("LIST_LINE", "list.line");
		addMessage("LIST_HOVER", "list.hover");
		addMessage("LIST_EMPTY", "list.empty");
		
		addMessage("ALL_DESCRIPTION", "all.description");
		addMessage("ALL_TITLE", "all.title");
		addMessage("ALL_HOVER", "all.hover");
		addMessage("ALL_EMPTY", "all.empty");
		
		addMessage("SEARCH_DESCRIPTION", "search.description");
		addMessage("SEARCH_TITLE", "search.title");
		addMessage("SEARCH_LINE", "search.line");
		addMessage("SEARCH_HOVER", "search.hover");
		addMessage("SEARCH_EMPTY", "search.empty");
	}
}
