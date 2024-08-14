package mainPrograms;
import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//JSONArray
//JSONObject1 --> obejcts
//JSONObject2 --> details

public class json
{
    public static void createJSON(String path) throws IOException
    {
        /** Create chess folder */
        File file = new File(path + "\\JSON");
        if(!file.exists())
        {
            file.mkdir();
        }
        //** Create jsonFile */
        File jsonFile = new File(path + "\\JSON", "chess.json");
        if(!jsonFile.exists())
        {
            jsonFile.createNewFile();
        }
    }

    public static JSONArray get_JSONArray(String path) throws IOException, ParseException
    {
        FileReader reader = new FileReader(path + "\\JSON\\chess.json");
        BufferedReader br = new BufferedReader(reader);
        JSONArray JSON = new JSONArray();
        if(br.readLine() != null)
        {
            JSONParser jsonParser = new JSONParser();
            FileReader reader2 = new FileReader(path + "\\JSON\\chess.json");
            Object obj = jsonParser.parse(reader2);
            JSONArray JSON2 = (JSONArray) obj;
            reader.close();
            reader2.close();
            return JSON2;
        }
        reader.close();
        return JSON;
    }

    public static JSONObject get_JSONObject(String path, String objectString) throws IOException, ParseException
    {
        JSONArray JSON = get_JSONArray(path);
        int i = 0;
        try
        {
            while(true)
            {
                JSONObject object = (JSONObject) JSON.get(i);
                if(object.get(objectString) != null)
                {
                    return object;
                }
                i++;
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    public static JSONObject get_JSONDetails(String path, String objectString) throws IOException, ParseException
    {
        JSONObject object = get_JSONObject(path, objectString);
        JSONObject details = (JSONObject) object.get(objectString);
        return details;
    }

    public static String get_JSONDetail(String path, String objectString, String detailString) throws IOException, ParseException
    {
        JSONObject details = get_JSONDetails(path, objectString);
        String detail = (String) details.get(detailString);
        return detail;
    }

    public static void add_JSONObject(String path, String[][] objectDetails, String name) throws IOException, ParseException
    {
        JSONArray JSON = json.get_JSONArray(path);

        JSONObject details = new JSONObject();
        for(String[] detail : objectDetails)
        {
            details.put(detail[0], detail[1]);
        }

        JSONObject object = new JSONObject();
        object.put(name, details);

        FileWriter writer = new FileWriter(path + "\\JSON\\chess.json");
        JSON.add(object);
        writer.write(JSON.toJSONString());
        writer.flush();
        writer.close();

    }

    public static void add_JSONDetail(String path, String objectString, String detailString, String value) throws IOException, ParseException
    {
        JSONArray array = new JSONArray();
        JSONArray JSON = get_JSONArray(path);
        int i = 0;
        try
        {
            while(true)
            {
                JSONObject object = (JSONObject) JSON.get(i);
                JSONObject object2 = new JSONObject();
                if(object.get(objectString) != null)
                {
                    JSONObject details = (JSONObject) object.get(objectString);
                    String detail = (String) details.get(detailString);
                    if(detail == null)
                    {
                        details.put(detailString, value);
                    }
                    else
                    {
                        details.remove(detailString);
                        details.put(detailString, value);
                    }
                    object2.put(objectString, details);
                    array.add(object2);
                }
                else
                {
                    array.add(object);
                }
                i++;
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            FileWriter writer = new FileWriter(path + "\\JSON\\chess.json");
            writer.write(array.toJSONString());
            writer.flush();
            writer.close();
        }
    }

    public static void remove_JSONObject(String path, String objectString) throws IOException, ParseException
    {
        JSONArray JSON = get_JSONArray(path);
        JSONArray array = new JSONArray();

        int i = 0;
        try
        {
            while(true)
            {
                JSONObject object = (JSONObject) JSON.get(i);
                if(object.get(objectString) == null)
                {
                    array.add(object);
                }
                i++;
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            FileWriter writer = new FileWriter(path + "\\JSON\\chess.json");
            writer.write(array.toJSONString());
            writer.flush();
            writer.close();
        }
    }

    public static void remove_JSONDetail(String path, String objectString, String detailString) throws IOException, ParseException
    {
        JSONArray JSON = get_JSONArray(path);
        JSONArray array = new JSONArray();

        int i = 0;
        try
        {
            while(true)
            {
                JSONObject object = (JSONObject) JSON.get(i);
                JSONObject object2 = new JSONObject();
                if(object.get(objectString) != null)
                {
                    JSONObject details = (JSONObject) object.get(objectString);
                    details.remove(detailString);
                    object2.put(objectString, details);
                    array.add(object2);
                }
                else
                {
                    array.add(object);
                }
                i++;
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            FileWriter writer = new FileWriter(path + "\\JSON\\chess.json");
            writer.write(array.toJSONString());
            writer.flush();
            writer.close();
        }
    }
}
