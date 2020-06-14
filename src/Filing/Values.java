package Filing;

public class Values {

    public static String JSONFile = "config.json";
    public static String JSONFilePath ="config.json";
    public static String createFileAttributes = "{\"printingConfig\":{\"selectPrinter\":\"thermalPrinter\",\"address\":\"building no # 45, street 78, city center\",\"noOfItemInTransection\":\"16\",\"shopName\":\"BILAL MART\",\"contactNo\":\"035646555, 0541564854, 02256546\"}}";

    public static class PrintingConfig{

        public static String objectName = "printingConfig";
        public static String shopName = "shopName";
        public static String address = "address";
        public static String contactNo = "contactNo";
        public static String noOfItemInTransection = "noOfItemInTransection";
        public static String selectPrinter = "selectPrinter";

        public static class SelectPrinterValues{
            public static String jetPrinter = "jetPrinter";
            public static String thermalPrinter = "thermalPrinter";
        }
    }


}
