package PageSize;

public interface BarcodePageSize {
    Double ppi = Double.valueOf(72);

    Double width = Double.valueOf(8.268) * ppi;
    Double height = Double.valueOf(11.693) * ppi;

    Double leftMargin = Double.valueOf(0.293701) * ppi;
    Double rightMargin = Double.valueOf(0.293701) * ppi;
    Double topMargin = Double.valueOf(0.19685) * ppi;
    Double bottomMargin = Double.valueOf(0.19685) * ppi;

    Double imageableWidth = width - (leftMargin + rightMargin);
    Double imageableHeight = height - (topMargin + bottomMargin);

    Boolean landscape = false;
}
