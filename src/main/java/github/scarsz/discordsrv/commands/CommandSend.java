/*
 * DiscordSRV - A Minecraft to Discord and back link plugin
 * Copyright (C) 2016-2019 Austin "Scarsz" Shapiro
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

package github.scarsz.discordsrv.commands;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.util.DiscordUtil;
import github.scarsz.discordsrv.util.LangUtil;
import me.vankka.reserializer.discord.DiscordSerializer;
import net.dv8tion.jda.core.entities.TextChannel;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommandSend {

    @Command(commandNames = { "send" },
            helpMessage = "Broadcasts a message to the main text channel on Discord",
            permission = "discordsrv.bcast",
            usageExample = "send General Hello from the server!"
    )
    public static void execute(CommandSender sender, String[] arg) {
        List<String> args = new LinkedList<>(Arrays.asList(arg));
        if (args.size() < 2) {
            sender.sendMessage(ChatColor.RED + LangUtil.InternalMessage.NO_MESSAGE_GIVEN_TO_BROADCAST.toString());
            return;
        }

        TextChannel channel = DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName(args.remove(0));
        if (channel == null) {
            sender.sendMessage(ChatColor.RED + "Channel not found yo.");
            return;
        }

        String rawMessage = String.join(" ", args);
        if (DiscordSRV.config().getBoolean("Experiment_MCDiscordReserializer")) {
            DiscordUtil.sendMessage(channel, DiscordSerializer.serialize(LegacyComponentSerializer.INSTANCE.deserialize(rawMessage)));
        } else {
            DiscordUtil.sendMessage(channel, rawMessage);
        }
    }

}
