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

import com.github.minecraftaurora.auroralib.common.permission.AuroraPermissions;
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
            PERM_MAPPING.put("worldedit.analysis.count", "command./count");
            PERM_MAPPING.put("worldedit.analysis.distr", "command./distr");
            PERM_MAPPING.put("worldedit.biome.info", "command.biomeinfo");
            PERM_MAPPING.put("worldedit.biome.list", "command.biomelist");
            PERM_MAPPING.put("worldedit.biome.set", "command./setbiome");
            PERM_MAPPING.put("worldedit.brush.options.mask", "command.mask");
            PERM_MAPPING.put("worldedit.brush.options.material", "command.material");
            PERM_MAPPING.put("worldedit.brush.options.range", "command.range");
            PERM_MAPPING.put("worldedit.brush.options.size", "command.size");
            PERM_MAPPING.put("worldedit.brush.options.tracemask", "command.tracemask");
            PERM_MAPPING.put("worldedit.brush.item", "command.brush");
            PERM_MAPPING.put("worldedit.brush.sphere", "command.brush");
            PERM_MAPPING.put("worldedit.brush.cylinder", "command.brush");
            PERM_MAPPING.put("worldedit.brush.clipboard", "command.brush");
            PERM_MAPPING.put("worldedit.brush.smooth", "command.brush");
            PERM_MAPPING.put("worldedit.brush.ex", "command.brush");
            PERM_MAPPING.put("worldedit.brush.gravity", "command.brush");
            PERM_MAPPING.put("worldedit.brush.butcher", "command.brush");
            PERM_MAPPING.put("worldedit.brush.heightmap", "command.brush");
            PERM_MAPPING.put("worldedit.brush.deform", "command.brush");
            PERM_MAPPING.put("worldedit.brush.set", "command.brush");
            PERM_MAPPING.put("worldedit.brush.forest", "command.brush");
            PERM_MAPPING.put("worldedit.brush.raise", "command.brush");
            PERM_MAPPING.put("worldedit.brush.lower", "command.brush");
            PERM_MAPPING.put("worldedit.brush.snow", "command.brush");
            PERM_MAPPING.put("worldedit.brush.biome", "command.brush");
            PERM_MAPPING.put("worldedit.brush", "command.brush");
            PERM_MAPPING.put("worldedit.butcher", "command.butcher");
            PERM_MAPPING.put("worldedit.calc", "command./calc");
            PERM_MAPPING.put("worldedit.chunkinfo", "command.chunkinfo");
            PERM_MAPPING.put("worldedit.clipboard.clear", "command.clearclipboard");
            PERM_MAPPING.put("worldedit.clipboard.copy", "command./copy");
            PERM_MAPPING.put("worldedit.clipboard.cut", "command./cut");
            PERM_MAPPING.put("worldedit.clipboard.flip", "command./flip");
            PERM_MAPPING.put("worldedit.clipboard.load", "command./schematic");
            PERM_MAPPING.put("worldedit.clipboard.paste", "command./paste");
            PERM_MAPPING.put("worldedit.clipboard.rotate", "command./rotate");
            PERM_MAPPING.put("worldedit.clipboard.save", "command./schematic");
            PERM_MAPPING.put("worldedit.delchunks", "command.delchunks");
            PERM_MAPPING.put("worldedit.drain", "command./drain");
            PERM_MAPPING.put("worldedit.drawsel", "command./drawsel");
            PERM_MAPPING.put("worldedit.extinguish", "command./extinguish");
            PERM_MAPPING.put("worldedit.fast", "command./fast");
            PERM_MAPPING.put("worldedit.fill", "command./fill");
            PERM_MAPPING.put("worldedit.fill.recursive", "command./fillr");
            PERM_MAPPING.put("worldedit.fixlava", "command./fixlava");
            PERM_MAPPING.put("worldedit.fixwater", "command./fixwater");
            PERM_MAPPING.put("worldedit.generation.cylinder", "command./cyl");
            PERM_MAPPING.put("worldedit.generation.forest", "command.forestgen");
            PERM_MAPPING.put("worldedit.generation.pumpkins", "command.pumpkins");
            PERM_MAPPING.put("worldedit.generation.pyramid", "command./pyramid");
            PERM_MAPPING.put("worldedit.generation.shape", "command./generate");
            PERM_MAPPING.put("worldedit.generation.shape.biome", "command./generatebiome");
            PERM_MAPPING.put("worldedit.generation.sphere", "command./sphere");
            PERM_MAPPING.put("worldedit.global-mask", "command./gmask");
            PERM_MAPPING.put("worldedit.green", "command./green");
            PERM_MAPPING.put("worldedit.help", "command./help");
            PERM_MAPPING.put("worldedit.history.clear", "command./clearhistory");
            PERM_MAPPING.put("worldedit.history.redo", "command./redo");
            PERM_MAPPING.put("worldedit.history.redo.self", "command./redo");
            PERM_MAPPING.put("worldedit.history.undo", "command./undo");
            PERM_MAPPING.put("worldedit.history.undo.self", "command./undo");
            PERM_MAPPING.put("worldedit.limit", "command./limit");
            PERM_MAPPING.put("worldedit.listchunks", "command.listchunks");
            PERM_MAPPING.put("worldedit.navigation.ascend", "command.ascend");
            PERM_MAPPING.put("worldedit.navigation.ceiling", "command.ceil");
            PERM_MAPPING.put("worldedit.navigation.descend", "command.descend");
            PERM_MAPPING.put("worldedit.navigation.jumpto.command", "command.jumpto");
            PERM_MAPPING.put("worldedit.navigation.thru.command", "command.thru");
            PERM_MAPPING.put("worldedit.navigation.unstuck", "command.unstuck");
            PERM_MAPPING.put("worldedit.navigation.up", "command.up");
            PERM_MAPPING.put("worldedit.perf", "command./perf");
            PERM_MAPPING.put("worldedit.regen", "command./regen");
            PERM_MAPPING.put("worldedit.region.center", "command./center");
            PERM_MAPPING.put("worldedit.region.curve", "command./curve");
            PERM_MAPPING.put("worldedit.region.deform", "command./deform");
            PERM_MAPPING.put("worldedit.region.faces", "command./faces");
            PERM_MAPPING.put("worldedit.region.flora", "command./flora");
            PERM_MAPPING.put("worldedit.region.forest", "command./forest");
            PERM_MAPPING.put("worldedit.region.hollow", "command./hollow");
            PERM_MAPPING.put("worldedit.region.line", "command./line");
            PERM_MAPPING.put("worldedit.region.move", "command./move");
            PERM_MAPPING.put("worldedit.region.naturalize", "command./naturalize");
            PERM_MAPPING.put("worldedit.region.overlay", "command./overlay");
            PERM_MAPPING.put("worldedit.region.replace", "command./replace");
            PERM_MAPPING.put("worldedit.region.set", "command./set");
            PERM_MAPPING.put("worldedit.region.smooth", "command./smooth");
            PERM_MAPPING.put("worldedit.region.stack", "command./stack");
            PERM_MAPPING.put("worldedit.region.walls", "command./walls");
            PERM_MAPPING.put("worldedit.remove", "command.remove");
            PERM_MAPPING.put("worldedit.removeabove", "command./removeabove");
            PERM_MAPPING.put("worldedit.removebelow", "command./removebelow");
            PERM_MAPPING.put("worldedit.removenear", "command./removenear");
            PERM_MAPPING.put("worldedit.reorder", "command./reorder");
            PERM_MAPPING.put("worldedit.replacenear", "command./replacenear");
            PERM_MAPPING.put("worldedit.schematic.delete", "command./schematic");
            PERM_MAPPING.put("worldedit.schematic.formats", "command./schematic");
            PERM_MAPPING.put("worldedit.schematic.list", "command./schematic");
            PERM_MAPPING.put("worldedit.schematic.load", "command./schematic");
            PERM_MAPPING.put("worldedit.schematic.save", "command./schematic");
            PERM_MAPPING.put("worldedit.scripting.execute", "command.cs");
            PERM_MAPPING.put("worldedit.searchitem", "command./searchitem");
            PERM_MAPPING.put("worldedit.selection.chunk", "command./chunk");
            PERM_MAPPING.put("worldedit.selection.contract", "command./contract");
            PERM_MAPPING.put("worldedit.selection.expand", "command./expand");
            PERM_MAPPING.put("worldedit.selection.hpos", "command./hpos1");
            PERM_MAPPING.put("worldedit.selection.inset", "command./inset");
            PERM_MAPPING.put("worldedit.selection.outset", "command./outset");
            PERM_MAPPING.put("worldedit.selection.pos", "command./pos1");
            PERM_MAPPING.put("worldedit.selection.shift", "command./shift");
            PERM_MAPPING.put("worldedit.selection.size", "command./size");
            PERM_MAPPING.put("worldedit.setwand", "command./selwand");
            PERM_MAPPING.put("worldedit.snapshots.list", "command.snapshot");
            PERM_MAPPING.put("worldedit.snapshots.restore", "command.snapshot");
            PERM_MAPPING.put("worldedit.snow", "command./snow");
            PERM_MAPPING.put("worldedit.superpickaxe", "command.superpickaxe");
            PERM_MAPPING.put("worldedit.superpickaxe.area", "command.superpickaxe");
            PERM_MAPPING.put("worldedit.superpickaxe.recursive", "command.superpickaxe");
            PERM_MAPPING.put("worldedit.thaw", "command./thaw");
            PERM_MAPPING.put("worldedit.timeout", "command./timeout");
            PERM_MAPPING.put("worldedit.tool.data-cycler", "command.tool");
            PERM_MAPPING.put("worldedit.tool.deltree", "command.tool");
            PERM_MAPPING.put("worldedit.tool.farwand", "command.tool");
            PERM_MAPPING.put("worldedit.tool.flood-fill", "command.tool");
            PERM_MAPPING.put("worldedit.tool.info", "command.tool");
            PERM_MAPPING.put("worldedit.tool.lrbuild", "command.tool");
            PERM_MAPPING.put("worldedit.tool.replacer", "command.tool");
            PERM_MAPPING.put("worldedit.tool.tree", "command.tool");
            PERM_MAPPING.put("worldedit.wand", "command./wand");
            PERM_MAPPING.put("worldedit.wand.toggle", "command.toggleeditwand");
            PERM_MAPPING.put("worldedit.watchdog", "command./watchdog");
            PERM_MAPPING.put("worldedit.world", "command./world");
        }

        public VanillaPermissionsProvider(ForgePlatform platform) {
            this.platform = platform;
        }

        @Override
        public boolean hasPermission(ServerPlayerEntity player, String permission) {
            ForgeConfiguration configuration = platform.getConfiguration();
            final String mappedPerm = PERM_MAPPING.getOrDefault(permission, permission);
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
