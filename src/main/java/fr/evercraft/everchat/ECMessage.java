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

import fr.evercraft.everapi.message.EMessageFormat;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.plugin.file.EMessage;
import fr.evercraft.everapi.plugin.file.EnumMessage;

public class ECMessage extends EMessage<EverChat> {

	public ECMessage(final EverChat plugin) {
		super(plugin, ECMessages.values());
	}
	
	public enum ECMessages implements EnumMessage {
		PREFIX("prefix",  							
				"[&4Ever&6&lIcons&f] "),
		DESCRIPTION("description", 
				"Gestion du tchat", 
				"Management of the chat"),
		CLEAR_DESCRIPTION("clear.description",
				"Efface le chat du jeu"),
		CLEAR_PLAYER("clear.player",
				"&7Votre chat a été effacé par &6<player>&7."),
		CLEAR_OTHERS("clear.others",
				"&7Vous avez effacé le chat de &6<player>&7."),
		CLEAR_ALL("clear.all",
				"&7Vous avez effacé le chat de tous les joueurs."),
		ICON_DESCRIPTION("icon.description", 
				"Affiche la liste des icônes", 
				"Displays the list of icons"),
		ICON_LIST_TITLE("icon.listTitle", 
				"&aLa liste des icônes", 
				"&aThe list of icons"),
		ICON_LIST_EMPTY("icon.listEmtpy", 
				"&7Aucune icône", 
				"&7None icon"),
		ICON_SEARCH_TITLE("icon.searchTitle", 
				"&aRecherche d'icônes", 
				"&aSearch for icons"),
		ICON_SEARCH_EMPTY("icon.searchEmpty", 
				"&aAucune icône trouvée", 
				"&aNo found icon"),
		ICON_HOVER("icon.hover", 
				"&7Numéro : &a<id>[RT]&7Nom : &a<name>", 
				"&7ID : &a<id>[RT]&7Name : &a<name>"),
		ICON_UNKNOWN("icon.unknown", "<icon=UNKNOWN>");
		
		private final String path;
	    private final EMessageFormat french;
	    private final EMessageFormat english;
	    private EMessageFormat message;
	    
	    private ECMessages(final String path, final String french) {   	
	    	this(path, 
	    		EMessageFormat.builder().chat(new EFormatString(french), true).build(), 
	    		EMessageFormat.builder().chat(new EFormatString(french), true).build());
	    }
	    
	    private ECMessages(final String path, final String french, final String english) {   	
	    	this(path, 
	    		EMessageFormat.builder().chat(new EFormatString(french), true).build(), 
	    		EMessageFormat.builder().chat(new EFormatString(english), true).build());
	    }
	    
	    private ECMessages(final String path, final EMessageFormat french) {   	
	    	this(path, french, french);
	    }
	    
	    private ECMessages(final String path, final EMessageFormat french, final EMessageFormat english) {
	    	Preconditions.checkNotNull(french, "Le message '" + this.name() + "' n'est pas définit");
	    	
	    	this.path = path;	    	
	    	this.french = french;
	    	this.english = english;
	    	this.message = french;
	    }

	    public String getName() {
			return this.name();
		}
	    
		public String getPath() {
			return this.path;
		}

		public Object getFrench() {
			return this.french;
		}

		public Object getEnglish() {
			return this.english;
		}
		
		public EMessageFormat getMessage() {
			return this.message;
		}
		
		public void set(EMessageFormat message) {
			this.message = message;
		}
	}
}
