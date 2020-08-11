package PageSize;

public interface ProductPrintPageSize{
    
    Double ppi = Double.valueOf(72);

    Double width = Double.valueOf(8.268) * ppi;
    Double height = Double.valueOf(11.693) * ppi;

    Double leftMargin = Double.valueOf(0.181);
    Double rightMargin = Double.valueOf(0.181);
    Double topMargin = Double.valueOf(0.181);
    Double bottomMargin = Double.valueOf(0.181 );

    Double imageableWidth = width - (leftMargin + rightMargin);
    Double imageableHeight = height - (topMargin + bottomMargin);

    Boolean landscape = false;
}