package server_client;

import mainPrograms.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import org.json.simple.parser.ParseException;

public class clientMain
{
    private int port;
    private String checkInput = "123145345623";
    private Socket client;
    private String ip;
    private DataInputStream input;
    private DataOutputStream output;

    public clientMain(int port) throws IOException, ParseException
    {
        this.port = port;
        InetAddress localhost = InetAddress.getLocalHost();
        System.out.println(localhost);
        String ip1 = "" + localhost;
        String[] ip2 = ip1.split("/");
        ip = ip2[1];
        clientSettings();
    }

    public void clientSettings() throws IOException
    {
        client = new Socket(ip, port);
        input = new DataInputStream(client.getInputStream());
        output = new DataOutputStream(client.getOutputStream());
    }

    public String waiting() throws IOException
    {
       String message = input.readUTF();
       while(message == null || checkInput.equals(message))
       {
           message = input.readUTF();
       }
       checkInput = message;
       return message;
    }

    public void send(String message) throws IOException
    {
        output.writeUTF(message);
    }
}

