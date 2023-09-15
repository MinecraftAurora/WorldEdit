/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.sk89q.worldedit.forge;

import com.github.minecraftaurora.auroralib.server.permission.AuroraPermissions;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.server.permission.PermissionAPI;
import java.util.HashMap;

public interface ForgePermissionsProvider {

    boolean hasPermission(ServerPlayerEntity player, String permission);

    void registerPermission(String permission);

    class VanillaPermissionsProvider implements ForgePermissionsProvider {

        private final ForgePlatform platform;
        private static final HashMap<String, String> PERM_MAPPING = new HashMap<>();

        static {
            PERM_MAPPING.put("", "");
        }

        public VanillaPermissionsProvider(ForgePlatform platform) {
            this.platform = platform;
        }

        @Override
        public boolean hasPermission(ServerPlayerEntity player, String permission) {
            ForgeConfiguration configuration = platform.getConfiguration();
            final mappedPerm = PERM_MAPPING.contains(permission) ? PERM_MAPPING.get(permission) : permission;
            return configuration.cheatMode
                || ServerLifecycleHooks.getCurrentServer().getPlayerList().canSendCommands(player.getGameProfile())
                || AuroraPermissions.profileHasPermission(player.getGameProfile(), mappedPerm)
                || (configuration.creativeEnable && player.interactionManager.isCreative());
        }

        @Override
        public void registerPermission(String permission) {
        }
    }

    // TODO Re-add when Sponge for 1.14 is out
    //    class SpongePermissionsProvider implements ForgePermissionsProvider {
    //
    //        @Override
    //        public boolean hasPermission(EntityPlayerMP player, String permission) {
    //            return ((Player) player).hasPermission(permission);
    //        }
    //
    //        @Override
    //        public void registerPermission(ICommand command, String permission) {
    //
    //        }
    //    }
}
