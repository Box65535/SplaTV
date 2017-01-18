package bot;

import command.Command;
import command.StreamsCommand;
import command.UsageCommand;
import discord.Messenger;
import discord.UnsynchronizedMessenger;
import listen.CommandListener;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.util.HashSet;
import java.util.Set;

public class Main {
    
    public static void main(String[] args) {
        
        String token = args[0];
        String clientId = args[1];
        try {
            IDiscordClient client = new ClientBuilder().withToken(token).login();
            Messenger messenger = new UnsynchronizedMessenger(client);
            Set<Command> commands = new HashSet<>();
            commands.add(new UsageCommand(messenger));
            commands.add(new StreamsCommand(messenger, clientId));
            CommandListener listener = new CommandListener(commands);
            client.getDispatcher().registerListener(listener);
        }
        catch (DiscordException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
