package com.github.johnnyjayjay.discord.commandapi;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.*;


class CommandListener extends ListenerAdapter {

    private CommandSettings settings;
    private Map<Long, Set<Pair<ICommand, Long>>> cooldowns; // Long: User id, <Long: last timestamp, ICommand: command>

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

                CommandEvent.Command cmd = CommandEvent.parseCommand(raw, prefix, settings);
                if (cmd.getExecutor() != null) {
                    if(cooldowns.containsKey(userId)) {
                        Set<Pair<ICommand, Long>> set = cooldowns.get(userId);
                        Optional<Pair<ICommand, Long>> pairOptional = set.stream().filter(pair -> pair.getLeft() == cmd.getExecutor()).findAny();
                        if(pairOptional.isPresent() && (timestamp - pairOptional.get().getRight()) < settings.getCooldown(cmd.getExecutor())) {
                            if (settings.isResetCooldown()) {
                                Pair<ICommand, Long> pair = pairOptional.get();
                                set.remove(pair);
                                set.add(constructPair(cmd.getExecutor(), timestamp));
                            }
                            return;
                        }

                        set.add(constructPair(cmd.getExecutor(), timestamp));
                    } else {
                        Set<Pair<ICommand, Long>> set = new HashSet<>();
                        set.add(constructPair(cmd.getExecutor(), timestamp));
                        cooldowns.put(userId, set);
                    }

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

    private Pair<ICommand, Long> constructPair(ICommand left, Long right) {
        return new Pair<ICommand, Long>() {
            @Override
            public ICommand getLeft() {
                return left;
            }

            @Override
            public Long getRight() {
                return right;
            }
        };
    }
}
