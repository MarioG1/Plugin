/*
 * PlayerDetailedStats.java
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

package com.mctrakr.modules.stats.player;

import lombok.AccessLevel;
import lombok.Getter;

import org.bukkit.Location;

import com.mctrakr.database.Query;
import com.mctrakr.modules.DataStore.DetailedData;
import com.mctrakr.modules.stats.player.Tables.PlayersLogTable;
import com.mctrakr.session.OnlineSession;
import com.mctrakr.util.Util;

/**
 * An immutable player login / logout entry
 * @author bitWolfy
 *
 */
@Getter(AccessLevel.PUBLIC)
public class PlayerDetailedStats {
    
    @Getter(AccessLevel.PUBLIC)
    public static class PlayerLogin extends DetailedData {
        
        private final long time;
        private final boolean isLogin;
        private final Location location;
        
        public PlayerLogin(OnlineSession session, Location location, boolean isLogin) {
            super(session);
            time = Util.getTimestamp();
            this.isLogin = isLogin;
            this.location = location.clone();
        }
         
        @Override
        public boolean pushData() {
            return Query.table(PlayersLogTable.TableName)
                    .value(PlayersLogTable.PlayerId, session.getId())
                    .value(PlayersLogTable.Timestamp, time)
                    .value(PlayersLogTable.IsLogin, isLogin)
                    .value(PlayersLogTable.World, location.getWorld().getName())
                    .value(PlayersLogTable.XCoord, location.getBlockX())
                    .value(PlayersLogTable.YCoord, location.getBlockY())
                    .value(PlayersLogTable.ZCoord, location.getBlockZ())
                    .insert();
        }
     
    }
 
}