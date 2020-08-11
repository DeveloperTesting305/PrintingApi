package Print;

import BeanClass.ProductsBean;
import BeanClass.ProductCategoryBean;
import DatabaseConnection.DatabaseManager;
import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProductPrint extends Component implements Printable {

    private ArrayList<ProductsBean> productArrayList;
    private ProductCategoryBean categoryBean;
    private int totalProduct;
    private int pageNo;
    private int totalPage;

    public ProductPrint(ArrayList<ProductsBean> productArrayList, ProductCategoryBean categoryBean, int totalProduct, int pageNo, int totalPage) {
        this.productArrayList = productArrayList;
        this.categoryBean = categoryBean;
        this.totalProduct = totalProduct;
        this.pageNo = pageNo;
        this.totalPage = totalPage;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int i) throws PrinterException {

        Integer startX = 0;
        Integer startY = 0;

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        graphics.setColor(Color.BLACK);

        ///*** DATE
        graphics.setFont(new Font("Open Sans", Font.PLAIN, 8));
        String dateStr = "Date : "+new SimpleDateFormat("dd-MM-yyyy hh:mm:ss:aa").format(new Date());
        graphics.drawString(dateStr, 21, 22);

        ///*** TITLE
        graphics.setFont(new Font("Open Sans", Font.BOLD, 16));
        String title = "Rate List";
        graphics.drawString(title, 248, 24);

        ///*** PRODUCT CATEGORY
        graphics.setFont(new Font("Open Sans", Font.PLAIN, 8));
        String cat = "Category : "+categoryBean.getProdCatId()+" - "+categoryBean.getProdCatName();
        graphics.drawString(cat, 398, 22);

        ///*** TOTAL PRODUCT
        String totalProductStr = "No Of Product : "+totalProduct;
        graphics.drawString(totalProductStr, 398, 35);

        ///*** NO: OF PRODUCT
        String noOfProduct = "Total Product : "+totalProduct;
        graphics.drawString(cat, 398, 22);

        ///*** SEPARATOR LINE
        graphics.drawLine(13, 51-8, 582, 51-8);
        graphics.setFont(new Font("Open Sans", Font.BOLD, 12));

        ///*** PRODUCT ID
        graphics.drawString("PID", 25, 54);

        ///*** PRODUCT NAME
        graphics.drawString("PRODUCT NAME", 70, 54);

        ///*** BRAND NAME
        graphics.drawString("BRAND NAME", 231, 54);

        ///*** UNIT
        graphics.drawString("UNIT", 442, 54);

        ///*** SALE RATE
        graphics.drawString("RATE", 534, 54);

        ///*** SEPARATOR LINE
        graphics.drawLine(13, 69-10, 582, 69-10);


        try {

            int y = 72;
            for (ProductsBean bean : productArrayList) {
                graphics.setFont(new Font("Open Sans", Font.PLAIN, 12));
                graphics.setColor(Color.BLACK);
                graphics.drawString("" + bean.getProductId(), 25, y);                                                /// PID
                graphics.drawString("" + bean.getProdName(), 70, y);                                                 /// PRODUCT NAME
                y += 15;
                graphics.drawString(bean.getProdBrandName(), 231, y);                                                   /// BRAND NAME
                y -= 15;
                graphics.drawString(DatabaseManager.db.getUnitBeanById(bean.getUnitId()).getUnitName(), 442, y);         /// UNIT
//                graphics.drawString(""+bean.getUnitId(), 442, y);                                                    /// UNIT
                graphics.setFont(new Font("Open Sans", Font.BOLD, 12));
                graphics.drawString("" + bean.getSaleRate(), 534, y);                                                /// RATE
                y += 15;
                ///*** SEPARATOR LINE
                graphics.setColor(Color.GRAY);
                graphics.drawLine(13, y+5, 582, y+5);
                y += 20;
            }

            ///*** PAGE NUMBER
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Open Sans", Font.PLAIN, 8));
            String pNo = "Page "+pageNo+" / "+totalPage;
            graphics.drawString(pNo, 526, 811);
        }catch (Exception ee){
            ee.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error "+ee.getMessage());
        }

        return this.PAGE_EXISTS;
    }
}
