package com.github.johnnyjayjay.discord.commandapi;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;


class CommandListener extends ListenerAdapter {

    private CommandSettings settings;
    private Map<Long, Long> cooldowns; // Long: User id, Long: last timestamp

    public CommandListener(CommandSettings settings) {
        this.settings = settings;
        this.cooldowns = new HashMap<>();
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        handleCommand(event.getMessage(), event.getResponseNumber());
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        handleCommand(event.getMessage(), event.getResponseNumber());
    }

    private void handleCommand(Message message, long responseNumber) {
        MessageChannel channel = message.getChannel();
        if (!settings.getBlacklistedChannels().contains(channel.getIdLong()) && (!message.getAuthor().isBot() || settings.botsMayExecute())) {
            String raw = message.getContentRaw();
            String prefix = message.getChannel() instanceof TextChannel ? settings.getPrefix(message.getGuild().getIdLong()) : settings.getPrefix();
            if (raw.startsWith(prefix)) {
                long timestamp = System.currentTimeMillis();
                long userId = message.getAuthor().getIdLong();
                if (cooldowns.containsKey(userId) && (timestamp - cooldowns.get(userId)) < settings.getCooldown()) {
                    if (settings.isResetCooldown())
                        cooldowns.put(userId, timestamp);
                    return;
                }
                cooldowns.put(userId, timestamp);
                CommandEvent.Command cmd = CommandEvent.parseCommand(raw, prefix, settings);
                if (cmd.getExecutor() != null) {
                    try {
                        cmd.getExecutor().onCommand(new CommandEvent(message.getJDA(), responseNumber, message, cmd, settings),
                                message.getMember(), channel, cmd.getArgs());
                    } catch (Throwable t) {
                        CommandSettings.LOGGER.warn("One of the commands had an uncaught exception:", t);
                    }
                } else {
                    Message unknownCommand = settings.getUnknownCommandMessage();
                    if (unknownCommand != null && ((!(channel instanceof TextChannel)) || message.getGuild().getSelfMember().hasPermission((TextChannel) channel, Permission.MESSAGE_WRITE, Permission.MESSAGE_EMBED_LINKS)))
                        channel.sendMessage(unknownCommand).queue();
                }
            }
        }
    }
}
