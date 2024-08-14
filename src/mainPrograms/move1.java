package mainPrograms;

import java.io.IOException;

import javax.swing.text.TabExpander;

import org.json.simple.parser.ParseException;

//castles bei ches.json hinzufügen

public class move1 
{
    private String path = System.getProperty("user.dir");
    private String game;
    private String lastMove;
    private int[] lMove = new int[2];

    private String[][][] chessboard = new String[8][8][3];
    private String colour = "white";
    private int[] sField = new int[2];
    private int[] tField = new int[2];
    private int[][] movement = new int[27][2];
    private boolean board_switched = false;
    private String takedPiece;
    //castle
    private boolean castle = false;
    private static boolean white_qs = true;
    private static boolean white_ks = true;
    private static boolean black_qs = true;
    private static boolean black_ks = true;

    private static boolean castlePossible = true;
    //en passant
    private boolean finalPiece = true;
    private boolean enPassant = false;
    private int[] enPassantPosition = new int[2];


    public move1(String[][][] board, int[] startPosition, int[] targetPosition, String game, boolean online) throws IOException, ParseException
    {
        //**********
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                chessboard[i][j] = board[i][j];
            }
        }
        sField[0] = startPosition[0]; sField[1] = startPosition[1];
        tField[0] = targetPosition[0]; tField[1] = targetPosition[1];
        //**********
        this.game = game;
        this.lastMove = json.get_JSONDetail(path, game, "lastMove");
        String[] m1 = lastMove.split(",");
        lMove[0] = Integer.parseInt(m1[0]);
        lMove[1] = Integer.parseInt(m1[1]);
    }

    public void switchBoard()
    {
        String[][][] chessboard1 = new String[8][8][3];
        if(chessboard[0][0][2].equals("7,7") || board_switched)
        {
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    chessboard1[i][j] = chessboard[7 - i][7 - j];
                }
            }
            for(int i = 0; i < 8; i++)
            {
                for(int j = 0; j < 8; j++)
                {
                    chessboard[i][j] = chessboard1[i][j];
                }
            }
            sField[0] = 7 - sField[0]; sField[1] = 7 - sField[1];
            tField[0] = 7 - tField[0]; tField[1] = 7 - tField[1];
            colour = "black";
            board_switched = true;
        }
    }

    public String[][][] run() throws IOException, ParseException
    {
        switchBoard();
        if(!check_start_target(sField, tField))
        {
            return null;
        }
        getMovement(sField, chessboard[sField[0]][sField[1]][1]);
        if(!legalMove(tField))
        {
            return null;
        }
        movePiece(sField, tField);
        finalPiece = false;
        int[] position = getkingPosition(colour);
        if(pieceAttaked(colour, position))
        {
            move_back(sField, tField);
            return null;
        }
        enPassant = false;
        castle = false;
        if(patt())
        {
            json.add_JSONDetail(path, game, "status", "patt");
        }
        if(mate())
        {
            json.add_JSONDetail(path, game, "status", "mate");
        }
        json.add_JSONDetail(path, game, "lastMove", Integer.toString(tField[0]) + "," + Integer.toString(tField[1]));
        if(board_switched)
        {
            switchBoard();
        }
        return chessboard;
    }

    public boolean check_start_target(int[] field1, int[] field2)
    {
        if(ownPiece(field1[0], field1[1], colour) && !ownPiece(field2[0], field2[1], colour))
        {
            return true;
        }
        return false;
    }

    public boolean OutOfBounds(int field_y, int field_x)
    {
        if(-1 < field_y && field_y < 8 && -1 < field_x && field_x < 8)
        {
            return false;
        }
        return true;
    }

    public void add(int field_y, int field_x)
    {
        for(int i = 0; i < 27; i++)
        {
            if(movement[i][0] == 0 && movement[i][1] == 0)
            {
                movement[i][0] = field_y;
                movement[i][1] = field_x;
                break;
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

    public boolean legalMove(int[] field)
    {
        for(int[] move : movement)
        {
            if(move[0] == field[0] && move[1] == field[1])
            {
                clearMovement();
                return true;
            }
        }
        clearMovement();
        return false;
    }

    public void getMovement(int[] field, String piece) throws IOException, ParseException
    {
        String[] pieceColour = piece.split("_");
        if(piece.equals("white_pawn.png"))
        {
            if(!OutOfBounds(field[0] - 1, field[1]) && chessboard[field[0] - 1][field[1]][1].equals("") && !ownPiece(field[0] - 1, field[1], pieceColour[0]))
            {
                add(field[0] - 1, field[1]);
                if(!OutOfBounds(field[0] - 2, field[1]) && chessboard[field[0] - 2][field[1]][1].equals("") && !ownPiece(field[0] - 2, field[1], pieceColour[0]))
                {
                    add(field[0] - 2, field[1]);
                }
            }
            if(!OutOfBounds(field[0] - 1, field[1] + 1) && !chessboard[field[0] - 1][field[1] + 1][1].equals("") && !ownPiece(field[0] - 1, field[1] + 1, pieceColour[0]) || !OutOfBounds(field[0] - 1, field[1] + 1) && chessboard[field[0]][field[1] + 1][1].equals("black_pawn.png") && chessboard[field[0] - 1][field[1] + 1][1].equals("") && lMove[0] == 3)
            {
                add(field[0] - 1, field[1] + 1);
            }
            if(!OutOfBounds(field[0] - 1, field[1] - 1) && !chessboard[field[0] - 1][field[1] - 1][1].equals("") && !ownPiece(field[0] - 1, field[1] - 1, pieceColour[0]) || !OutOfBounds(field[0] - 1, field[1] - 1) && chessboard[field[0]][field[1] - 1][1].equals("black_pawn.png") && chessboard[field[0] - 1][field[1] - 1][1].equals("") && lMove[0] == 3)
            {
                add(field[0] - 1, field[1] - 1);
            }
        }
        if(piece.equals("black_pawn.png"))
        {
            if(!OutOfBounds(field[0] + 1, field[1]) && chessboard[field[0] + 1][field[1]][1].equals("") && !ownPiece(field[0] + 1, field[1], pieceColour[0]))
            {
                add(field[0] + 1, field[1]);
                if(!OutOfBounds(field[0] + 2, field[1]) && chessboard[field[0] + 2][field[1]][1].equals("") && !ownPiece(field[0] + 2, field[1], pieceColour[0]))
                {
                    add(field[0] + 2, field[1]);
                }
            }
            if(!OutOfBounds(field[0] + 1, field[1] + 1) && !chessboard[field[0] + 1][field[1] + 1][1].equals("") && !ownPiece(field[0] + 1, field[1] + 1, pieceColour[0]) || !OutOfBounds(field[0] + 1, field[1] + 1) && chessboard[field[0]][field[1] + 1][1].equals("white_pawn.png") && chessboard[field[0] + 1][field[1] + 1][1].equals("") && lMove[0] == 4)
            {
                add(field[0] + 1, field[1] + 1);
            }
            if(!OutOfBounds(field[0] + 1, field[1] - 1) && !chessboard[field[0] + 1][field[1] - 1][1].equals("") && !ownPiece(field[0] + 1, field[1] - 1, pieceColour[0]) || !OutOfBounds(field[0] + 1, field[1] - 1) && chessboard[field[0]][field[1] - 1][1].equals("white_pawn.png") && chessboard[field[0] + 1][field[1] - 1][1].equals("") && lMove[0] == 4)
            {
                add(field[0] + 1, field[1] - 1);
            }
        }
        if(piece.equals("white_knight.png") || piece.equals("black_knight.png"))
        {
            int[][] knightMovement = {{1, 2}, {1, -2}, {2, 1}, {2, -1}, {-1, 2}, {-1, -2}, {-2, 1}, {-2, -1}};
            for(int[] move : knightMovement)
            {
                if(!OutOfBounds(field[0] + move[0], field[1] + move[1]) && !ownPiece(field[0] + move[0], field[1] + move[1], pieceColour[0]))
                {
                    add(field[0] + move[0], field[1] + move[1]);
                }
            }
        }
        if(piece.equals("white_bishop.png") || piece.equals("black_bishop.png"))
        {
            int[][] bishopMovement = {{1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
            for(int[] move : bishopMovement)
            {
                int i = 1;
                while(!OutOfBounds(field[0] + (i * move[0]), field[1] + (i * move[1])) && !ownPiece(field[0] + (i * move[0]), field[1] + (i * move[1]), pieceColour[0]))
                {
                    add(field[0] + (i * move[0]), field[1] + (i * move[1]));
                    if(!chessboard[field[0] + (i * move[0])][field[1] + (i * move[1])][1].equals(""))
                    {
                        break;
                    }
                    i++;
                }
            }
        }
        if(piece.equals("white_rook.png") || piece.equals("black_rook.png"))
        {
            int[][] rookMovement = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for(int[] move : rookMovement)
            {
                int i = 1;
                while(!OutOfBounds(field[0] + (i * move[0]), field[1] + (i * move[1])) && !ownPiece(field[0] + (i * move[0]), field[1] + (i * move[1]), pieceColour[0]))
                {
                    add(field[0] + (i * move[0]), field[1] + (i * move[1]));
                    if(!chessboard[field[0] + (i * move[0])][field[1] + (i * move[1])][1].equals(""))
                    {
                        break;
                    }
                    i++;
                }
            }
        }
        if(piece.equals("white_queen.png") || piece.equals("black_queen.png"))
        {
            if(pieceColour[0].equals("white"))
            {
                getMovement(field, "white_rook.png");
                getMovement(field, "white_bishop.png");
            }
            else
            {
                getMovement(field, "black_rook.png");
                getMovement(field, "black_bishop.png");
            }
        }
        if(piece.equals("white_king.png") || piece.equals("black_king.png"))
        {
            int[][] kingMovement = {{1, 0}, {1, 1}, {1, -1}, {-1, 0}, {-1, 1}, {-1, -1}, {0, 1}, {0, -1}};
            for(int[] move : kingMovement)
            {
                if(!OutOfBounds(field[0] + move[0], field[1] + move[1]) && !ownPiece(field[0] + move[0], field[1] + move[1], pieceColour[0]))
                {
                    add(field[0] + move[0], field[1] + move[1]);
                }
            }
            //castle
            int[] kingMovement2 = {1, -1};
            int i = 1;
            for(int kingMove : kingMovement2)
            {
                while(!OutOfBounds(field[0], field[1] + (i * kingMove)))
                {
                    if(!chessboard[field[0]][field[1] + (i * kingMove)][1].equals(""))
                    {
                        if(chessboard[field[0]][field[1] + (i * kingMove)][1].equals("white_rook.png") || chessboard[field[0]][field[1] + (i * kingMove)][1].equals("black_rook.png"))
                        {
                            add(field[0], field[1] + (2 * kingMove));
                            castle = true;
                            break;
                        }
                        break;
                    }
                    else
                    {

                    }
                    i++;
                }
            }
            
        }
    }

    public boolean ownPiece(int field_y, int field_x, String pieceColour)
    {
        if(pieceColour.equals("white"))
        {
            String[] split = chessboard[field_y][field_x][1].split("_");
            if(split[0].equals("white"))
            {
                return true;
            }
            return false;
        }
        else
        {
            String[] split = chessboard[field_y][field_x][1].split("_");
            if(split[0].equals("black"))
            {
                return true;
            }
            return false;
        }
    }

    public int[] getkingPosition(String pieceColour)
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                String[] colour1 = chessboard[i][j][1].split("_");
                if(pieceColour.equals(colour1[0]) && chessboard[i][j][1] == "white_king.png" || pieceColour.equals(colour1[0]) && chessboard[i][j][1] == "black_king.png")
                {
                    int[] kingPosition = {i, j};
                    return kingPosition;
                }
            }
        }
        return null;
    }

    public boolean pieceAttaked(String pieceColour, int[] position) throws IOException, ParseException
    {
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(!chessboard[i][j][1].equals("") && !ownPiece(i, j, pieceColour))
                {
                    int[] field = {i, j};
                    getMovement(field, chessboard[i][j][1]);
                    if(legalMove(position))
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
        String pColour;
        if(colour.equals("white"))
        {
            pColour = "black";
        }
        else
        {
            pColour = "white";
        }
        int[] kingPosition = getkingPosition(pColour);
        if(pieceAttaked(pColour, kingPosition))
        {
            return false;
        }
        int counter1 = 0; int counter2 = 0;
        boolean pattChance = false;
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(ownPiece(i, j, colour))
                {
                    counter1++;
                }
                if(ownPiece(i, j, pColour))
                {
                    counter2++;
                }
                if(chessboard[i][j][1].equals("white_knight.png") || chessboard[i][j][1].equals("black_knight.png") || chessboard[i][j][1].equals("white_bishop.png") || chessboard[i][j][1].equals("white_bishop.png"))
                {
                    pattChance = true;
                }
            }
        }
        if(counter1 == 1 && counter2 == 2 && pattChance || counter1 == 2 && counter2 == 1 && pattChance)
        {
            return true;
        }
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(ownPiece(i, j, pColour))
                {
                    int[] field = {i, j};
                    getMovement(field, chessboard[i][j][1]);
                    int[][] movement1 = new int[27][2];
                    for(int l = 0; l < 27; l++)
                    {
                        movement1[l][0] = movement[l][0]; movement1[l][1] = movement[l][1];
                    }
                    clearMovement();
                    for(int k = 0; k < 27; k++) 
                    {
                        if(movement1[k][0] == 0 && movement1[k][1] == 0)
                        {
                            break;
                        }
                        int[] move = {movement1[k][0], movement1[k][1]};
                        movePiece(field, move);
                        int[] position = getkingPosition(pColour);
                        if(!pieceAttaked(pColour, position))
                        {
                            move_back(field, move);
                            return false;
                        }
                        move_back(field, move);
                    }
                }
            }
        }
        return true;
    }

    public boolean mate() throws IOException, ParseException
    {
        String pColour;
        if(colour.equals("white"))
        {
            pColour = "black";
        }
        else
        {
            pColour = "white";
        }
        int[] position = getkingPosition(pColour);
        if(!pieceAttaked(pColour, position))
        {
            return false;
        }
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                if(ownPiece(i, j, pColour))
                {
                    int[] field = {i, j};
                    getMovement(field, chessboard[i][j][1]);
                    int[][] movement1 = new int[27][2];
                    for(int l = 0; l < 27; l++)
                    {
                        movement1[l][0] = movement[l][0]; movement1[l][1] = movement[l][1];
                    }
                    clearMovement();
                    for(int k = 0; k < 27; k++) 
                    {
                        if(movement1[k][0] == 0 && movement1[k][1] == 0)
                        {
                            break;
                        }
                        int[] move = {movement1[k][0], movement1[k][1]};
                        movePiece(field, move);
                        position = getkingPosition(pColour);
                        if(!pieceAttaked(pColour, position))
                        {
                            move_back(field, move);
                            return false;
                        }
                        move_back(field, move);
                    }
                }
            }
        }
        return true;
    }

    public void movePiece(int[] start, int[] target)
    {
        if(castle && chessboard[start[0]][start[1]][1].equals("white_king.png") || castle && chessboard[start[0]][start[1]][1].equals("black_king.png"))
        {
            if(start[1] + 2 == target[1])
            {
                if(chessboard[start[0]][start[1]][1].equals("black_king.png") && move1.black_ks)
                {
                    chessboard[target[0]][target[1]][1] = chessboard[start[0]][start[1]][1];
                    chessboard[start[0]][start[1]][1] = "";

                    chessboard[target[0]][target[1] - 1][1] = chessboard[start[0]][7][1];
                    chessboard[start[0]][7][1] = "";
                    if(finalPiece)
                    {
                        move1.black_ks = false;
                        castlePossible = false;
                    }
                }
                else if(chessboard[start[0]][start[1]][1].equals("white_king.png") && move1.white_ks)
                {
                    chessboard[target[0]][target[1]][1] = chessboard[start[0]][start[1]][1];
                    chessboard[start[0]][start[1]][1] = "";

                    chessboard[target[0]][target[1] - 1][1] = chessboard[start[0]][7][1];
                    chessboard[start[0]][7][1] = "";
                    if(finalPiece)
                    {
                        move1.white_ks = false;
                        castlePossible = false;
                    }
                }
            }
            else if(start[1] - 2 == target[1])
            {
                if(chessboard[start[0]][start[1]][1].equals("black_king.png") && move1.black_qs)
                {
                    chessboard[target[0]][target[1]][1] = chessboard[start[0]][start[1]][1];
                    chessboard[start[0]][start[1]][1] = "";

                    chessboard[target[0]][target[1] + 1][1] = chessboard[start[0]][0][1];
                    chessboard[start[0]][0][1] = "";
                    if(finalPiece)
                    {
                        move1.black_qs = false;
                        castlePossible = false;
                    }
                }
                else if(chessboard[start[0]][start[1]][1].equals("white_king.png") && move1.white_qs)
                {
                    chessboard[target[0]][target[1]][1] = chessboard[start[0]][start[1]][1];
                    chessboard[start[0]][start[1]][1] = "";

                    chessboard[target[0]][target[1] + 1][1] = chessboard[start[0]][0][1];
                    chessboard[start[0]][0][1] = "";
                    if(finalPiece)
                    {
                        move1.white_qs = false;
                        castlePossible = false;
                    }
                }
            }
            else
            {
                castle = false;
                movePiece(start, target);
            }
        }
        else
        {
            if(chessboard[start[0]][start[1]][1].equals("white_king.png") && finalPiece)
            {
                move1.white_ks = false;
                move1.white_qs = false;
            }
            if(chessboard[start[0]][start[1]][1].equals("black_king.png") && finalPiece)
            {
                move1.black_ks = false;
                move1.black_qs = false;
            }
            takedPiece = chessboard[target[0]][target[1]][1];
            chessboard[target[0]][target[1]][1] = chessboard[start[0]][start[1]][1];
            chessboard[start[0]][start[1]][1] = "";

            if(chessboard[target[0]][target[1]][1].equals("white_pawn.png") && lMove[0] == 3)
            {
                if(chessboard[target[0] + 1][target[1]][1].equals("black_pawn.png") && chessboard[start[0]][start[1] + 1][1].equals("black_pawn.png") || chessboard[target[0] + 1][target[1]][1].equals("black_pawn.png") && chessboard[start[0]][start[1] - 1][1].equals("black_pawn.png"))
                {
                    takedPiece = chessboard[target[0] + 1][target[1]][1];
                    chessboard[target[0] + 1][target[1]][1] = "";
                    enPassantPosition[0] = target[0] + 1; enPassantPosition[1] = target[1];
                    enPassant = true;
                }
            }
            else if(chessboard[target[0]][target[1]][1].equals("black_pawn.png") && lMove[0] == 4)
            {
                if(chessboard[target[0] - 1][target[1]][1].equals("white_pawn.png") && chessboard[start[0]][start[1] + 1][1].equals("white_pawn.png") || chessboard[target[0] - 1][target[1]][1].equals("white_pawn.png") && chessboard[start[0]][start[1] - 1][1].equals("white_pawn.png"))
                {
                    takedPiece = chessboard[target[0] - 1][target[1]][1];
                    chessboard[target[0] - 1][target[1]][1] = "";
                    enPassantPosition[0] = target[0] - 1; enPassantPosition[1] = target[1];
                    enPassant = true;
                }
            }
        }
    }

    public void move_back(int[] start, int[] target)
    {
       
        if(castle)
        {
            castle = false;
            if(start[1] + 2 == target[1])
            {
                chessboard[start[0]][start[1]][1] = chessboard[target[0]][target[1]][1];
                chessboard[target[0]][target[1]][1] = "";

                chessboard[start[0]][7][1] = chessboard[target[0]][target[1] - 1][1];
                chessboard[target[0]][target[1] - 1][1] = "";
            }
            else if(start[1] - 2 == target[1])
            {
                chessboard[start[0]][start[1]][1] = chessboard[target[0]][target[1]][1];
                chessboard[target[0]][target[1]][1] = "";

                chessboard[start[0]][0][1] = chessboard[target[0]][target[1] + 1][1];
                chessboard[target[0]][target[1] + 1][1] = "";
            }
        }
        else if(enPassant)
        {
            enPassant = false;
            chessboard[start[0]][start[1]][1] = chessboard[target[0]][target[1]][1];
            chessboard[target[0]][target[1]][1] = "";
            chessboard[enPassantPosition[0]][enPassantPosition[1]][1] = takedPiece;
        }
        else
        {     
            chessboard[start[0]][start[1]][1] = chessboard[target[0]][target[1]][1];
            chessboard[target[0]][target[1]][1] = takedPiece;
        }
        takedPiece = "";
    } 
}

//Tagesplan
//castle
//conversion

//clear king moved

//bug en passant
