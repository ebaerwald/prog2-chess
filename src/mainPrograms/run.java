package mainPrograms;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import grafics.board.LaunchFrame;
import grafics.board.SpielBrettFrame;
import server_client.clientMain;
import server_client.serverMain;

public class run {
    private String path = System.getProperty("user.dir");
    private String game;
    
    private serverMain server;
    private clientMain client;

    private String name;
    private String colour;
    private String time;
    private String increment;
    private String opponentName;
    private String[][] board = {
        {"bR", "bN", "bB", "bQ", "bK", "bB", "bN", "bR"},
        {"bP", "bP", "bP", "bP", "bP", "bP", "bP", "bP"},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"wP", "wP", "wP", "wP", "wP", "wP", "wP", "wP"},
        {"wR", "wN", "wB", "wQ", "wK", "wB", "wN", "wR"}
    };

    public static void main(String[] args) throws Exception {
        LaunchFrame lFrame = new LaunchFrame();
    }
    public void secondMain(String game1) {
        game = game1;
        try {
            if (json.get_JSONDetail(path, game, "online").equals("true")) 
            {
                if (json.get_JSONDetail(path, game, "host").equals("true")) 
                {
                    server = new serverMain(9332);
                    name = json.get_JSONDetail(path, game, "playerName");
                    colour = json.get_JSONDetail(path, game, "colour");
                    time = json.get_JSONDetail(path, game, "time");
                    increment = json.get_JSONDetail(path, game, "increment");
                    server.send(name + "," + colour + "," + time + "," + increment);
                    opponentName = server.waiting();
                    json.add_JSONDetail(path, game, "opponentName", opponentName);
                    long seconds = System.currentTimeMillis() + 30;
                    server.send("" + seconds);
                    while (seconds != System.currentTimeMillis()) 
                    {
                        continue;
                    }
                    startGame();
                } else {
                    client = new clientMain(9332);
                    String settings = client.waiting();
                    String[] settings1 = settings.split(",");
                    json.add_JSONDetail(path, game, "opponentName", settings1[0]);
                    if (settings1[1].equals("white")) 
                    {
                        json.add_JSONDetail(path, game, "colour", "black");
                    } else 
                    {
                        json.add_JSONDetail(path, game, "colour", "white");
                    }
                    json.add_JSONDetail(path, game, "time", settings1[2]);
                    json.add_JSONDetail(path, game, "increment", settings1[3]);
                    name = json.get_JSONDetail(path, game, "playerName");
                    client.send(name);
                    colour = json.get_JSONDetail(path, game, colour);
                    time = settings1[2];
                    increment = settings1[3];
                    opponentName = settings1[0];
                    String seconds = client.waiting();
                    while (Long.parseLong(seconds) != System.currentTimeMillis()) 
                    {
                        continue;
                    }
                    startGame();
                }
            } 
            else 
            {
                startGame();
            }
        } 
        catch (IOException | ParseException e) 
        {
            e.printStackTrace();
        }
    }

    public void startGame() throws IOException, ParseException 
    {
        // SpielBrettFrame sFrame = new SpielBrettFrame(path, game); 
        // sFrame.run();
        SpielBrettFrame sFrame = new SpielBrettFrame(path, game, false);
        sFrame.run();
    }

    public boolean legalMove(int[] m1) throws IOException, ParseException
    {
        int[] startPosition = {m1[0], m1[1]};
        int[] targetPosition = {m1[2], m1[3]};
        int[] lastMove = new int[2];
        String lMove = json.get_JSONDetail(path, game, "lastMove");
        if(lMove.equals("null"))
        {
            lastMove[0] = 0;
            lastMove[1] = 0;
        }
        else
        {
            String[] l1 = lMove.split(",");
            lastMove[0] = Integer.parseInt(l1[0]);
            lastMove[1] = Integer.parseInt(l1[1]);
        }
        move m = new move(path, game, board, startPosition, targetPosition, lastMove);
        String[][] b = m.run();
        if(b == null)
        {
            return false;
        }
        else
        {
            board = b;
            if(json.get_JSONDetail(path, game, "host").equals("true"))
            {
                server.send(Integer.toString(m1[0]) + "," + Integer.toString(m1[1]) + "," + Integer.toString(m1[2]) + "," + Integer.toString(m1[3]));
            }
            else
            {
                client.send(Integer.toString(m1[0]) + "," + Integer.toString(m1[1]) + "," + Integer.toString(m1[2]) + "," + Integer.toString(m1[3]));
            }
            return true;
        }
    }
}

