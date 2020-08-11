import BeanClass.ProductCategoryBean;
import BeanClass.ProductsBean;
import Filing.JSONData;
import Filing.Values;
import org.json.simple.JSONObject;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.ArrayList;

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
//        JSONObject object = JSONData.read("printingConfig");
//        byte num = Byte.parseByte(object.get(Values.PrintingConfig.noOfItemInTransection)+"");
//        System.out.println(num);

        try{
            Book book = new Book();

            ProductCategoryBean categoryBean = new ProductCategoryBean();
            categoryBean.setProdCatId(2);
            categoryBean.setProdCatName("category test");

            ArrayList<ProductsBean> arrayList = new ArrayList<>();
            for(byte itm = 0; itm < 100; itm++){
                ProductsBean bean = new ProductsBean();
                bean.setProdCatId(itm*10000);
                bean.setProdName("Product Name Product Name Product Name Product Name");
                bean.setProdBrandName("Brand Name Product Name Product Name Product Name");
                bean.setUnitId(1);
                bean.setSaleRate((float) (400*itm));
                arrayList.add(bean);
            }

            int totalNoOfProduct = arrayList.size();
            int productsPerPage = 21;
            int productsOnLastPage = totalNoOfProduct % productsPerPage;
            int noOfPages = totalNoOfProduct / productsPerPage;

            int productNo = -1;
            for(int pageNo = 1; pageNo <= noOfPages; pageNo++){
                Paper paper =  new Paper();
                PageFormat format = PageSize.PageFormats.ProductPrintPageFormat(paper);
                ArrayList<ProductsBean> beanArrayList = new ArrayList<>();
                for(int objNo = 0; objNo < productsPerPage; objNo++) beanArrayList.add(arrayList.get(++productNo));
                Print.ProductPrint productPrint = new Print.ProductPrint(beanArrayList, categoryBean, totalNoOfProduct, pageNo, noOfPages+1);
                book.append(productPrint, format);
            }
            ///*** REMAINING PRODUCT
            Paper paper =  new Paper();
            PageFormat format = PageSize.PageFormats.ProductPrintPageFormat(paper);
            ArrayList<ProductsBean> beanArrayList = new ArrayList<>();
            for(int objNo = 0; objNo < productsOnLastPage; objNo++) beanArrayList.add(arrayList.get(productNo++));
            Print.ProductPrint productPrint = new Print.ProductPrint(beanArrayList, categoryBean, totalNoOfProduct, noOfPages+1, noOfPages+1);
            book.append(productPrint, format);

            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(book);
            if(job.printDialog()) job.print();

        }catch (Exception ee){
            ee.printStackTrace();
        }

    }
}
