package grafics.board;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.json.simple.parser.ParseException;

import mainPrograms.*;

public class input 
{
    private int host;
    private int online; 
    private int timeLimit; 
    private String name; 
    private String ip;
    private String game;
    private String path = System.getProperty("user.dir");
    private String time; 
    private String increment;
    private String colour;

    public input(int host, int online, int timeLimit, String name, String ip, String time, String increment, String colour)
    {
        this.host = host;
        this.online = online;
        this.timeLimit = timeLimit;
        this.name = name; 
        this.ip = ip;
        this.time = time;
        this.colour = colour;
        if(timeLimit == 2)
        {
            this.time = "0";
        }
        this.increment = increment;
    }

    public boolean checkSettings()
    {
        if(online == 2)
        {
            if(timeLimit != 0)
            {
                return true;
            }
        }
        else if(online == 1)
        {
            if(host == 2)
            {
                try 
                {
                    InetAddress ip1 = InetAddress.getByName(ip);
                } 
                catch (UnknownHostException e) 
                {
                    return false;
                }
                for(int i = 0; i < name.length(); i++)
                {
                    char c = name.charAt(i);
                    if(Character.isAlphabetic(c))
                    {
                        return true;
                    }
                }
            }
            else if(host == 1)
            {
                if(timeLimit == 0)
                {
                    return false;
                }
                for(int i = 0; i < name.length(); i++)
                {
                    char c = name.charAt(i);
                    if(Character.isAlphabetic(c))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String safeSettings()
    {
        try 
        {
            json.createJSON(path);
            int i = 1;
            while(true)
            {
                if(json.get_JSONObject(path, "game" + i) == null)
                {
                    game = "game" + i;
                    break;
                }
                i++;
            }

            if(online == 2)
            {
                if(timeLimit == 2)
                {
                    String[][] details = {{"online", "false"}, {"time", "0"}, {"increment", "0"}, {"status", "null"}, {"lastMove", "0,0"}, {"conversion", "false"}};
                    json.add_JSONObject(path, details, game);
                }
                else
                {
                    String[][] details = {{"online", "false"}, {"time", time}, {"increment", increment}, {"status", "null"}, {"lastMove", "0,0"}, {"conversion", "false"}};
                    json.add_JSONObject(path, details, game);
                }
            }
            else
            {
                if(host == 1)
                {
                    if(colour.equals("random"))
                    {
                        int max = 3, min = 1;
                        double r = Math.random();
		                int random = (int)(r * (max - min)) + min;
                        if(random == 1)
                        {
                            colour = "white";
                        }
                        else 
                        {
                            colour = "black";
                        }
                    }
                    String[][] details = {{"online", "true"}, {"time", time}, {"increment", increment}, {"host", "true"}, {"playerName", name}, {"IP", ip}, {"colour", colour}, {"opponentName", "undifined"}, {"status", "null"}, {"lastMove", "0,0"}, {"conversion", "false"}};
                    json.add_JSONObject(path, details, game);
                }
                else
                {
                    String[][] details = {{"online", "true"}, {"time", "undefined"}, {"increment", "undefined"}, {"host", "false"}, {"playerName", name}, {"IP", ip}, {"colour", "undifined"}, {"opponentName", "undifined"}, {"status", "null"}, {"lastMove", "0,0"}, {"conversion", "false"}};
                    json.add_JSONObject(path, details, game);
                }
            }
            return game;
        } 
        catch (IOException e) 
        { 
            e.printStackTrace();
            return null;
        } 
        catch (ParseException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
}

//Farben werden random bestimmt --> später eventuell mit Auswahl