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

import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import fr.evercraft.everapi.EverAPI;
import fr.evercraft.everapi.event.ChatSystemEvent;
import fr.evercraft.everapi.event.ESpongeEventFactory;
import fr.evercraft.everapi.exception.PluginDisableException;
import fr.evercraft.everapi.exception.ServerDisableException;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.services.ChatService;
import fr.evercraft.everchat.command.ECIcons;
import fr.evercraft.everchat.command.sub.ECClear;
import fr.evercraft.everchat.command.sub.ECReload;
import fr.evercraft.everchat.icons.ECIconsConfig;
import fr.evercraft.everchat.icons.UtilsIcons;
import fr.evercraft.everchat.service.EChatService;

@Plugin(id = "everchat", 
		name = "EverChat", 
		version = EverAPI.VERSION, 
		description = "Manage chat",
		url = "http://evercraft.fr/",
		authors = {"rexbut"},
		dependencies = {
		    @Dependency(id = "everapi", version = EverAPI.VERSION),
		    @Dependency(id = "spongeapi", version = EverAPI.SPONGEAPI_VERSION)
		})
public class EverChat extends EPlugin<EverChat> {
	private ECConfig configs;
	private ECIconsConfig icons;
	
	private ECMessage messages;
	
	private EChatService service;
	
	public EverChat() {
		
	}
	
	@Override
	protected void onPreEnable() {
		this.configs = new ECConfig(this);
		this.icons = new ECIconsConfig(this);
		
		this.messages = new ECMessage(this);
		
		this.getGame().getEventManager().registerListeners(this, new ECListener(this));
		
		this.service = new EChatService(this);
		this.getGame().getServiceManager().setProvider(this, ChatService.class, this.service);
	}

	@Override
	protected void onEnable() {
		this.postEvent(ChatSystemEvent.Action.RELOADED);
	}
	
	@Override
	protected void onCompleteEnable() {
		ECCommand command = new ECCommand(this);
		
		command.add(new ECIcons(this));
		
		command.add(new ECClear(this, command));
		command.add(new ECReload(this, command));
		try {
			UtilsIcons.init();
			this.getELogger().info("[Icons] Editing SpongeCommon "
					+ "(Class='org.spongepowered.common.service.pagination.PaginationCalculator';Variable='UNICODE_CHAR_WIDTHS')");
		} catch (Exception e) {
			this.getELogger().warn("[Icons] Error during SpongeCommon modification "
					+ "(Class='org.spongepowered.common.service.pagination.PaginationCalculator';Variable='UNICODE_CHAR_WIDTHS') : " + e);
		}
	}

	@Override
	protected void onReload() throws PluginDisableException, ServerDisableException {
		super.onReload();
		
		this.service.reload();
	}
	
	protected void onDisable() {
	}
	
	public void postEvent(ChatSystemEvent.Action action) {
		if (action.equals(ChatSystemEvent.Action.RELOADED)) {
			this.getELogger().debug("Event ChatSystemEvent.Reload");
			this.getGame().getEventManager().post(ESpongeEventFactory.createChatSystemEventReload(this.getGame().getCauseStackManager().getCurrentCause()));
		}
	}

	/*
	 * Accesseurs
	 */
	
	public ECMessage getMessages(){
		return this.messages;
	}
	
	public ECConfig getConfigs() {
		return this.configs;
	}
	
	public ECPermissions[] getPermissions() {
		return ECPermissions.values();
	}
	
	public ECIconsConfig getConfigsIcons() {
		return this.icons;
	}

	public EChatService getService() {
		return this.service;
	}
}
