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

import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import fr.evercraft.everapi.plugin.EPermission;
import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.services.chat.ChatService;
import fr.evercraft.everapi.services.chat.event.ChatSystemEvent;
import fr.evercraft.everchat.icons.ECIconsConfig;
import fr.evercraft.everchat.service.EChatService;

@Plugin(id = "fr.evercraft.everchat", 
		name = "EverChat", 
		version = "1.1", 
		description = "Manage chat",
		url = "http://evercraft.fr/",
		authors = {"rexbut"},
		dependencies = {
		    @Dependency(id = "fr.evercraft.everapi", version = "1.1")
		})
public class EverChat extends EPlugin {
	private ECConfig configs;
	private ECIconsConfig icons;
	
	private ECMessage messages;
	
	private EChatService service;
	
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
		new ECCommand(this);
	}

	protected void onReload(){
		this.reloadConfigurations();
		this.service.reload();
		this.postEvent(ChatSystemEvent.Action.RELOADED);
	}
	
	protected void onDisable() {
	}
	
	private void postEvent(ChatSystemEvent.Action action) {
		this.getLogger().debug("Event ChatSystemEvent : (Action='" + action.name() +"')");
		this.getGame().getEventManager().post(new ChatSystemEvent(this, action));
	}

	/*
	 * Accesseurs
	 */
	public EPermission getPermissions() {
		return null;
	}
	
	public ECMessage getMessages(){
		return this.messages;
	}
	
	public ECConfig getConfigs() {
		return this.configs;
	}
	
	public ECIconsConfig getConfigsIcons() {
		return this.icons;
	}

	public EChatService getService() {
		return this.service;
	}
}
