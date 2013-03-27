/*
 * Statistics
 * Copyright (C) 2013 bitWolfy <http://www.wolvencraft.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.wolvencraft.yasp.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.wolvencraft.yasp.AsyncDataCollector;
import com.wolvencraft.yasp.StatsPlugin;
import com.wolvencraft.yasp.util.Util;

/**
 * Listens to any block changes on the server and reports them to the plugin.
 * @author bitWolfy
 *
 */
public class BlockListener implements Listener {
	
	/**
	 * <b>Default constructor</b><br />
	 * Creates a new instance of the Listener and registers it with the PluginManager
	 * @param plugin StatsPlugin instance
	 */
	public BlockListener(StatsPlugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		if(StatsPlugin.getPaused()) return;
		Player player = event.getPlayer();
		if(Util.isExempt(player, "block.break")) return;
		AsyncDataCollector
			.get(player)
			.blocks()
			.blockBreak(event.getBlock().getLocation(), event.getBlock().getType(), event.getBlock().getData());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if(StatsPlugin.getPaused()) return;
		Player player = event.getPlayer();
		if(Util.isExempt(player, "block.place")) return;
		AsyncDataCollector
			.get(player)
			.blocks()
			.blockPlace(event.getBlock().getLocation(), event.getBlock().getType(), event.getBlock().getData());
	}
}
