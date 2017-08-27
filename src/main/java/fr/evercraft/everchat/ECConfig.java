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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import fr.evercraft.everapi.message.replace.EReplacesPlayer;
import fr.evercraft.everapi.plugin.file.EConfig;
import fr.evercraft.everapi.plugin.file.EMessage;

public class ECConfig extends EConfig<EverChat> {

	public ECConfig(final EverChat plugin) {
		super(plugin);
	}
	
	public void reload() {
		super.reload();
		this.plugin.getELogger().setDebug(this.isDebug());
	}
	
	@Override
	public void loadDefault() {
		addDefault("DEBUG", false, "Displays plugin performance in the logs");
		addDefault("LANGUAGE", EMessage.FRENCH, "Select language messages", "Examples : ", "  French : FR_fr", "  English : EN_en");
		
		addDefault("enable-format", true);
		addDefault("enable-icons", true);
		
		Map<String, String> replaces = new HashMap<String, String>();
		replaces.put("[<3]", "\u2764");
		replaces.put("[check]", "\u2714");
		replaces.put("[*]", "\u2716");
		replaces.put("[ARROW]", "\u279c");
		replaces.put("[X]", "\u2588");
		replaces.put("[RT]", "\n");
		addDefault("replaces", replaces);
		
		addDefault("format-default", EReplacesPlayer.DISPLAYNAME.getName() + " &7:&f {MESSAGE}");
		
		Map<String, String> formats = new HashMap<String, String>();
		formats.put("Admin", "&f[&4Admin&f] " + EReplacesPlayer.DISPLAYNAME.getName() + " &7:&f {MESSAGE}");
		formats.put("Moderator", "&f[&5Moderator&f] " + EReplacesPlayer.DISPLAYNAME.getName() + " &7:&f {MESSAGE}");
		addDefault("format-groups", formats);
	}
	
	public Map<String, String> getReplaces() {
		Map<String, String> replaces = new HashMap<String, String>();
		for (Entry<Object, ? extends CommentedConfigurationNode> node : this.get("replaces").getChildrenMap().entrySet()) {
			if (node.getKey() instanceof String) {
				replaces.put((String) node.getKey(), node.getValue().getString(""));
			}
		}
		return replaces;
	}
	
	public Map<String, String> getFormatGroups() {
		Map<String, String> replaces = new HashMap<String, String>();
		for (Entry<Object, ? extends CommentedConfigurationNode> node : this.get("format-groups").getChildrenMap().entrySet()) {
			String value = node.getValue().getString(null);
			if (node.getKey() instanceof String && value != null) {
				replaces.put((String) node.getKey(), value);
			}
		}
		return replaces;
	}
	
	public String getFormatDefault() {
		return this.get("format-default").getString("<{NAME}> {MESSAGE}");
	}
	
	public boolean enableFormat() {
		return this.get("enable-format").getBoolean(true);
	}
	
	public boolean enableIcons() {
		return this.get("enable-icons").getBoolean(true);
	}
}
