package command;

import discord.Messenger;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;

import java.io.IOException;
import java.nio.charset.Charset;

import org.json.JSONObject;

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

        JSONObject response = getResponse(clientId);
        if (response == null) {
            messenger.sendMessage(event.getMessage().getChannel(), "Could not run query");
            return;
        }
        
        StringBuilder message = new StringBuilder();
        message.append(String.format("%1$20s", "Streamer"));
        message.append(String.format("%1$6s", "Viewers"));
        message.append(String.format("%1$44s", "Status"));
        message.append(String.format("%1$30s", "Link"));
        message.append("\n");
        
        JSONArray streams = response.getJSONArray("streams");
        for (int i = 0; i < streams.length(); i++) {
            JSONObject stream = streams.getJSONObject(i);
            JSONObject channel = stream.getJSONObject("channel");
            String streamer = channel.getString("display_name");
            String viewers = Integer.toString(stream.getInt("viewers"));
            String status = channel.getString("status");
            String link = channel.getString("url").replace("https://www.", "");

            message.append(String.format("%1$20s", streamer));
            message.append(String.format("%1$6s", viewers));
            message.append(String.format("%1$44s", status));
            message.append(String.format("%1$30s", link));
            message.append("\n");
        }
        
        messenger.sendQuoteMessage(event.getMessage().getChannel(), message.toString());
    }
    
    public static synchronized JSONObject getResponse(String clientId) {

        String command = new StringBuilder()
                .append("curl -H 'Accept: application/vnd.twitchtv.v5+json' -H 'Client-ID: ")
                .append(clientId)
                .append("' -X GET 'https://api.twitch.tv/kraken/search/streams?query=splatoon'")
                .toString();
        
        try {
            System.out.println(command);
            Process process = Runtime.getRuntime().exec(command);
            String stringResponse = IOUtils.toString(process.getInputStream(), Charset.defaultCharset());
            String errorResponse = IOUtils.toString(process.getErrorStream(), Charset.defaultCharset());
            System.out.println(stringResponse);
            System.out.println(errorResponse);
            return new JSONObject(stringResponse);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
