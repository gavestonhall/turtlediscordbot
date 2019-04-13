package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ServerInfo extends Command {

    public ServerInfo() {
        this.name = "serverinfo";
        this.aliases = new String[] {"server"};
        this.category = new Category("Utility");
        this.guildOnly = true;
        this.help = "Gives you information about the current server.";
    }

    @Override
    protected void execute(CommandEvent e) {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.RED);
        eb.setAuthor(e.getGuild().getName());
        eb.setThumbnail(e.getGuild().getIconUrl());
        eb.addField("Server Owner: ", e.getGuild().getOwner().getEffectiveName(), true);
        eb.addField("Member Count: ", Integer.toString(e.getGuild().getMembers().size()), true);
        eb.addField("Roles:", getRolesAsString(e.getGuild().getRoles()), true);

        e.reply(eb.build());
    }

    private String getRolesAsString(List rolesList) {
        String roles = "";
        if (!rolesList.isEmpty()) {
            Role firstRole = (Role) rolesList.get(0);
            roles = firstRole.getName();

            for (int i = 1;  i < rolesList.size(); i++) {
                Role tempRole = (Role) rolesList.get(i);
                roles = roles + ", " + tempRole.getName();
            }
        } else {
            roles = "None";
        }
        return roles;
    }
}
