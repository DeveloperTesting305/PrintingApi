import Filing.JSONData;
import Filing.Values;
import org.json.simple.JSONObject;

import java.io.*;

public class MainTemp {

//    static {
//        System.out.println("static block");
//        File file = new File(Values.JSONFilePath);
//        if(!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    public static void main(String[] args) {
        JSONObject object = JSONData.read("printingConfig");
        byte num = Byte.parseByte(object.get(Values.PrintingConfig.noOfItemInTransection)+"");
        System.out.println(num);

    }
}
