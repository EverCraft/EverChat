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
package fr.evercraft.everchat.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectReference;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import com.google.common.base.Preconditions;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.event.ChatSystemEvent;
import fr.evercraft.everapi.message.format.EFormatString;
import fr.evercraft.everapi.message.replace.EReplace;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.ChatService;
import fr.evercraft.everchat.ECMessage.ECMessages;
import fr.evercraft.everchat.ECPermissions;
import fr.evercraft.everchat.EverChat;
import fr.evercraft.everchat.icons.UtilsIcons;

public class EChatService implements ChatService {
	private static final Pattern ICONS_PATTERN = Pattern.compile("\\{(?:icon|Icon|ICON)\\=(.*?)}");
	private static final Pattern COMMAND_PATTERN = Pattern.compile("/(.*?)\\$");
	private static final Pattern URL_PATTERN = Pattern.compile("((?<style>(&[0-9a-z])*)(?<url>(((https?)://)?[\\w-_\\.]{2,})\\.([a-z]{2,3}(/\\S+)?)))");
	
	public static final Pattern PATTERN_IP = Pattern.compile("((25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3})");
	
	private final EverChat plugin;
	
	private final ConcurrentMap<String, String> icons;
	
	private final ConcurrentMap<Pattern, EReplace<?>> replaces;
	
	private final ConcurrentMap<String, String> format;
	
	public EChatService(final EverChat plugin) {
		this.plugin = plugin;
		
		this.replaces = new ConcurrentHashMap<Pattern, EReplace<?>>();
		this.icons = new ConcurrentHashMap<String, String>();
		
		this.format = new ConcurrentHashMap<String, String>();
		
		reload();
	}
	
	public void reload() {
		this.icons.clear();
		this.replaces.clear();
		
		this.icons.putAll(this.plugin.getConfigsIcons().getAllIcons());
		this.format.putAll(this.plugin.getConfigs().getFormatGroups());
		
		this.plugin.getConfigs().getReplaces().forEach((key, value) -> {
			key = key.replace("[", "\\[").replace("{", "\\{").replace("(", "\\(").replace("*", "\\*");
			this.replaces.put(Pattern.compile(key), EReplace.of(value));
		});
		
		this.plugin.postEvent(ChatSystemEvent.Action.RELOADED);
	}
	
	@Override
	public Map<Pattern, EReplace<?>> getReplaceAll() {
		Map<Pattern, EReplace<?>> builder = this.getReplaceCharacters();
		builder.putAll(this.getReplaceIcons());
		builder.putAll(this.getReplaceCommand());
		builder.putAll(this.getReplaceUrl());
		return builder;
	}
	
	@Override
	public Map<Pattern, EReplace<?>> getReplaceCharacters() {
		return new HashMap<Pattern, EReplace<?>>(this.replaces);
	}
	
	@Override
	public Map<Pattern, EReplace<?>> getReplaceIcons() {
		HashMap<Pattern, EReplace<?>> builder = new HashMap<Pattern, EReplace<?>>();
		builder.put(ICONS_PATTERN, EReplace.of(key -> {
			try { 
		        String value = String.valueOf((char)(UtilsIcons.CHARACTER + Integer.parseInt(key)));
		        if (this.icons.containsValue(value)) {
		        	return value;
				}
		    } catch(NumberFormatException | NullPointerException e) { 
		    	String value = this.icons.get(key);
		    	if (value != null) {
					return value;
				}
		    }
			return ECMessages.ICON_UNKNOWN.getString();
		}));
		return builder;
	}
	
	@Override
	public Map<Pattern, EReplace<?>> getReplaceCommand() {
		HashMap<Pattern, EReplace<?>> builder = new HashMap<Pattern, EReplace<?>>();
		builder.put(COMMAND_PATTERN, EReplace.of(value -> Text.builder("/" + value)
				.onHover(TextActions.showText(EAMessages.HOVER_COPY.getText()))
				.onClick(TextActions.suggestCommand("/" + value))
				.onShiftClick(TextActions.insertText("/" + value))
			.build()));
		return builder;
	}
	
