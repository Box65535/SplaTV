package discord;

import java.io.File;
import java.util.EnumSet;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MessageBuilder;

public class UnsynchronizedMessenger implements Messenger {

    private static final EnumSet<Permissions> EMPTY_SET = EnumSet.noneOf(Permissions.class);

    private IDiscordClient client;

    public UnsynchronizedMessenger(IDiscordClient client) {
        this.client = client;
    }

    public boolean sendMessage(IChannel channel, String message) {
        try {
            channel.sendMessage(message);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendQuoteMessage(IChannel channel, String message) {
        try {
            new MessageBuilder(client).withChannel(channel).withQuote(message).send();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean uploadFile(IChannel channel, File file) {
        try {
            channel.sendFile(file);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public IChannel createChannel(IGuild guild, String channelName) {
        try {
            return guild.createChannel(channelName);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean removeChannel(IChannel channel) {
        try {
            channel.delete();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addChannelPermissions(IChannel channel, IRole role, EnumSet<Permissions> permissions) {
        try {
            channel.overrideRolePermissions(role, permissions, EMPTY_SET);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeChannelPermissions(IChannel channel, IRole role, EnumSet<Permissions> permissions) {
        try {
            channel.overrideRolePermissions(role, permissions, EMPTY_SET);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
