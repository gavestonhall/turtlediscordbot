package main;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;

import commands.*;
import events.HelloEvent;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class MainBot extends ListenerAdapter {
    public static CommandClient client;

    public static void main(String[] args) throws LoginException {
        // Start up the bot
        JDA jda = new JDABuilder(AccountType.BOT).setToken("NTY0OTk4NjcxNDAyNDAxNzkz.XKwCPA.BP35Ey6tH7lIKxWZzzXpB7vxzFM").build();

        // What properties to build the client with
        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setOwnerId("564998671402401793");
        builder.setPrefix(";");
        builder.setAlternativePrefix("$");
        builder.setHelpWord("help");


        // Commands
        builder.addCommand(new ServerInfo());
        builder.addCommand(new UserInfo());
        builder.addCommand(new Image());
        builder.addCommand(new Add());
        builder.addCommand(new Sub());
        builder.addCommand(new Mult());
        builder.addCommand(new Div());


        // Build the client
        client = builder.build();

        // Register events
        jda.addEventListener(new HelloEvent());
        jda.addEventListener(new Invite());
        jda.addEventListener(client);
    }
}
