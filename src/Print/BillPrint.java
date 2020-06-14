package Print;

import BeanClass.CustomersBean;
import BeanClass.ProductTransectionBean;
import BeanClass.ProductsBean;
import BeanClass.TransectionBean;
import Customs.Decode;
import DatabaseConnection.DatabaseManager;
import Filing.JSONData;
import Filing.Values;
import PageSize.BillPageSize;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;

public class BillPrint extends Component implements Printable {
    private TransectionBean transectionBean;
    private ArrayList<ProductTransectionBean> productTransectionArrayList;
    private String memo;

    public BillPrint(Integer transectionId, String memo){
        try{
            this.transectionBean = DatabaseManager.db.getTransectionBeanByTransectionId(transectionId);
            this.productTransectionArrayList = DatabaseManager.db.getProductTransectionByTransectionId(transectionId);
            this.memo = memo;
        }catch (Exception ee){
            JOptionPane.showMessageDialog(null, "Error");
            ee.printStackTrace();
        }
    }

    public BillPrint(TransectionBean transectionBean, ArrayList<ProductTransectionBean> productTransectionArrayList, String memo) {
        this.transectionBean = transectionBean;
        this.productTransectionArrayList = productTransectionArrayList;
        this.memo = memo;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int i) throws PrinterException {

        try {
            Integer startX = 0;
            Integer startY = 0;

            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            graphics.setColor(Color.BLACK);

            FontMetrics fontMetrics = graphics.getFontMetrics();

            Integer x = (int) pageFormat.getImageableX();
            Integer y = (int) pageFormat.getImageableY();

            x += 5;
            y += 5;

            int width = 0;

            ///*** GET DATA FROM JSON FILE
            JSONObject detail = JSONData.read(Values.PrintingConfig.objectName);

            ///*** SHOP NAME
            graphics.setFont(new Font("Open Sans", Font.BOLD, 16));
            String shopName = detail.get(Values.PrintingConfig.shopName).toString();
            width = graphics.getFontMetrics().stringWidth(shopName);
            int shopNameX = (int) ((BillPageSize.imageableWidth - width) / 2);
            graphics.drawString(shopName, shopNameX, 15);

            ///*** SHOP ADDRESS
            graphics.setFont(new Font("Open Sans", Font.PLAIN, 7));
            String address = detail.get(Values.PrintingConfig.address).toString();
            width = graphics.getFontMetrics().stringWidth(address);
            int addressX = (int) ((BillPageSize.imageableWidth - width) / 2);
            graphics.drawString(address, addressX, 23);

            ///*** SHOP CONTACT NUMBER
            String contactNo = detail.get(Values.PrintingConfig.contactNo).toString();
            width = graphics.getFontMetrics().stringWidth(contactNo);
            int contactNoX = (int) ((BillPageSize.imageableWidth - width) / 2);
            graphics.drawString(contactNo, contactNoX, 30);

            ///*** TID
            graphics.setFont(new Font("Open Sans", Font.PLAIN, 8));
            graphics.drawString("TID : " + transectionBean.getTransectionId(), 5, 43);

            ///*** CASH/CREDIT MEMO HEADIND
            graphics.setFont(new Font("Open Sans", Font.BOLD, 10));
            graphics.drawString(this.memo, 112, 43);

            ///*** DATE
            graphics.setFont(new Font("Open Sans", Font.PLAIN, 8));
            graphics.drawString("Date : " + transectionBean.getTransDate(), 206, 43);

            ///*** CLIENT NAME
            CustomersBean customersBean = DatabaseManager.db.getCustomerBeanByCustomerId(transectionBean.getCustomerId());
            graphics.setFont(new Font("Open Sans", Font.PLAIN, 10));
            String clientName;
            if(!transectionBean.getName().equals("")) clientName = transectionBean.getName();
            else clientName = customersBean.getFirmName();
            graphics.drawString("Client Name : " + clientName, 5, 58);

            ///*** CITY
            graphics.drawString("City : "+DatabaseManager.db.getCity(customersBean.getCityId()), 5, 71);

            ///*** HEADING UPPER LINE
            graphics.drawLine(5, 91 - 8, 275, 91 - 8);

            ///*** HEADING
            graphics.setFont(new Font("Open Sans", Font.BOLD, 10));
            graphics.drawString("Qty", 6, 93);
            graphics.drawString("Perticulars", 40, 93);
            graphics.drawString("Unit", 139, 93);
            graphics.drawString("Price", 183, 93);
            graphics.drawString("Amount", 225, 93);

            ///*** HEADING LOWER LINE
            graphics.drawLine(5, 106 - 8, 275, 106 - 8);

            ///*** PRODUNT DETAIL
            graphics.setFont(new Font("Open Sans", Font.PLAIN, 10));

            y = 109;
            Float netAmount = 0.0f;
            for (ProductTransectionBean bean : this.productTransectionArrayList) {
                ProductsBean productsBean = DatabaseManager.db.getProductBeanByProductId(bean.getProductId());
                graphics.setColor(Color.BLACK);
                graphics.drawString(""+bean.getQuantity(), 7, y);                                                    /// QUANTITY
                graphics.drawString(""+productsBean.getProdName(), 40, y);                                           /// PRODUCT NAME
                y += 11;
                graphics.drawString(DatabaseManager.db.getProductUnitNameByProductId(bean.getProductId()), 139, y);     /// UNIT
                graphics.drawString(""+bean.getUnitPrice(), 183, y);                                                 /// PRICE
                graphics.drawString(""+bean.getAmount(), 224, y);                                                    /// AMOUNT
                y += 12;
                ///*** SEPARATOR LINE
                graphics.setColor(Color.LIGHT_GRAY);
                graphics.drawLine(5, y - 8, 275, y - 8);
                y += 2;

                ///*** CALCULATE NET AMOUNT
                netAmount += bean.getAmount();
            }

            ///*** FOR BILL DETAIL STATUS|CONFIRMED BY|REMARKS
            Integer billDetailY = y;

            ///*** CHANGE COLOR
            graphics.setColor(Color.BLACK);

            ///*** NET TOTAL
            graphics.drawString("Net Total", 130, y);
            graphics.drawString(""+netAmount, 209, y);
            y += 12;

            ///*** DISCOUNT
            if (transectionBean.getDiscount() > 0) {
                graphics.drawString("Discount -", 130, y);
                graphics.drawString(""+transectionBean.getDiscount(), 209, y);
                y += 12;
            }

            ///*** EXPENCE
            Float expence = transectionBean.getOtherExpence() + transectionBean.getPackingExpence();
            if (expence > 0) {
                graphics.drawString("Expence +", 130, y);
                graphics.drawString(""+expence, 209, y);
                y += 12;
            }

            ///*** AMOUNT SEPARATOR LINE
            graphics.drawLine(127, y - 7, 275, y - 7);
            y += 2;

            ///*** TOTAL AMOUNT
            graphics.setFont(new Font("Open Sans", Font.BOLD, 10));
            graphics.drawString("Total Amount", 130, y);
            graphics.setFont(new Font("Open Sans", Font.PLAIN, 10));
            graphics.drawString(""+transectionBean.getTotalAmount(), 209, y);

            ///*** BILL DETAIL
            billDetailY += 25;
            graphics.setFont(new Font("Open Sans", Font.PLAIN, 8));
            ///*** STATUS
            graphics.drawString("Status : "+ Decode.transection_Status(transectionBean.getStatus()), 6, billDetailY);
            billDetailY += 8;

            ///*** CONFIRMED BY
            graphics.drawString("Confimed By : "+transectionBean.getConfirmedBy(), 6, billDetailY);
            billDetailY += 8;

            ///*** REMARKS
            graphics.drawString("Remarks :", 6, billDetailY);
            StringBuffer remarks = new StringBuffer(transectionBean.getRemarks());
            System.out.println(remarks.length());

            for (int line = 60; line <= remarks.length(); line += 60) {
                remarks.insert(line, "\n");
            }
            drawString(graphics, remarks.toString(), 13, billDetailY);

            return this.PAGE_EXISTS;

        }catch (Exception ee){
            ee.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error "+ee.getMessage());
        }

        return this.NO_SUCH_PAGE;
    }

    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += 8);
    }


}
