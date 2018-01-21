package com.readlearncode.dukesbookshop.restserver.infrastructure.abstractRepositories;

import com.readlearncode.dukesbookshop.restserver.domain.Table;

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

    void deleteTableById(int id);
}
