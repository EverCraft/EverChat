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

import java.util.Optional;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.serializer.TextSerializers;

import fr.evercraft.everapi.server.player.EPlayer;

public class ECListener {
	private EverChat plugin;
	
	public ECListener(final EverChat plugin) {
		this.plugin = plugin;
	}
	
    @Listener
    public void onPlayerWriteChat(MessageChannelEvent.Chat event, @First Player player) {
    	if (this.plugin.getConfigs().enableFormat()) {
			Optional<EPlayer> eplayer = this.plugin.getEServer().getEPlayer(player);
			if (eplayer.isPresent()){
				event.setMessage(this.plugin.getService().sendMessage(eplayer.get(), TextSerializers.formattingCode('&').serialize(event.getRawMessage())));
			}
    	}
    }
}
