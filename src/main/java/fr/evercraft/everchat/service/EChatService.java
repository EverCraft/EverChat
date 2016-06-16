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
package fr.evercraft.everchat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.event.ChatSystemEvent;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.ChatService;
import fr.evercraft.everapi.text.ETextBuilder;
import fr.evercraft.everchat.ECMessage.ECMessages;
import fr.evercraft.everchat.ECPermissions;
import fr.evercraft.everchat.EverChat;

public class EChatService implements ChatService {
	public static final int CHARACTER = 36864;
	
	private final EverChat plugin;
	
	private final ConcurrentMap<String, String> character;
	private final ConcurrentMap<String, String> icons;
	
	private final ConcurrentMap<String, String> format;
	
	public EChatService(final EverChat plugin) {
		this.plugin = plugin;
		
		this.character = new ConcurrentHashMap<String, String>();
		this.icons = new ConcurrentHashMap<String, String>();
		
		this.format = new ConcurrentHashMap<String, String>();
		
		reload();
	}
	
	public void reload() {
		this.character.putAll(this.plugin.getConfigs().getReplaces());
		this.icons.putAll(this.plugin.getConfigsIcons().getAllIcons());
		
		this.format.putAll(this.plugin.getConfigs().getFormatGroups());
		
		this.plugin.postEvent(ChatSystemEvent.Action.RELOADED);
	}
	
	/*
	 * Un message
	 */
	
	@Override
	public String replace(String message) {
		Preconditions.checkNotNull(message, "message");
		
		message = replaceCharacter(message);
		message = replaceIcons(message);
		return message;
	}
	
	public String replaceCharacter(String message) {
		Preconditions.checkNotNull(message, "message");
		for(Entry<String, String> replace : this.character.entrySet()) {
			message = message.replace(replace.getKey(), replace.getValue());
		}
		return message;
    }
	
	public String replaceIcons(String message) {
		Preconditions.checkNotNull(message, "message");
		
		Pattern pattern = Pattern.compile("<(icon|Icon|ICON)=(.*?)>");
		Matcher matcher = pattern.matcher(message);
		while(matcher.find()) {
			String name = matcher.group(2);
			try { 
		        String value = String.valueOf((char)(CHARACTER + Integer.parseInt(name)));
		        if(this.icons.containsValue(value)) {
					message = matcher.replaceFirst(value);
				} else {
					message = matcher.replaceFirst(ECMessages.ICON_UNKNOWN.get());
				}
		    } catch(NumberFormatException | NullPointerException e) { 
		    	if(this.icons.containsKey(name)) {
					message = matcher.replaceFirst(this.icons.get(name));
				} else {
					message = matcher.replaceFirst(ECMessages.ICON_UNKNOWN.get());
				}
		    }
			matcher = pattern.matcher(message);
        }
		return message;
    }
	
	/*
	 * Liste de message
	 */
	
	@Override
	public List<String> replace(List<String> messages) {
		Preconditions.checkNotNull(messages, "messages");
		
		messages = replaceCharacter(messages);
		messages = replaceIcons(messages);
		return messages;
	}
	
	public List<String> replaceCharacter(final List<String> messages) {
		Preconditions.checkNotNull(messages, "messages");
		
		List<String> list = new ArrayList<String>();
        for(String message : messages){
        	list.add(this.replaceCharacter(message));
        }
        return list;
    }
	
	public List<String> replaceIcons(final List<String> messages) {
		Preconditions.checkNotNull(messages, "messages");
		
		List<String> list = new ArrayList<String>();
        for(String message : messages){
        	list.add(this.replaceIcons(message));
        }
        return list;
    }
	
	/*
	 * Format
	 */
	
	@Override
	public String getFormat(final Subject subject) {
		return this.getFormat(subject, subject.getActiveContexts());
	}
	
	@Override
	public String getFormat(final Subject subject, Set<Context> contexts) {
		Preconditions.checkNotNull(subject, "subject");
		
		String format = null;
		Optional<Subject> group = this.getGroup(subject, contexts);
		
		if(group.isPresent()) {
			format = this.format.get(group.get().getIdentifier());
		}
		
		if(format == null) {
			format = this.plugin.getConfigs().getFormatDefault();
		}
        return format;
    }
	
	private Optional<Subject> getGroup(final Subject subject, final Set<Context> contexts) {
		Preconditions.checkNotNull(subject, "subject");
		Preconditions.checkNotNull(contexts, "contexts");
		
		List<Subject> groups = subject.getSubjectData().getParents(contexts);
		if(!groups.isEmpty()) {
			return Optional.of(groups.get(0));
		}
		return Optional.empty();
    }
	
	public Text sendMessage(final EPlayer player, String original) {
		String format = this.getFormat(player.getPlayer().get());
		format = this.plugin.getChat().replaceGlobal(format);
		format = this.plugin.getChat().replacePlayer(player, format);
		
		original = this.plugin.getChat().replace(original);
		
		if(!player.hasPermission(ECPermissions.COLOR.get())) {
			original = original.replaceAll(EChat.REGEX_COLOR, "");
		}
		if(!player.hasPermission(ECPermissions.FORMAT.get())) {
			original = original.replaceAll(EChat.REGEX_FORMAT, "");
		}
		if(!player.hasPermission(ECPermissions.MAGIC.get())) {
			original = original.replaceAll(EChat.REGEX_MAGIC, "");
		}
		return this.plugin.getChat().replaceFormat(player, ETextBuilder.toBuilder(format).replace("<MESSAGE>", EChat.of(original)));
	}
	
	/*
	 * Accesseurs
	 */
	
	public Map<String, String> getIcons() {
		return this.icons;
	}
}
