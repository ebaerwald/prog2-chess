package mainPrograms;

import java.io.IOException;
import org.json.simple.parser.ParseException;

public class move
{
    private String path;
    private String game;

    private String[][] board;
    private int[] startPosition;
    private int[] targetPosition;
    private String colour;
    private int[] lastMove = new int[2];
    private int[][] movement = new int[27][2];
    
    private int[][] kingMovement = {{1, 0}, {1, 1}, {1, -1}, {-1, 0}, {-1, 1}, {-1, -1}, {0, 1}, {0, -1}};
    private int[][] knightMovement = {{1, 2}, {1, -2}, {2, 1}, {2, -1}, {-1, 2}, {-1, -2}, {-2, 1}, {-2, -1}};

    public move(String path, String game, String[][] board, int[] startPosition, int[] targetPosition, int[] lastMove) throws IOException, ParseException
    {
        this.path = path;
        this.game = game;

        this.board = board;
        this.startPosition = startPosition;
        this.targetPosition = targetPosition;
        this.colour = json.get_JSONDetail(path, game, "colour");
        this.lastMove = lastMove;
    }

    public String[][] run() throws IOException, ParseException
    {
        if(!ownPiece(startPosition[0], startPosition[1]) || ownPiece(targetPosition[0], targetPosition[1]))
        {
            return null;
        }
        getMovement(startPosition[0], startPosition[1], board[startPosition[0]][startPosition[1]]);
        if(!legalMove(startPosition, targetPosition))
        {
            return null;
        }
        movePiece(startPosition, targetPosition);
        if(ownKingCheck())
        {
            return null;
        }
        moveBack(startPosition, targetPosition);
        finalMove(startPosition, targetPosition);
        changeColour();
        if(patt())
        {
            json.add_JSONDetail(path, game, "status", "patt");
        }
        if(mate())
        {
            json.add_JSONDetail(path, game, "status", "mate");
        }
        return board;
    }

    public boolean ownPiece(int position_y, int position_x)
    {
        if(colour.equals("white"))
        {
            String[] split = board[position_y][position_x].split("w");
            if(split[0].equals(" "))
            {
                return true;
            }
            return false;
        }
        else
        {
            String[] split = board[position_y][position_x].split("b");
            if(split[0].equals(" "))
            {
                return true;
            }
            return false;
        }
    }

    public void getMovement(int s1, int s2, String piece)
    {
        switch(piece)
        {
            case "wP":
                if(!OutOfBounds(s1 - 1, s2) && board[s1 - 1][s2].equals(" "))
                {
                    add(s1 - 1, s2);
                    if(!OutOfBounds(s1 - 2, s2) && board[s1 - 2][s2].equals(" ") && s1 == 6) 
                    {
                        add(s1 - 2, s2);
                    }
                }
                else if(!OutOfBounds(s1 - 1, s2 - 1) && !board[s1 - 1][s2 - 1].equals(" ") && !ownPiece(s1 - 1, s2 - 1) || OutOfBounds(s1 - 1, s2 - 1) && board[s1 - 1][s2 - 1].equals(" ") && lastMove[0] == 3 && board[lastMove[0]][lastMove[1]].equals("bP")) 
                {
                    add(s1 - 1, s2 - 1);
                }
                else if(!OutOfBounds(s1 - 1, s2 + 1) && !board[s1 - 1][s2 + 1].equals(" ") && !ownPiece(s1 - 1, s2 + 1) || !OutOfBounds(s1 - 1, s2 + 1) && board[s1 - 1][s2 + 1].equals(" ") && lastMove[0] == 3 && board[lastMove[0]][lastMove[1]].equals("bP"))
                {
                    add(s1 - 1, s2 + 1);
                }
            case "bP":
                if(!OutOfBounds(s1 + 1, s2) && board[s1 + 1][s2].equals(" "))
                {
                    add(s1 + 1, s2);
                    if(!OutOfBounds(s1 + 2, s2) && board[s1 + 2][s2].equals(" ") && s1 == 1) 
                    {
                        add(s1 + 2, s2);
                    }
                }
                else if(!OutOfBounds(s1 + 1, s2 - 1) && !board[s1 + 1][s2 - 1].equals(" ") && !ownPiece(s1 + 1, s2 - 1) || !OutOfBounds(s1 + 1, s2 - 1) && board[s1 + 1][s2 - 1].equals(" ") && lastMove[0] == 4 && board[lastMove[0]][lastMove[1]].equals("wP"))
                {
                    add(s1 + 1, s2 - 1);
                }
                else if(!OutOfBounds(s1 + 1, s2 + 1) && !board[s1 + 1][s2 + 1].equals(" ") && !ownPiece(s1 + 1, s2 + 1) || !OutOfBounds(s1 + 1, s2 + 1) && board[s1 + 1][s2 + 1].equals(" ") && lastMove[0] == 4 && board[lastMove[0]][lastMove[1]].equals("wP"))
                {
                    add(s1 + 1, s2 + 1);
                }
            case "wN": case "bN": 
                for(int[] move : knightMovement)
                {
                    if(!OutOfBounds(s1 + move[0], s2 + move[1]) && !ownPiece(s1 + move[0], s2 + move[1]))
                    {
                        add(s1 + move[0], s2 + move[1]);
                    }
                }
            case "wB": case "bB": 
                int i = 1;
                while(!OutOfBounds(s1 + i, s2 + i) && !ownPiece(s1 + i, s2 + i))
                {
                    add(s1 + i, s2 + i);
                    i++;
                }
                i = 1;
                while(!OutOfBounds(s1 + i, s2 - i) && !ownPiece(s1 + i, s2 - i))
                {
                    add(s1 + i, s2 - i);
                    i++;
                }
                i = 1;
                while(!OutOfBounds(s1 - i, s2 + i) && !ownPiece(s1 - i, s2 + i))
                {
                    add(s1 - i, s2 + i);
                    i++;
                }
                i = 1;
                while(!OutOfBounds(s1 - i, s2 - i) && !ownPiece(s1 - i, s2 - i))
                {
                    add(s1 - i, s2 - i);
                    i++;
                }
            case "wR": case "bR": 
                i = 1;
                while(!OutOfBounds(s1 + i, s2) && !ownPiece(s1 + i, s2))
                {
                    add(s1 + i, s2);
                    i++;
                }
                i = 1;
                while(!OutOfBounds(s1 - i, s2) && !ownPiece(s1 - i, s2))
                {
                    add(s1 - i, s2);
                    i++;
                }
                i = 1;
                while(!OutOfBounds(s1, s2 + i) && !ownPiece(s1, s2 + i))
                {
                    add(s1, s2 + i);
                    i++;
                }
                i = 1;
                while(!OutOfBounds(s1, s2 - i) && !ownPiece(s1, s2 - i))
                {
                    add(s1, s2 - i);
                    i++;
                }
            case "wQ": case "bQ": 
                getMovement(s1, s2, "wR");
                getMovement(s1, s2, "wB");
            case "wK": case "bK":
                for(int[] move : kingMovement)
                {
                    if(!OutOfBounds(s1 + move[0], s2 + move[1]) && !ownPiece(s1 + move[0], s2 + move[1]))
                    {
                        add(s1 + move[0], s2 + move[1]);
                    }
                }
        }
    }

