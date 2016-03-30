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
		addDefault("prefix", "[&4Ever&6&lMails&f] ");
		addDefault("description", "Gestion des mails");
		
		addDefault("list.description", "Affiche la liste des adresses mails");
		addDefault("list.title", "&aLa liste des adresses mails");
		addDefault("list.line", "    &6&l➤  <player> : &7<address>");
		addDefault("list.empty", "&7Aucune adresse mail");
		
		addDefault("set.description", "Défini une adresse mail");
		addDefault("set.player", "&7L'adresse mail de &6<player> &7a été défini en tant que &6<address>&7.");
		addDefault("set.equals", "&7Vous avez défini votre adresse mail en &6<address>&7.");
		addDefault("set.errorPattern", "&cL'adresse mail n'a pas un format valide.");
		addDefault("set.errorEquals", "&cLes adresses mails sont identiques.");
		
		addDefault("delete.description", "Supprime une adresse mail");
		addDefault("delete.player", "&7L'adresse mail de &6<player> &7 a été supprimé");
		addDefault("delete.equals", "&7Vous avez supprimé votre adresse mail.");
		addDefault("delete.errorPlayer", "&6<identifier> &cn'a pas d'adresse mail.");
		addDefault("delete.errorEquals", "&cVous n'avez pas d'adresse mail.");
		
		addDefault("alert.description", "Envoie message à tous les adresses mails");
		addDefault("alert.object", "Avertissement de <player>");
		addDefault("alert.message", "<message>");
		addDefault("alert.player", "&7Votre message d'avertissement a bien était envoyé.");
		addDefault("alert.error", "&cIl n'y a aucune adresse mail d'enregistré");
		
		addDefault("send.description", "Envoie mail à un joueur");
		addDefault("send.object", "Message de <player>");
		addDefault("send.message", "<message>");
		addDefault("send.player", "&7Votre message a bien était envoyé à &6<player>&7.");
		addDefault("send.equals", "&7Vous vous êtes bien envoyé un message.");
		addDefault("send.error", "&cIl n'y a aucune adresse mail d'enregistré au nom de &6<player>&7.");
	}

	@Override
	public void loadConfig() {
		// Prefix
		addMessage("PREFIX", "prefix");
		addMessage("DESCRIPTION", "description");
		
		addMessage("LIST_DESCRIPTION", "list.description");
		addMessage("LIST_TITLE", "list.title");
		addMessage("LIST_LINE", "list.line");
		addMessage("LIST_EMPTY", "list.empty");
		
		addMessage("SET_DESCRIPTION", "set.description");
		addMessage("SET_PLAYER", "set.player");
		addMessage("SET_EQUALS", "set.equals");
		addMessage("SET_ERROR_PATTERN", "set.errorPattern");
		addMessage("SET_ERROR_EQUALS", "set.errorEquals");
		
		addMessage("DELETE_DESCRIPTION", "delete.description");
		addMessage("DELETE_PLAYER", "delete.player");
		addMessage("DELETE_EQUALS", "delete.equals");
		addMessage("DELETE_ERROR_PLAYER", "delete.errorPlayer");
		addMessage("DELETE_ERROR_EQUALS", "delete.errorEquals");
		
		addMessage("ALERT_DESCRIPTION", "alert.description");
		addMessage("ALERT_OBJECT", "alert.object");
		addMessage("ALERT_MESSAGE", "alert.message");
		addMessage("ALERT_PLAYER", "alert.player");
		addMessage("ALERT_ERROR", "alert.error");
		
		addMessage("SEND_DESCRIPTION", "send.description");
		addMessage("SEND_OBJECT", "send.object");
		addMessage("SEND_MESSAGE", "send.message");
		addMessage("SEND_PLAYER", "send.player");
		addMessage("SEND_EQUALS", "send.equals");
		addMessage("SEND_ERROR", "send.error");
	}
}
