package com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface;

import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.infrastructure.exceptions.TableNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 1/21/18.
 */
public interface TableRepository {
    Optional<Table> getTableById(int id);

    List<Table> getTableBySeatSorted(int numberOfSeats);

    List<Table> getAllTables();

    Optional<Table> createTable(int numberOfSeats);

    Optional<Table> createTable(Table newTable);

    Optional<Table> updateTable(int numberOfSeats, int id) throws TableNotFoundException;

    void deleteTableById(int id);

}
