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

import java.util.Optional;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.server.player.EPlayer;

public class ECListener {
	private EverChat plugin;
	
	public ECListener(final EverChat plugin) {
		this.plugin = plugin;
	}
	
    @Listener
    public void onPlayerWriteChat(MessageChannelEvent.Chat event, @First Player player) {
		Optional<EPlayer> eplayer = this.plugin.getEServer().getEPlayer(player);
		if(eplayer.isPresent()){
			event.setMessage(this.plugin.getService().sendMessage(eplayer.get(), TextSerializers.formattingCode('&').serialize(event.getRawMessage())));
		}
    }
    
    @Listener
	public void onSignChange(ChangeSignEvent event, @First Player player) {
		SignData signData = event.getText();
		Optional<ListValue<Text>> value = signData.getValue(Keys.SIGN_LINES);
		if(player.hasPermission(this.plugin.getPermissions().get("COLOR_SIGN")) && value.isPresent()) {
			signData = signData.set(value.get().set(0, EChat.of(this.plugin.getChat().replace(value.get().get(0).toPlain()))));
			signData = signData.set(value.get().set(1, EChat.of(this.plugin.getChat().replace(value.get().get(1).toPlain()))));
			signData = signData.set(value.get().set(2, EChat.of(this.plugin.getChat().replace(value.get().get(2).toPlain()))));
			signData = signData.set(value.get().set(3, EChat.of(this.plugin.getChat().replace(value.get().get(3).toPlain()))));
		}
	}

}
