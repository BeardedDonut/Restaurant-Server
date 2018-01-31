package com.readlearncode.dukesbookshop.restserver.dataTransferObjects;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

/**
 * Created by navid on 1/30/18.
 */
@XmlRootElement
public class TotalSaleDTO {

    private HashMap<String, Integer> sales = new HashMap<>();

    public HashMap<String, Integer> getSales() {
        return sales;
    }

    public void setSales(HashMap<String, Integer> sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TotalSaleDTO that = (TotalSaleDTO) o;

        return sales != null ? sales.equals(that.sales) : that.sales == null;
    }

    @Override
    public int hashCode() {
        return sales != null ? sales.hashCode() : 0;
    }
}
