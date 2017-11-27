package com.readlearncode.dukesbookshop.restserver.util;

import com.readlearncode.dukesbookshop.restserver.domain.Table;

import java.util.Comparator;

/**
 * Created by navid on 11/24/17.
 */
public class TableSeatComparator implements Comparator<Table> {
    @Override
    public int compare(Table table1, Table table2) {
        return table1.getNumberOfSeats() - table2.getNumberOfSeats();
    }
}
