package PageSize;


import java.awt.print.PageFormat;
import java.awt.print.Paper;

public class PageFormats {
    
    public static PageFormat BillPageFormat(Paper paper){
        PageFormat pageFormat = new PageFormat();

        paper.setSize(BillPageSize.width, BillPageSize.height);
        paper.setImageableArea(BillPageSize.leftMargin, BillPageSize.topMargin, BillPageSize.imageableWidth, BillPageSize.imageableHeight);

        pageFormat.setPaper(paper);
        pageFormat.setOrientation(PageFormat.PORTRAIT);

        return pageFormat;
    }

    public static PageFormat ThermalBillPageFormat(Paper paper){
        PageFormat pageFormat = new PageFormat();

        paper.setSize(ThermalBillPageSize.width, ThermalBillPageSize.height);
        paper.setImageableArea(ThermalBillPageSize.leftMargin, ThermalBillPageSize.topMargin, ThermalBillPageSize.imageableWidth, ThermalBillPageSize.imageableHeight);

        pageFormat.setPaper(paper);
        pageFormat.setOrientation(PageFormat.PORTRAIT);

        return pageFormat;
    }
    
}