    public void changeColour()
    {
        if(colour.equals("white"))
        {
            colour = "black";
        }
        else
        {
            colour = "white";
        }
    }

    public boolean legalMove(int[] startField, int[] targetField)
    {
        for(int i = 0; i < 27; i++)
        {
            if(startField[0] + movement[i][0] == targetField[0] && startField[1] + movement[i][1] == targetField[1])
            {
                clearMovement();
                return true;
            }
        }
        clearMovement();
        return false;
    }

    public void add(int position_y, int position_x)
    {
        for(int i = 0; i < 27; i++)
        {
            if(movement[i][0] == 0 && movement[i][1] == 0)
            {
                movement[i][0] = position_y;
                movement[i][1] = position_x;
            }
        }
    }

    public void clearMovement()
    {
        for(int i = 0; i < 27; i++)
        {
            movement[i][0] = 0;
            movement[i][1] = 0;
        }
    }

    public boolean OutOfBounds(int position_y, int position_x)
    {
        if(-1 < position_y && position_y < 8 && -1 < position_x && position_x < 8)
        {
            return false;
        }
        return true;
    }

    public int[] getKingPosition()
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(board[i][j].equals("wK") && ownPiece(i, j) || board[i][j].equals("bK") && ownPiece(i, j))
                {
                    int[] position = {i, j};
                    return position;
                }
            }
        }
        return null;
    }

    public boolean ownKingCheck() throws IOException, ParseException
    {
        int[] KingPosition = getKingPosition();
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(!board[i][j].equals(" ") && !ownPiece(i, j))
                {
                    getMovement(i, j, board[i][j]);
                    int[] startField = {i, j};
                    if(legalMove(startField, KingPosition))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean patt() throws IOException, ParseException
    {
        if(ownKingCheck())
        {
            return false;
        }
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(ownPiece(i, j))
                {
                    getMovement(i, j, board[i][j]);
                    int[] startField = {i, j};
                    for(int[] move : movement)
                    {
                        movePiece(startField, move);
                        if(!ownKingCheck())
                        {
                            moveBack(startField, move);
                            return false;
                        }
                        moveBack(startField, move);
                    }
                }
            }
        }
        return true;
    }

    public boolean mate() throws IOException, ParseException
    {
        if(!ownKingCheck())
        {
            return false;
        }
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(ownPiece(i, j))
                {
                    getMovement(i, j, board[i][j]);
                    int[] startField = {i, j};
                    for(int[] move : movement)
                    {
                        movePiece(startField, move);
                        if(!ownKingCheck())
                        {
                            moveBack(startField, move);
                            return false;
                        }
                        moveBack(startField, move);
                    }
                }
            }
        }
        return true;
    }

    public void movePiece(int[] startField, int[] targetField) throws IOException, ParseException
    {
        board[startField[0]][startField[1]] = " ";
        board[targetField[0]][targetField[1]] = board[startField[0]][startField[1]];
    }

    public void moveBack(int[] startField, int[] targetField)
    {
        board[startField[0]][startField[1]] = board[targetField[0]][targetField[1]];
        board[targetField[0]][targetField[1]] = " ";
    }

    public void finalMove(int[] startPosition, int[] targetPosition) throws IOException, ParseException
    {
        board[startPosition[0]][startPosition[1]] = " ";
        board[targetPosition[0]][targetPosition[1]] = board[startPosition[0]][startPosition[1]];
        json.add_JSONDetail(path, game, "lastMove", "" + targetPosition[0] + "," + targetPosition[1]);
        if(targetPosition[0] == 0 && board[targetPosition[0]][targetPosition[1]].equals("wP") || targetPosition[0] == 7 && board[targetPosition[0]][targetPosition[1]].equals("bP"))
        {
            json.add_JSONDetail(path, game, "conversion", "true");
        }
    }
}
