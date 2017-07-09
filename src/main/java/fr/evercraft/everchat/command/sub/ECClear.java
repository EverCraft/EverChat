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
package fr.evercraft.everchat.command.sub;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.plugin.command.ESubCommand;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everchat.ECMessage.ECMessages;
import fr.evercraft.everchat.ECPermissions;
import fr.evercraft.everchat.ECCommand;
import fr.evercraft.everchat.EverChat;

public class ECClear extends ESubCommand<EverChat> {
	private static final Text CLEAR = Text.of("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
			+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
			+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
			+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	
	public ECClear(final EverChat plugin, final ECCommand command) {
        super(plugin, command, "clear");
    }
	
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(ECPermissions.CLEAR.get());
	}

	public Text description(final CommandSource source) {
		return ECMessages.CLEAR_DESCRIPTION.getText();
	}
	
	public Collection<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		if (args.size() == 1 && source.hasPermission(ECPermissions.CLEAR_OTHERS.get())){
			return this.getAllPlayers(source, true);
		}
		return Arrays.asList();
	}

	public Text help(final CommandSource source) {
		if (source.hasPermission(ECPermissions.CLEAR_OTHERS.get())){
			return Text.builder("/" + this.getName() + " clear [" + EAMessages.ARGS_PLAYER + "|*]")
						.onClick(TextActions.suggestCommand("/" + this.getName() + " clear"))
						.color(TextColors.RED)
						.build();
		} else {
			return Text.builder("/" + this.getName() + " clear")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " clear"))
					.color(TextColors.RED)
					.build();
		}
	}
	
	public CompletableFuture<Boolean> execute(final CommandSource source, final List<String> args) throws CommandException {
		if (args.isEmpty()) {
			// Si la source est bien un joueur
			if (source instanceof EPlayer) {
				return this.commandClear((EPlayer) source);
			// Si la source est une console ou un commande block
			} else {
				EAMessages.COMMAND_ERROR_FOR_PLAYER.sender()
					.prefix(ECMessages.PREFIX)
					.sendTo(source);
			}
		} else {
			if (source.hasPermission(ECPermissions.CLEAR_OTHERS.get())) {
				if (args.get(0).equals("*") || args.get(0).equalsIgnoreCase("all")) {
					return this.commandClearAll(source);
				} else {
					Optional<EPlayer> optPlayer = this.plugin.getEServer().getEPlayer(args.get(1));
					// Le joueur existe
					if (optPlayer.isPresent()){
						return this.commandClearOthers(source, optPlayer.get());
					// Le joueur est introuvable
					} else {
						EAMessages.PLAYER_NOT_FOUND.sender()
							.prefix(ECMessages.PREFIX)
							.sendTo(source);
					}
				}
			} else {
				EAMessages.NO_PERMISSION.sender()
					.prefix(ECMessages.PREFIX)
					.sendTo(source);
			}
		}
		
		return CompletableFuture.completedFuture(false);
	}

	private CompletableFuture<Boolean> commandClear(CommandSource player) {
		player.sendMessage(CLEAR);
		return CompletableFuture.completedFuture(true);
	}
	
	private CompletableFuture<Boolean> commandClearOthers(CommandSource staff, EPlayer player) throws CommandException{
		if (!staff.equals(player)) {
			player.sendMessage(CLEAR);
			ECMessages.CLEAR_OTHERS.sender()
				.replace("<player>", () -> player.getDisplayName())
				.sendTo(staff);
			ECMessages.CLEAR_PLAYER.sender()
				.replace("<player>", () -> staff.getName())
				.sendTo(player);
			return CompletableFuture.completedFuture(true);
		} else {
			return this.commandClear(staff);
		}
	}
	
	private CompletableFuture<Boolean> commandClearAll(CommandSource player){
		for (EPlayer target : this.plugin.getEServer().getOnlineEPlayers()){
			if (!player.equals(target)){
				player.sendMessage(CLEAR);
				ECMessages.CLEAR_PLAYER.sender()
					.replace("<player>", () -> player.getName())
					.sendTo(target);
			}
		}
		ECMessages.CLEAR_ALL.sendTo(player);
		return CompletableFuture.completedFuture(true);
	}
}
