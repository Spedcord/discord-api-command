package com.github.johnnyjayjay.discord.commandapi;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.hooks.IEventManager;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.managers.DirectAudioController;
import net.dv8tion.jda.api.managers.Presence;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.GuildAction;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheView;
import net.dv8tion.jda.api.utils.cache.SnowflakeCacheView;
import okhttp3.OkHttpClient;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Johnny_JayJay
 * @version 0.1-SNAPSHOT
 */
public class JDAMock implements JDA {

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public long getGatewayPing() {
        return 0;
    }

    @Nonnull
    @Override
    public JDA awaitStatus(@Nonnull Status status, @Nonnull Status... statuses) throws InterruptedException {
        return null;
    }

    @Nonnull
    @Override
    public ScheduledExecutorService getRateLimitPool() {
        return null;
    }

    @Nonnull
    @Override
    public ScheduledExecutorService getGatewayPool() {
        return null;
    }

    @Nonnull
    @Override
    public ExecutorService getCallbackPool() {
        return null;
    }

    @Nonnull
    @Override
    public OkHttpClient getHttpClient() {
        return null;
    }

    @Nonnull
    @Override
    public DirectAudioController getDirectAudioController() {
        return null;
    }

    @Override
    public void setEventManager(IEventManager iEventManager) {

    }

    @Override
    public void addEventListener(Object... objects) {

    }

    @Override
    public void removeEventListener(Object... objects) {

    }

    @Override
    public List<Object> getRegisteredListeners() {
        return null;
    }

    @Override
    public GuildAction createGuild(String s) {
        return null;
    }

    @Override
    public CacheView<AudioManager> getAudioManagerCache() {
        return null;
    }

    @Override
    public SnowflakeCacheView<User> getUserCache() {
        return null;
    }

    @Override
    public List<Guild> getMutualGuilds(User... users) {
        return null;
    }

    @Override
    public List<Guild> getMutualGuilds(Collection<User> collection) {
        return null;
    }

    @Override
    public RestAction<User> retrieveUserById(String s) {
        return null;
    }

    @Override
    public RestAction<User> retrieveUserById(long l) {
        return null;
    }

    @Override
    public SnowflakeCacheView<Guild> getGuildCache() {
        return null;
    }

    @Nonnull
    @Override
    public Set<String> getUnavailableGuilds() {
        return null;
    }

    @Override
    public boolean isUnavailable(long l) {
        return false;
    }

    @Override
    public SnowflakeCacheView<Role> getRoleCache() {
        return null;
    }

    @Override
    public SnowflakeCacheView<Category> getCategoryCache() {
        return null;
    }

    @Nonnull
    @Override
    public SnowflakeCacheView<StoreChannel> getStoreChannelCache() {
        return null;
    }

    @Override
    public SnowflakeCacheView<TextChannel> getTextChannelCache() {
        return null;
    }

    @Override
    public SnowflakeCacheView<VoiceChannel> getVoiceChannelCache() {
        return null;
    }

    @Override
    public SnowflakeCacheView<PrivateChannel> getPrivateChannelCache() {
        return null;
    }

    @Override
    public SnowflakeCacheView<Emote> getEmoteCache() {
        return null;
    }

    @Nonnull
    @Override
    public IEventManager getEventManager() {
        return null;
    }

    @Override
    public SelfUser getSelfUser() {
        return null;
    }

    @Override
    public Presence getPresence() {
        return null;
    }

    @Override
    public ShardInfo getShardInfo() {
        return null;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public long getResponseTotal() {
        return 0;
    }

    @Override
    public int getMaxReconnectDelay() {
        return 0;
    }

    @Override
    public void setAutoReconnect(boolean b) {

    }

    @Override
    public void setRequestTimeoutRetry(boolean b) {

    }

    @Override
    public boolean isAutoReconnect() {
        return false;
    }

    @Override
    public boolean isBulkDeleteSplittingEnabled() {
        return false;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void shutdownNow() {

    }

    @Override
    public AccountType getAccountType() {
        return null;
    }

    @Nonnull
    @Override
    public RestAction<ApplicationInfo> retrieveApplicationInfo() {
        return null;
    }

    @Nonnull
    @Override
    public String getInviteUrl(@Nullable Permission... permissions) {
        return null;
    }

    @Nonnull
    @Override
    public String getInviteUrl(@Nullable Collection<Permission> collection) {
        return null;
    }

    @Nullable
    @Override
    public ShardManager getShardManager() {
        return null;
    }

    @Nonnull
    @Override
    public RestAction<Webhook> retrieveWebhookById(@Nonnull String s) {
        return null;
    }
}
