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

import fr.evercraft.everapi.plugin.EPlugin;
import fr.evercraft.everapi.services.chat.ChatService;
import fr.evercraft.everchat.command.ECIcons;
import fr.evercraft.everchat.service.EChatService;
import fr.evercraft.everchat.service.icon.ECConfigIcons;

@Plugin(id = "fr.evercraft.everchat", 
		name = "EverChat", 
		version = "1.0", 
		description = "Manage chat",
		url = "http://evercraft.fr/",
		authors = {"rexbut"},
		dependencies = {
		    @Dependency(id = "fr.evercraft.everapi", version = "1.0")
		})
public class EverChat extends EPlugin {
	private ECConfig configs;
	private ECConfigIcons icons;
	
	private ECMessage messages;
	private ECPermission permissions;
	
	private EChatService service;
	
	@Override
	protected void onPreEnable() {
		this.permissions = new ECPermission(this);
		
		this.configs = new ECConfig(this);
		this.icons = new ECConfigIcons(this);
		
		this.messages = new ECMessage(this);
		
		this.getGame().getEventManager().registerListeners(this, new ECListener(this));
		
		this.service = new EChatService(this);
		this.getGame().getServiceManager().setProvider(this, ChatService.class, this.service);
	}

	
	@Override
	protected void onCompleteEnable() {
		new ECCommand(this);
		new ECIcons(this);
	}

	protected void onReload(){
		this.reloadConfigurations();
		this.service.reload();
	}
	
	protected void onDisable() {
	}

	/*
	 * Accesseurs
	 */
	public ECPermission getPermissions() {
		return this.permissions;
	}
	
	public ECMessage getMessages(){
		return this.messages;
	}
	
	public ECConfig getConfigs() {
		return this.configs;
	}
	
	public ECConfigIcons getConfigsIcons() {
		return this.icons;
	}

	public EChatService getService() {
		return this.service;
	}
}
