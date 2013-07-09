/*
 * VanishPlayerStats.java
 * 
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

package com.mctrakr.db.hooks.vanish;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.mctrakr.db.Query;
import com.mctrakr.db.data.NormalData;
import com.mctrakr.db.data.DataStore.HookType;
import com.mctrakr.db.hooks.vanish.Tables.VanishTable;
import com.mctrakr.managers.HookManager;

public class VanishPlayerStats extends NormalData {
    
    private String playerName;
    
    public VanishPlayerStats (Player player, int playerId) {
        this.playerName = player.getName();
        
        fetchData(playerId);
    }
    
    @Override
    public void fetchData(int playerId) {
        if(Query.table(VanishTable.TableName)
                .condition(VanishTable.PlayerId, playerId)
                .exists()) return;
        
        Player player = Bukkit.getPlayerExact(playerName);
        if(player == null) return;
        
        VanishHook hook = (VanishHook) HookManager.getHook(HookType.Vanish);
        if(hook == null) return;
        
        Query.table(VanishTable.TableName)
            .value(VanishTable.PlayerId, playerId)
            .value(VanishTable.IsVanished, hook.isVanished(player))
            .insert();
    }

    @Override
    public boolean pushData(int playerId) {
        Player player = Bukkit.getPlayerExact(playerName);
        if(player == null) return false;
        
        VanishHook hook = (VanishHook) HookManager.getHook(HookType.Vanish);
        if(hook == null) return false;
        
        return Query.table(VanishTable.TableName)
            .value(VanishTable.IsVanished, hook.isVanished(player))
            .condition(VanishTable.PlayerId, playerId)
            .update();
    }
    
}