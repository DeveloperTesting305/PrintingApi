package PageSize;

public interface ThermalBillPageSize {
    Double ppi = Double.valueOf(72);

    Double width = Double.valueOf(2.8) * ppi;
    Double height = Double.valueOf(5) * ppi;

    Double leftMargin = Double.valueOf(0);
    Double rightMargin = Double.valueOf(0);
    Double topMargin = Double.valueOf(0);
    Double bottomMargin = Double.valueOf(0);

    Double imageableWidth = width - (leftMargin + rightMargin);
    Double imageableHeight = height - (topMargin + bottomMargin);

    Boolean landscape = false;
}
