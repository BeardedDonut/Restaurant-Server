package com.readlearncode.dukesbookshop.restserver.dataTransferObjects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by navid on 1/30/18.
 */

@XmlRootElement
public class TotalIncomeDTO {
    private Double totalIncome;

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TotalIncomeDTO that = (TotalIncomeDTO) o;

        return totalIncome != null ? totalIncome.equals(that.totalIncome) : that.totalIncome == null;
    }

    @Override
    public int hashCode() {
        return totalIncome != null ? totalIncome.hashCode() : 0;
    }
}
