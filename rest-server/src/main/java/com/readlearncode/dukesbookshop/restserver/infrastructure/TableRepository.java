package com.readlearncode.dukesbookshop.restserver.infrastructure;


import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.util.TableSeatComparator;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by navid on 11/24/17.
 */
@Stateless
public class TableRepository {
    //TODO: for test
    public static int currentNumberOfTables = 1;

    private HashMap<Integer, Table> tables = new HashMap<>();

    public Optional<Table> getTableById(int id) {
        return Optional.ofNullable(tables.get(id));
    }

    public ArrayList<Table> getTableBySeatSorted(int numberOfSeats) {
        ArrayList<Table> allTables = new ArrayList<Table>(tables.values());
        ArrayList<Table> fittingTables = new ArrayList<>();

        for (Table t : allTables) {
            if (t.getNumberOfSeats() > numberOfSeats) {
                fittingTables.add(t);
            }
        }

        fittingTables.sort(new TableSeatComparator());

        return fittingTables;
    }

    public ArrayList<Table> getAllTables() {
        return new ArrayList<>(tables.values());
    }

    public Optional<Table> addTable(int numberOfSeats) {
        //TODO: generate ID
        Table newTable = new Table(currentNumberOfTables++, numberOfSeats);
        tables.put(newTable.getTableId(), newTable);
        return Optional.of(newTable);
    }
}
