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
		addDefault("description", "Gestion du tchat", "Management of the chat");
		
		addDefault("icon.description", "Affiche la liste des icônes", "Displays the list of icons");
		addDefault("icon.listTitle", "&aLa liste des icônes", "&aThe list of icons");
		addDefault("icon.listEmtpy", "&7Aucune icône", "&7None icon");
		addDefault("icon.searchTitle", "&aRecherche d'icônes", "&aSearch for icons");
		addDefault("icon.searchEmpty", "&aAucune icône trouvée", "&aNo found icon");
		addDefault("icon.hover", "&7Numéro : &a<id>[RT]&7Nom : &a<name>", "&7ID : &a<id>[RT]&7Name : &a<name>");
		addDefault("icon.unknown", "<icon=UNKNOWN>");
	}

	@Override
	public void loadConfig() {
		// Prefix
		addMessage("PREFIX", "prefix");
		addMessage("DESCRIPTION", "description");
		
		addMessage("ICON_DESCRIPTION", "icon.description");
		addMessage("ICON_LIST_TITLE", "icon.listTitle");
		addMessage("ICON_LIST_EMPTY", "icon.listEmtpy");
		addMessage("ICON_SEARCH_TITLE", "icon.searchTitle");
		addMessage("ICON_SEARCH_EMPTY", "icon.searchEmpty");
		addMessage("ICON_HOVER", "icon.hover");
		
		addMessage("ICON_UNKNOWN", "icon.unknown");
	}
}
