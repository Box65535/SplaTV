package command;

import discord.Messenger;
import org.apache.commons.io.IOUtils;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;

import java.io.IOException;
import java.nio.charset.Charset;

public class StreamsCommand implements Command {
    
    private Messenger messenger;
    private String clientId;
    
    public StreamsCommand(Messenger messenger, String clientId) {
        this.messenger = messenger;
        this.clientId = clientId;
    }

    @Override
    public boolean isCommand(MessageReceivedEvent event) {
        return Command.isFirstToken(event, "!streams");
    }

    @Override
    public void performCommand(MessageReceivedEvent event) {
        
        String command = new StringBuilder()
                .append("stuff")
                .append(clientId)
                .append("stuff")
                .toString();

        String response = null;
        try {
            Process process = Runtime.getRuntime().exec(command);
            response = IOUtils.toString(process.getInputStream(), Charset.defaultCharset());
        }
        catch (IOException e) {
            messenger.sendMessage(event.getMessage().getChannel(), "Could not run query");
            e.printStackTrace();
        }
        
        String message = null;
        messenger.sendMessage(event.getMessage().getChannel(), message);
    }
}
