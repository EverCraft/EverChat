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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.LiteralText.Builder;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import fr.evercraft.everapi.EAMessage.EAMessages;
import fr.evercraft.everapi.plugin.EChat;
import fr.evercraft.everapi.plugin.ECommand;
import fr.evercraft.everapi.server.player.EPlayer;
import fr.evercraft.everapi.services.pagination.ESubCommand;
import fr.evercraft.everchat.ECMessage.ECMessages;
import fr.evercraft.everchat.icons.ECIconsCommand;

public class ECCommand extends ECommand<EverChat> {
	private final ECIconsCommand icons;

	public ECCommand(final EverChat plugin) {
		super(plugin, "everchat", "chat");
		
		this.icons = new ECIconsCommand(this.plugin);
    }
	
	@Override
	public boolean testPermission(final CommandSource source) {
		return source.hasPermission(ECPermissions.EVERCHAT.get());
	}
	
	@Override
	public Text description(final CommandSource source) {
		return ECMessages.DESCRIPTION.getText();
	}

	@Override
	public Text help(final CommandSource source) {
		boolean help = source.hasPermission(ECPermissions.HELP.get());
		boolean reload = source.hasPermission(ECPermissions.RELOAD.get());
		boolean clear = source.hasPermission(ECPermissions.CLEAR.get()) || source.hasPermission(ECPermissions.CLEAR_OTHERS.get());;

		Builder build;
		if(help || reload || clear){
			build = Text.builder("/" + this.getName() + " <");
			if(help){
				build = build.append(Text.builder("help").onClick(TextActions.suggestCommand("/" + this.getName() + " help")).build());
				if(reload){
					build = build.append(Text.builder("|").build());
				}
			}
			if(reload){
				build = build.append(Text.builder("reload").onClick(TextActions.suggestCommand("/" + this.getName() + " reload")).build());
				if(clear){
					build = build.append(Text.builder("|").build());
				}
			}
			if(clear){
				build = build.append(Text.builder("clear").onClick(TextActions.suggestCommand("/" + this.getName() + " clear")).build());
			}
			build = build.append(Text.builder(">").build());
		} else {
			build = Text.builder("/" + this.getName()).onClick(TextActions.suggestCommand("/" + this.getName()));
		}
		return build.color(TextColors.RED).build();
	}
	
	public Text helpReload(final CommandSource source) {
		return Text.builder("/" + this.getName() + " reload")
					.onClick(TextActions.suggestCommand("/" + this.getName() + " reload"))
					.color(TextColors.RED)
					.build();
	}
	
