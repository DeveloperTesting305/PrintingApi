package Print;

import BeanClass.ProductsBean;
import Filing.JSONData;
import Filing.Values;
import PageSize.BarcodePageSize;
import com.barcodelib.barcode.Linear;
import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class BarcodePrint extends Component implements Printable {


    private ProductsBean productsBean;
    private byte noOfBarcodes;
    private boolean showBarcodeNumber;
    private boolean showFirmName;
    private boolean showProductRate;
    private boolean showProductName;

    public BarcodePrint(ProductsBean productsBean, byte noOfBarcodes, boolean showBarcodeNumber, boolean showFirmName, boolean showProductRate, boolean showProductName) {
        this.productsBean = productsBean;
        this.noOfBarcodes = noOfBarcodes;
        this.showBarcodeNumber = showBarcodeNumber;
        this.showFirmName = showFirmName;
        this.showProductRate = showProductRate;
        this.showProductName = showProductName;
    }

    interface BoxSize {
        int boxWidth = (int) (2.4 * BarcodePageSize.ppi);
        int boxHeight = (int) (1.310 * BarcodePageSize.ppi);

        int barcodeWidth = (int) (2.083 * BarcodePageSize.ppi) ;
        int barcodeHeight = (int) (0.448 * BarcodePageSize.ppi);
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        // TODO Auto-generated method stub

        try {
            Graphics2D graphics2d = (Graphics2D) graphics;

            ///*** GET DATA FROM JSON FILE
            JSONObject detail = JSONData.read(Values.PrintingConfig.objectName);

            Linear barcode = new Linear();
            // barcode data to encode
            barcode.setType(Linear.CODE128);
            barcode.setData(""+productsBean.getProductId());

            barcode.setShowText(showBarcodeNumber);

            barcode.setUOM(Linear.UOM_PIXEL);
            // wide bar width vs narrow bar width ratio
            barcode.setN(3.0f);
            barcode.setShowText(true);
            barcode.setResolution(72);

            // get image in buffer object
            BufferedImage bufferedImage = barcode.renderBarcode();

            // x = x-axis : y = y-axis
            int x = (int) pageFormat.getImageableX() + 10;
            int y = (int) pageFormat.getImageableY();

            byte counter = 0;

            for (byte row = 1; row <= 8; row++) {
                for (byte col = 1; col <= 3; col++) {

                    if(counter >= noOfBarcodes) break;

                    int initialX = x;
                    int initialY = y;

                    ///*** SET FONT
                    graphics2d.setFont(new Font("Arial", Font.PLAIN, 8));

                    String text = "";
                    int textWidth = 0;

                    ///*** SHOP NAME
                    if(showFirmName) {
                        text = detail.get(Values.PrintingConfig.shopName).toString();
                        textWidth = graphics2d.getFontMetrics().stringWidth(text);
                        int titleX = ((BoxSize.boxWidth - textWidth) / 2) - 10;
                        graphics2d.drawString(text, initialX + titleX, initialY + 10);
                    }


                    ///*** PRODUCT NAME
                    if(showProductName) {
                        text = productsBean.getProdName();
                        textWidth = graphics2d.getFontMetrics().stringWidth(text);
                        int productNameX = ((BoxSize.boxWidth - textWidth) / 2) - 20;
                        graphics2d.drawString(text, initialX + productNameX, initialY + 50);
                    }


                    ///*** PRODUCT RATE
                    if(showProductRate) {
                        text = "RS : " + productsBean.getSaleRate();
                        textWidth = graphics2d.getFontMetrics().stringWidth(text);
                        int productRateX = ((BoxSize.boxWidth - textWidth) / 2) - 5;
                        graphics2d.drawString(text, initialX + productRateX, initialY + 60);
                    }

                    ///*** DRAW BARCODE
                    graphics2d.drawImage(
                            bufferedImage,
                            initialX,
                            initialY + 10,
                            BoxSize.barcodeWidth,
                            BoxSize.barcodeHeight,
                            null);

                    ++counter;
                    x += BoxSize.boxWidth + (0.458 * BarcodePageSize.ppi) - 15;
                }
                x = (int) pageFormat.getImageableX() + 10;
                y += BoxSize.boxHeight + 10;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return this.PAGE_EXISTS;
    }
}
