package events;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class HelloEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String messageSent = event.getMessage().getContentRaw();
        String userMention = event.getMessage().getMember().getAsMention();

        if (messageSent.equalsIgnoreCase("HELLO") && !(event.getMember().getUser().isBot()) ) {
            event.getChannel().sendMessage(String.format("Hello, %s!", userMention)).queue();
        }
    }
}
