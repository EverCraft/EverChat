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
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.chat.ChatService;
import fr.evercraft.everapi.services.chat.event.ChatSystemEvent;
import fr.evercraft.everapi.text.ETextBuilder;
import fr.evercraft.everchat.EverChat;

public class EChatService implements ChatService {
	private final EverChat plugin;
	
	private final ConcurrentMap<String, String> character;
	private final ConcurrentMap<String, String> icons;
	
	private final ConcurrentMap<String, String> format;
	
	public EChatService(EverChat plugin) {
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
		
		this.plugin.getGame().getEventManager().post(new ChatSystemEvent(this.plugin, ChatSystemEvent.Action.RELOADED));
	}
	
	@Override
	public String replace(String message) {
		message = replaceCharacter(message);
		message = replaceIcons(message);
		return message;
	}

	@Override
	public List<String> replace(List<String> messages) {
		messages = replaceCharacter(messages);
		messages = replaceIcons(messages);
		return messages;
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
		
		if(this.plugin.getConfigs().getIcons()) {
			for(Entry<String, String> replace : this.icons.entrySet()) {
				message = message.replace(replace.getKey(), replace.getValue());
			}
		}
		return message;
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
	
	public Text sendMessage(EPlayer player, String original) {
		String message = this.getFormat(player.getPlayer().get());
		message = this.plugin.getChat().replaceGlobal(message);
		message = this.plugin.getChat().replacePlayer(player, message);
		
		original = this.plugin.getChat().replace(original);
		
		Text original_text;
		if(player.hasPermission(this.plugin.getPermissions().get("COLOR"))) {
			original_text = EChat.of(original);
		} else {
			original_text = Text.of(original);
		}
		
		return this.plugin.getChat().replaceVariableText(player, 
				ETextBuilder.toBuilder(message)
					.replace("<MESSAGE>", original_text)
					.replace("<DISPLAYNAMEHOVER>", player.getDisplayNameHover()));
	}
}
