package commands;

import main.MainBot;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Invite extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        String invite = event.getChannel().createInvite().complete().getURL();

        if (args[0].equalsIgnoreCase(MainBot.client.getPrefix() + "invite") && args.length == 1) {
            event.getChannel().sendMessage("Invite generated: " + invite).queue();
        }
    }
}
