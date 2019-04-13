package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import main.MainBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

import java.awt.*;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInfo extends Command {

    public UserInfo() {
        this.name = "userinfo";
        this.aliases = new String[] {"user"};
        this.arguments = "[@user]";
        this.category = new Category("Utility");
        this.guildOnly = true;
        this.help = "Gives you information about the user.";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getArgs().split(" ");

        if (args.length == 1) {

            if (e.getMessage().getMentionedMembers().size() != 0) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();

                // Find the user given the mentioned users in the message sent
                Member member = e.getMessage().getMentionedMembers().get(0);
                String onlineStatus = member.getOnlineStatus().toString();
                String nickname = (member.getNickname() != null ? member.getNickname() : "None");
                String game = ((member.getGame() != null) ? member.getGame().getName() : "None");

                // Create embed of user information
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(Color.GREEN);
                embed.setTitle("Information: " + member.getUser().getName());
                embed.addField("Name:", member.getUser().getName(), true);
                embed.addField("Nickname:", nickname, true);
                embed.addField("Online Status:", onlineStatus, true);
                embed.addField("Roles:", getRolesAsString(member.getRoles()), true);
                embed.addField("Game:", game, true);

                embed.setThumbnail(member.getUser().getAvatarUrl());
                embed.setFooter("Request made at: " + formatter.format(date), e.getGuild().getIconUrl());

                e.reply(embed.build());

            } else {
                e.reply("Sorry, could not find a member.");
            }
        } else {
            e.reply("`Usage: " + MainBot.client.getPrefix() + name + " " + arguments + "`");
        }
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
