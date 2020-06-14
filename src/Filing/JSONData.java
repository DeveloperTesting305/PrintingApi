package Filing;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;

public class JSONData {

    ///*** READ DATA
    public static JSONObject read(String key){
        try{
            JSONParser parser = new JSONParser();
            Reader reader = new FileReader(Values.JSONFilePath);
            JSONObject object = (JSONObject) parser.parse(reader);

            return (JSONObject) object.get(key);
        }catch (Exception ee){
            ee.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR "+ee.getMessage());
        }
        return null;
    }

    ///*** READ DATA
    public static void write(String key, JSONObject object){
        if(object == null) return;

        try {
            ///*** READ OLD DATA
            JSONParser parser = new JSONParser();
            Reader reader = new FileReader(Values.JSONFilePath);
            JSONObject getObject = (JSONObject) parser.parse(reader);

            ///*** ADD UPDATED DATA WITH OBJECT
            getObject.put(key, object);

            ///*** WRITE DATA
            FileWriter writer = new FileWriter(Values.JSONFilePath);
            writer.write(getObject.toJSONString());
            writer.flush();
            writer.close();

        }catch (Exception ee){
            ee.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERROR "+ee.getMessage());
        }
    }

}