	public Text helpClear(final CommandSource source) {
		if (source.hasPermission(ECPermissions.CLEAR_OTHERS.get())){
			return Text.builder("/" + this.getName() + " clear [joueur|*]")
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

	@Override
	public List<String> tabCompleter(final CommandSource source, final List<String> args) throws CommandException {
		List<String> suggests = new ArrayList<String>();
		if(args.size() == 1) {
			if(source.hasPermission(ECPermissions.RELOAD.get())) {
				suggests.add("reload");
			}
			if(source.hasPermission(ECPermissions.CLEAR.get()) || source.hasPermission(ECPermissions.CLEAR_OTHERS.get())) {
				suggests.add("clear");
			}
		} else if(args.size() == 2 && source.hasPermission(ECPermissions.CLEAR_OTHERS.get())) {
			suggests = null;
		}
		return suggests;
	}
	
	public boolean execute(final CommandSource source, final List<String> args) throws CommandException {
		boolean resultat = false;
		if(args.size() == 0 || args.get(0).equalsIgnoreCase("help")) {
			if(source.hasPermission(ECPermissions.HELP.get())) {
				resultat = commandHelp(source);
			} else {
				source.sendMessage(EAMessages.NO_PERMISSION.getText());
			}
		} else if(args.size() == 1) {
			if(args.get(0).equalsIgnoreCase("reload")) {
				if(source.hasPermission(ECPermissions.RELOAD.get())) {
					resultat = commandReload(source);
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else if(args.get(0).equalsIgnoreCase("clear")) {
				if(source.hasPermission(ECPermissions.CLEAR.get())) {
					// Si la source est bien un joueur
					if(source instanceof EPlayer) {
						resultat = commandClear((EPlayer) source);
					// Si la source est une console ou un commande block
					} else {
						source.sendMessage(ECMessages.PREFIX.getText().concat(EAMessages.COMMAND_ERROR_FOR_PLAYER.getText()));
					}
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else {
				source.sendMessage(help(source));
			}
		} else if(args.size() == 2){
			this.plugin.getEServer().broadcast("test 1");
			if(args.get(0).equalsIgnoreCase("clear")) {
				if(source.hasPermission(ECPermissions.CLEAR_OTHERS.get())) {
					// Si la source est bien un joueur
					if(source instanceof EPlayer) {
						if(args.get(1).equals("*")) {
							resultat = commandClearAll(source);
						} else {
							Optional<EPlayer> optPlayer = this.plugin.getEServer().getEPlayer(args.get(1));
							// Le joueur existe
							if(optPlayer.isPresent()){
								resultat = commandClearOthers(source, optPlayer.get());
							// Le joueur est introuvable
							} else {
								source.sendMessage(ECMessages.PREFIX.getText().concat(EAMessages.PLAYER_NOT_FOUND.getText()));
							}
						}
					// Si la source est une console ou un commande block
					} else {
						source.sendMessage(ECMessages.PREFIX.getText().concat(EAMessages.COMMAND_ERROR_FOR_PLAYER.getText()));
					}
				} else {
					source.sendMessage(EAMessages.NO_PERMISSION.getText());
				}
			} else {
				source.sendMessage(help(source));
			}
		} else {
			source.sendMessage(help(source));
		}
		return resultat;
	}
	
	private boolean commandClear(CommandSource source) {
		for(int cpt=0; cpt < 60; cpt++) {
			source.sendMessage(Text.of(""));
		}
		return true;
	}
	
	private boolean commandClearOthers(CommandSource source, EPlayer destination) throws CommandException{
		if(!source.equals(destination)){
			if(commandClear(destination)){
				source.sendMessage(EChat.of(ECMessages.PREFIX.get() + ECMessages.CLEAR_OTHERS.get()
						.replaceAll("<player>", destination.getDisplayName())));
				destination.sendMessage(ECMessages.PREFIX.get() + ECMessages.CLEAR_PLAYER.get()
						.replaceAll("<player>", source.getName()));
				return true;
			} else {
				return false;
			}
		} else {
			return execute(source, new ArrayList<String>());
		}
	}
	
	private boolean commandClearAll(CommandSource source){
		for(EPlayer destination : this.plugin.getEServer().getOnlineEPlayers()){
			if(!source.equals(destination)){
				if(commandClear(destination)){
					destination.sendMessage(ECMessages.PREFIX.get() + ECMessages.CLEAR_PLAYER.get()
							.replaceAll("<player>", source.getName()));
				}
			}
		}
		source.sendMessage(ECMessages.PREFIX.getText().concat(ECMessages.CLEAR_ALL.getText()));
		return true;
	}

	private boolean commandHelp(final CommandSource source) {
		LinkedHashMap<String, ESubCommand> commands = new LinkedHashMap<String, ESubCommand>();
		if(source.hasPermission(ECPermissions.RELOAD.get())) {
			commands.put(this.getName() + " reload", new ESubCommand(this.helpReload(source), EAMessages.RELOAD_DESCRIPTION.getText()));
		}
		if(source.hasPermission(ECPermissions.ICON_COMMAND.get())) {
			commands.put(this.icons.getName(), new ESubCommand(this.icons.help(source), this.icons.description(source)));
		}
		if(source.hasPermission(ECPermissions.CLEAR.get())) {
			commands.put(this.getName() + " clear", new ESubCommand(this.helpClear(source), ECMessages.CLEAR_DESCRIPTION.getText()));
		}
		this.plugin.getEverAPI().getManagerService().getEPagination().helpSubCommand(commands, source, this.plugin);
		return true;
	}

	private boolean commandReload(CommandSource player) {
		this.plugin.reload();
		player.sendMessage(ECMessages.PREFIX.getText().concat(EAMessages.RELOAD_COMMAND.getText()));
		return true;
	}
}
