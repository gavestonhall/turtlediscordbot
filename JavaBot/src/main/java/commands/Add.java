package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import main.MainBot;

public class Add extends Command {

    public Add() {
        this.name = "add";
        this.aliases = new String[] {"addition"};
        this.arguments = "[number1] [number2]";
        this.category = new Category("Arithmetic");
        this.guildOnly = false;
        this.help = "Adds two numbers together and evaluates the result.";
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getArgs().split(" ");

        if (args.length == 2) {
            try {
                double number1 = Double.parseDouble(args[0]);
                double number2 = Double.parseDouble(args[1]);
                String answer = Double.toString(number1 + number2);
                e.reply("Result: " + answer);
            } catch (NumberFormatException ex) {
                e.reply("Make sure that you enter real numbers not words.");
            }

        } else {
            e.reply("`Usage: " + MainBot.client.getPrefix() + name + " " + arguments + "`");
        }
    }
}