	@Override
	public Map<Pattern, EReplace<?>> getReplaceUrl() {
		HashMap<Pattern, EReplace<?>> builder = new HashMap<Pattern, EReplace<?>>();
		builder.put(URL_PATTERN, EReplace.of(value -> {
			Matcher matcher = URL_PATTERN.matcher(value);
			if (matcher.find()) {
				String style = matcher.group("style");
				String url = matcher.group("url");
				try {
					return Text.builder(url)
						.format(EChat.getTextFormat((style == null) ? "" : style))
						.onShiftClick(TextActions.insertText(url))
						.onClick(TextActions.openUrl(new URL(value.startsWith("http") ? url : "http://" + url)))
						.onHover(TextActions.showText(EAMessages.HOVER_URL.getText()))
						.build();
				} catch (MalformedURLException e) {}
			}
			return Text.builder(value)
				.onShiftClick(TextActions.insertText(value))
				.build();
		}));
		builder.put(PATTERN_IP, EReplace.of(value -> {
			try {
				return Text.builder(value)
					.onShiftClick(TextActions.insertText(value))
					.onClick(TextActions.openUrl(new URL(value.startsWith("http") ? value : "http://" + value)))
					.onHover(TextActions.showText(EAMessages.HOVER_URL.getText()))
					.build();
			} catch (MalformedURLException e) {}
			return Text.builder(value)
				.onShiftClick(TextActions.insertText(value))
				.build();
		}));
		return builder;
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
		Optional<SubjectReference> group = this.getGroup(subject, contexts);
		
		if (group.isPresent()) {
			format = this.format.get(group.get().getSubjectIdentifier());
		}
		
		if (format == null) {
			format = this.plugin.getConfigs().getFormatDefault();
		}
        return format;
    }
	
	private Optional<SubjectReference> getGroup(final Subject subject, final Set<Context> contexts) {
		Preconditions.checkNotNull(subject, "subject");
		Preconditions.checkNotNull(contexts, "contexts");
		
		List<SubjectReference> groups = subject.getSubjectData().getParents(contexts);
		if (!groups.isEmpty()) {
			return Optional.of(groups.get(0));
		}
		return Optional.empty();
    }
	
	public Text sendMessage(final EPlayer player, String original) {
		String format = this.getFormat(player.getPlayer().get());
		Map<Pattern, EReplace<?>> replaces = player.getReplaces();
		
		if (!player.hasPermission(ECPermissions.COLOR.get())) {
			original = original.replaceAll(ChatService.REGEX_COLOR, "");
		}
		if (!player.hasPermission(ECPermissions.FORMAT.get())) {
			original = original.replaceAll(ChatService.REGEX_FORMAT, "");
		}
		if (!player.hasPermission(ECPermissions.MAGIC.get())) {
			original = original.replaceAll(ChatService.REGEX_MAGIC, "");
		}
		if (player.hasPermission(ECPermissions.CHARACTER.get())) {
			replaces.putAll(this.getReplaceCharacters());
		}
		if (player.hasPermission(ECPermissions.COMMAND.get())) {
			replaces.putAll(this.getReplaceCommand());
		}
		if (player.hasPermission(ECPermissions.ICONS.get())) {
			replaces.putAll(this.getReplaceIcons());
		}
		if (player.hasPermission(ECPermissions.URL.get())) {
			replaces.putAll(this.getReplaceUrl());
		}
		
		replaces.put(Pattern.compile("\\{MESSAGE}"), EReplace.of(EFormatString.of(original).toText(replaces)));
		
		return EFormatString.of(format).toText(replaces);
	}
	
	/*
	 * Accesseurs
	 */
	
	public Map<String, String> getIcons() {
		return this.icons;
	}
}
