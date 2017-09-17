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

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.message.EMessageBuilder;
import fr.evercraft.everapi.message.EMessageFormat;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.plugin.file.EMessage;
import fr.evercraft.everapi.plugin.file.EnumMessage;

public class ECMessage extends EMessage<EverChat> {

	public ECMessage(final EverChat plugin) {
		super(plugin, ECMessages.values());
	}
	
	public enum ECMessages implements EnumMessage {
		PREFIX("[&4Ever&6&lIcons&f] "),
		DESCRIPTION(
				"Gestion du tchat", 
				"Management of the chat"),
		CLEAR_DESCRIPTION("Efface le chat du jeu"),
		CLEAR_PLAYER("&7Votre chat a été effacé par &6{player}&7."),
		CLEAR_OTHERS("&7Vous avez effacé le chat de &6{player}&7."),
		CLEAR_ALL("&7Vous avez effacé le chat de tous les joueurs."),
		ICON_DESCRIPTION(
				"Affiche la liste des icônes", 
				"Displays the list of icons"),
		ICON_LIST_TITLE(
				"&aLa liste des icônes", 
				"&aThe list of icons"),
		ICON_LIST_EMPTY(
				"&7Aucune icône", 
				"&7None icon"),
		ICON_SEARCH_TITLE(
				"&aRecherche d'icônes", 
				"&aSearch for icons"),
		ICON_SEARCH_EMPTY(
				"&aAucune icône trouvée", 
				"&aNo found icon"),
		ICON_HOVER(
				"&7Numéro : &a{id}[RT]&7Nom : &a{name}", 
				"&7ID : &a{id}[RT]&7Name : &a{name}"),
		ICON_UNKNOWN("{icon=UNKNOWN}"),
		
		PERMISSIONS_COMMANDS_EXECUTE(""),
		PERMISSIONS_COMMANDS_HELP(""),
		PERMISSIONS_COMMANDS_RELOAD(""),
		PERMISSIONS_COMMANDS_CLEAR_EXECUTE(""),
		PERMISSIONS_COMMANDS_CLEAR_OTHERS(""),
		PERMISSIONS_COMMANDS_ICONS_EXECUTE(""),
		PERMISSIONS_REPLACES_COLOR(""),
		PERMISSIONS_REPLACES_FORMAT(""),
		PERMISSIONS_REPLACES_MAGIC(""),
		PERMISSIONS_REPLACES_CHARACTER(""),
		PERMISSIONS_REPLACES_COMMAND(""),
		PERMISSIONS_REPLACES_ICONS(""),
		PERMISSIONS_REPLACES_URL("");
		
		private final String path;
	    private final EMessageBuilder french;
	    private final EMessageBuilder english;
	    private EMessageFormat message;
	    private EMessageBuilder builder;
	    
	    private ECMessages(final String french) {   	
	    	this(EMessageFormat.builder().chat(new EFormatString(french), true));
	    }
	    
	    private ECMessages(final String french, final String english) {   	
	    	this(EMessageFormat.builder().chat(new EFormatString(french), true), 
	    		EMessageFormat.builder().chat(new EFormatString(english), true));
	    }
	    
	    private ECMessages(final EMessageBuilder french) {   	
	    	this(french, french);
	    }
	    
	    private ECMessages(final EMessageBuilder french, final EMessageBuilder english) {
	    	Preconditions.checkNotNull(french, "Le message '" + this.name() + "' n'est pas définit");
	    	
	    	this.path = this.resolvePath();	    	
	    	this.french = french;
	    	this.english = english;
	    	this.message = french.build();
	    }

	    public String getName() {
			return this.name();
		}
	    
		public String getPath() {
			return this.path;
		}

		public EMessageBuilder getFrench() {
			return this.french;
		}

		public EMessageBuilder getEnglish() {
			return this.english;
		}
		
		public EMessageFormat getMessage() {
			return this.message;
		}
		
		public EMessageBuilder getBuilder() {
			return this.builder;
		}
		
		public void set(EMessageBuilder message) {
			this.message = message.build();
			this.builder = message;
		}
	}
}
