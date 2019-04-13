package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import main.MainBot;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Image extends Command {

    public Image() {
        this.name = "image";
        this.aliases = new String[] {"img"};
        this.arguments = "[word1] [...] [imageCount]";
        this.guildOnly = false;
        this.category = new Category("Utility");
        this.help = "Get's an image from Google based upon your query";

    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getArgs().split(" ");
        // Reset it each command
        int IMAGE_CHOICE = 1;
        int argCount = args.length;

        if (args[0].equalsIgnoreCase("")) {
            e.reply("`Usage: " + MainBot.client.getPrefix() + name + " " + arguments + "`");
        } else if (!args[0].equalsIgnoreCase("") && args.length == 1) {
            // Google Images Search Query
            String URL = String.format("https://www.google.co.uk/search?tbm=isch&source=hp&biw=1920&bih=944&ei=0litX" +
                    "Pe6EIyNlwTZ4rLQDg&q=%s&oq=%s&gs_l=img.3..0l10.997.1587..1784...0.0..1.71.504.9......" +
                    "1....1..gws-wiz-img.....0.JpSdf1zy9sQ", args[0], args[0]);

            // List to store the links gathered
            List<String> googleImageList = new ArrayList<>();
            getGoogleImages(URL, googleImageList, e);

            for (int i = 0; i < IMAGE_CHOICE; i++) {
                e.reply(googleImageList.get(i));
            }



        } else if (args.length > 1) {

            // Choose amount of images to appear
            try {
                int argImageChoice = Integer.parseInt(args[argCount-1]);
                if (argImageChoice > 1 && argImageChoice <= 5) {
                    IMAGE_CHOICE = argImageChoice;
                }
            } catch (NumberFormatException ex) {}

            // Google Images Search Query
            String query = "";

            // Query in form of word+word2+word3
            query = args[0];
            for (int i = 1; i < args.length - 1; i++) {
                query = String.format(query + "%s", "+" + args[i]);
            }

            String URL = String.format("https://www.google.co.uk/search?tbm=isch&source=hp&biw=1920&bih=944&ei=0litX" +
                    "Pe6EIyNlwTZ4rLQDg&q=%s&oq=%s&gs_l=img.3..0l10.997.1587..1784...0.0..1.71.504.9......" +
                    "1....1..gws-wiz-img.....0.JpSdf1zy9sQ", query, query);

            // List to store the links gathered
            List<String> googleImageList = new ArrayList<>();
            getGoogleImages(URL, googleImageList, e);

            for (int i = 0; i < IMAGE_CHOICE; i++) {
                e.reply(googleImageList.get(i));
            }
        }
    }

    private void getGoogleImages(String URL, List<String> googleImageList, CommandEvent e) {
        try {
            // Get HTML code from URL
            Document doc = Jsoup.connect(URL).get();
            // Specifically the img tags
            Elements links = doc.select("img");

            // Gets everything enclosed in quotations
            Pattern imageLink = Pattern.compile("([\"'])(?:(?=(\\\\?))\\2.)*?\\1");
            Matcher m;

            // Goes through all the links gathered
            for (Element link : links) {
                String strRepLink = link.toString();
                // Checks if it contains this type of link
                if (strRepLink.contains("https://encrypted-tbn0.gstatic.com/images")) {
                    m = imageLink.matcher(strRepLink);

                    if (m.find()) {
                        // If it's found remove quotes and add to list, ready to be sent
                        String matchedLink = m.group(0);
                        matchedLink = matchedLink.replace("\"", "");
                        googleImageList.add(matchedLink);
                    }

                }
            }

        } catch (IOException ex) {
            e.reply("Exception getting web document: " + ex.getMessage());
        }
    }
}
