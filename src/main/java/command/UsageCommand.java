package command;

import discord.Messenger;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;

public class UsageCommand implements Command {
    
    private Messenger messenger;
    
    public UsageCommand(Messenger messenger) {
        this.messenger = messenger;
    }
    
    @Override
    public boolean isCommand(MessageReceivedEvent event) {
        return Command.isFirstToken(event, "!splatv");
    }

    @Override
    public void performCommand(MessageReceivedEvent event) {
        
        String message = new StringBuilder()
                .append("SplaTV Bot Usage:\n")
                .append("  !streams               Show list of Splatoon streamers\n")
                .toString();
        
        messenger.sendMessage(event.getMessage().getChannel(), message);
    }
}
