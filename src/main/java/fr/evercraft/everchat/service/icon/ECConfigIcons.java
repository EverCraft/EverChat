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
package fr.evercraft.everchat.service.icon;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ninja.leaping.configurate.ConfigurationNode;
import fr.evercraft.everapi.plugin.file.EConfig;
import fr.evercraft.everchat.EverChat;

public class ECConfigIcons extends EConfig {
	
	private static final int CHARACTER = 36864;

	public ECConfigIcons(final EverChat plugin) {
		super(plugin);
	}
	
	@Override
	public void loadDefault() {		
		if(this.getNode().getValue() == null) {
			addDefault("0", "WOODEN_SWORD");
			addDefault("1", "WOODEN_PICKAXE");
			addDefault("2", "WOODEN_SHOVEL");
			addDefault("3", "WOODEN_AXE");
			addDefault("4", "WOODEN_HOE");
			addDefault("5", "STONE_SWORD");
			addDefault("6", "STONE_PICKAXE");
			addDefault("7", "STONE_SHOVEL");
			addDefault("8", "STONE_AXE");
			addDefault("9", "STONE_HOE");
			addDefault("10", "GOLDEN_SWORD");
			addDefault("11", "GOLDEN_PICKAXE");
			addDefault("12", "GOLDEN_SHOVEL");
			addDefault("13", "GOLDEN_AXE");
			addDefault("14", "GOLDEN_HOE");
			addDefault("15", "IRON_SWORD");
			addDefault("16", "IRON_PICKAXE");
			addDefault("17", "IRON_SHOVEL");
			addDefault("18", "IRON_AXE");
			addDefault("19", "IRON_HOE");
			addDefault("20", "DIAMOND_SWORD");
			addDefault("21", "DIAMOND_PICKAXE");
			addDefault("22", "DIAMOND_SHOVEL");
			addDefault("23", "DIAMOND_AXE");
			addDefault("24", "DIAMOND_HOE");
			addDefault("25", "BOW");
			addDefault("26", "BOW_1");
			addDefault("27", "BOW_2");
			addDefault("28", "BOW_3");
			addDefault("29", "ARROW");
			addDefault("30", "QUIVER");
			addDefault("31", "HEAD");
			addDefault("32", "BODY");
			addDefault("33", "LEGS");
			addDefault("34", "BOOTS");
			addDefault("35", "WHITE_LEATHER_CAP");
			addDefault("36", "WHITE_LEATHER_TUNIC");
			addDefault("37", "WHITE_LEATHER_PANTS");
			addDefault("38", "WHITE_LEATHER_BOOTS");
			addDefault("39", "BLANK_LEATHER_CAP");
			addDefault("40", "BLANK_LEATHER_TUNIC");
			addDefault("41", "BLANK_LEATHER_PANTS");
			addDefault("42", "BLANK_LEATHER_BOOTS");
			addDefault("43", "LEATHER_CAP");
			addDefault("44", "LEATHER_TUNIC");
			addDefault("45", "LEATHER_PANTS");
			addDefault("46", "LEATHER_BOOTS");
			addDefault("47", "CHAIN_HELMET");
			addDefault("48", "CHAIN_CHESTPLATE");
			addDefault("49", "CHAIN_LEGGINGS");
			addDefault("50", "CHAIN_BOOTS");
			addDefault("51", "GOLDEN_HELMET");
			addDefault("52", "GOLDEN_CHESTPLATE");
			addDefault("53", "GOLDEN_LEGGINGS");
			addDefault("54", "GOLDEN_BOOTS");
			addDefault("55", "IRON_HELMET");
			addDefault("56", "IRON_CHESTPLATE");
			addDefault("57", "IRON_LEGGINGS");
			addDefault("58", "IRON_BOOTS");
			addDefault("59", "DIAMOND_HELMET");
			addDefault("60", "DIAMOND_CHESTPLATE");
			addDefault("61", "DIAMOND_LEGGINGS");
			addDefault("62", "DIAMOND_BOOTS");
			addDefault("63", "COMPASS_SOUTH");
			addDefault("64", "COMPASS_SOUTHWEST");
			addDefault("65", "COMPASS_WEST");
			addDefault("66", "COMPASS_NORTHWEST");
			addDefault("67", "COMPASS_NORTH");
			addDefault("68", "COMPASS_NORTHEAST");
			addDefault("69", "COMPASS_EAST");
			addDefault("70", "COMPASS_SOUTHEAST");
			addDefault("71", "CLOCK_NOON");
			addDefault("72", "CLOCK_EVENING");
			addDefault("73", "CLOCK_DUSK");
			addDefault("74", "CLOCK_NIGHT");
			addDefault("75", "CLOCK_MIDNIGHT");
			addDefault("76", "CLOCK_WEE_HOURS");
			addDefault("77", "CLOCK_DAWN");
			addDefault("78", "CLOCK_MORNING");
			addDefault("79", "FISHING_ROD");
			addDefault("80", "FISHING_ROD_CAST");
			addDefault("81", "CARROT_ON_A_STICK");
			addDefault("82", "FLINT_AND_STEEL");
			addDefault("83", "SHEARS");
			addDefault("84", "LEAD");
			addDefault("85", "NAME_TAG");
			addDefault("86", "IRON_HORSE_ARMOR");
			addDefault("87", "GOLD_HORSE_ARMOR");
			addDefault("88", "DIAMOND_HORSE_ARMOR");
			addDefault("89", "SADDLE");
			addDefault("90", "MINECART");
			addDefault("91", "MINECART_WITH_CHEST");
			addDefault("92", "MINECART_WITH_FURNACE");
			addDefault("93", "MINECART_WITH_HOPPER");
			addDefault("94", "MINECART_WITH_TNT");
			addDefault("95", "BOAT");
			addDefault("96", "BOOK");
			addDefault("97", "BOOK_AND_QUILL");
			addDefault("98", "WRITTEN_BOOK");
			addDefault("99", "ENCHANTED_BOOK");
			addDefault("100", "MAP");
			addDefault("101", "EMPTY_MAP");
			addDefault("102", "BUCKET");
			addDefault("103", "WATER_BUCKET");
			addDefault("104", "LAVA_BUCKET");
			addDefault("105", "MILK");
			addDefault("106", "SNOWBALL");
			addDefault("107", "EGG");
			addDefault("108", "ENDER_PEARL");
			addDefault("109", "EYE_OF_ENDER");
			addDefault("110", "FIRE_CHARGE");
			addDefault("111", "DISC_13");
			addDefault("112", "DISC_CAT");
			addDefault("113", "DISC_BLOCKS");
			addDefault("114", "DISC_CHIRP");
			addDefault("115", "DISC_FAR");
			addDefault("116", "DISC_MALL");
			addDefault("117", "DISC_MELLOHI");
			addDefault("118", "DISC_STAL");
			addDefault("119", "DISC_STRAD");
			addDefault("120", "DISC_WARD");
			addDefault("121", "DISC_11");
			addDefault("122", "DISC_WAIT");
			addDefault("123", "BOTTLE_O_ENCHANTING");
			addDefault("124", "FIREWORK");
			addDefault("125", "FIREWORK_STAR_BLANK");
			addDefault("126", "FIREWORK_STAR_SPOTS");
			addDefault("127", "FIREWORK_STAR_WHITE");
			addDefault("128", "FIREWORK_STAR_ORANGE");
			addDefault("129", "FIREWORK_STAR_MAGENTA");
			addDefault("130", "FIREWORK_STAR_LIGHT_BLUE");
			addDefault("131", "FIREWORK_STAR_YELLOW");
			addDefault("132", "FIREWORK_STAR_LIME");
			addDefault("133", "FIREWORK_STAR_PINK");
			addDefault("134", "FIREWORK_STAR_GRAY");
			addDefault("135", "FIREWORK_STAR_LIGHT_GRAY");
			addDefault("136", "FIREWORK_STAR_CYAN");
			addDefault("137", "FIREWORK_STAR_PURPLE");
			addDefault("138", "FIREWORK_STAR_BLUE");
			addDefault("139", "FIREWORK_STAR_BROWN");
			addDefault("140", "FIREWORK_STAR_GREEN");
			addDefault("141", "FIREWORK_STAR_RED");
			addDefault("142", "FIREWORK_STAR_BLACK");
			addDefault("143", "BONEMEAL");
			addDefault("144", "ORANGE_DYE");
			addDefault("145", "MAGENTA_DYE");
			addDefault("146", "LIGHT_BLUE_DYE");
			addDefault("147", "DANDELION_YELLOW");
			addDefault("148", "LIME_DYE");
			addDefault("149", "PINK_DYE");
			addDefault("150", "GRAY_DYE");
			addDefault("151", "LIGHT_GRAY_DYE");
			addDefault("152", "CYAN_DYE");
			addDefault("153", "PURPLE_DYE");
			addDefault("154", "LAPIS_LAZULI");
			addDefault("155", "COCOA_BEANS");
			addDefault("156", "CACTUS_GREEN");
			addDefault("157", "ROSE_RED");
			addDefault("158", "INK_SAC");
			addDefault("159", "SIGN");
			addDefault("160", "PAINTING");
			addDefault("161", "ITEM_FRAME");
			addDefault("162", "FLOWER_POT");
			addDefault("163", "WOODEN_DOOR");
			addDefault("164", "IRON_DOOR");
			addDefault("165", "HOPPER");
			addDefault("166", "BREWING_STAND");
			addDefault("167", "CAULDRON");
			addDefault("168", "BED");
			addDefault("169", "REPEATER");
			addDefault("170", "COMPARATOR");
			addDefault("171", "REDSTONE_DUST");
			addDefault("172", "GLOWSTONE_DUST");
			addDefault("173", "GUNPOWDER");
			addDefault("174", "SUGAR");
			addDefault("175", "IRON_INGOT");
			addDefault("176", "GOLD_INGOT");
			addDefault("177", "BRICK");
			addDefault("178", "NETHER_BRICK");
			addDefault("179", "COAL");
			addDefault("180", "CHARCOAL");
			addDefault("181", "DIAMOND");
			addDefault("182", "RUBY");
			addDefault("183", "EMERALD");
			addDefault("184", "NETHER_QUARTZ");
			addDefault("185", "FLINT");
			addDefault("186", "GOLD_NUGGET");
			addDefault("187", "GHAST_TEAR");
			addDefault("188", "NETHER_STAR");
			addDefault("189", "CLAY_BALL");
			addDefault("190", "SLIMEBALL");
			addDefault("191", "SKELETON_SKULL");
			addDefault("192", "WITHER_SKELETON_SKULL");
			addDefault("193", "ZOMBIE_HEAD");
			addDefault("194", "HEAD_2");
			addDefault("195", "CREEPER_HEAD");
			addDefault("196", "STICK");
			addDefault("197", "BONE");
			addDefault("198", "BLAZE_ROD");
			addDefault("199", "BLAZE_POWDER");
		}
	}
	
	public Map<String, String> getAllIcons() {
		Map<String, String> replaces = new HashMap<String, String>();
		for(Entry<Object, ? extends ConfigurationNode> node : this.getNode().getChildrenMap().entrySet()) {
			if(node.getKey() instanceof Integer) {
				replaces.put((String) node.getValue().getString(""), String.valueOf((char)(CHARACTER + (int)node.getKey())));
			}
		}
		return replaces;
	}
}
