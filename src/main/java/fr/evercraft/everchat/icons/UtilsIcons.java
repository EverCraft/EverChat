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
package fr.evercraft.everchat.icons;

import java.lang.reflect.Field;

public class UtilsIcons {
	public static final int CHARACTER = 58112;
	public static final int COUNT = 5631;
	
	// Fix : https://github.com/SpongePowered/SpongeAPI/issues/1512
	public static void init() throws Exception {
		Class<?> clazz = Class.forName("org.spongepowered.common.service.pagination.PaginationCalculator");
		Field field = clazz.getDeclaredField("UNICODE_CHAR_WIDTHS");
		field.setAccessible(true);
		Object object = field.get(null);
		if (object instanceof byte[]) {
			byte[] unicodeCharWidths = (byte[]) object;
			for (int cpt=CHARACTER; cpt < CHARACTER + COUNT; cpt++) {
				unicodeCharWidths[cpt] = 15;
			}
		}
	}
}
