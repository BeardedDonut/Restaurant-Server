package com.readlearncode.dukesbookshop.restserver.infrastructure.concreteRepositories;


import com.readlearncode.dukesbookshop.restserver.DatabaseConfig;
import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.infrastructure.abstractRepositories.TableRepository;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 11/24/17.
 */
@Stateless
public class ConcreteTableRepository implements TableRepository {

    @Override
    public Optional<Table> getTableById(int id) {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        Table tbl = (Table) session.load(Table.class, id);

        session.close();
        return Optional.ofNullable(tbl);
    }

    @Override
    public List<Table> getTableBySeatSorted(int numberOfSeats) {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        String hql = "FROM restaurantTable order by numberOfSeats";
        Query query = session.createQuery(hql);

        @SuppressWarnings("unchecked")
        List<Table> tables = query.list();

        session.close();
        return tables;
    }

    @Override
    public List<Table> getAllTables() {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        String hql = "FROM restaurantTable";
        Query query = session.createQuery(hql);

        @SuppressWarnings("unchecked")
        List<Table> tables = query.list();

        session.close();
        return tables;
    }

    @Override
    public Optional<Table> createTable(int numberOfSeats) {
        Session session = DatabaseConfig.createSessionFactory().openSession();
        Table newTable = new Table();
        newTable.setNumberOfSeats(numberOfSeats);

        session.beginTransaction();
        session.save(newTable);
        session.getTransaction().commit();
        System.out.println("Table Created Successfully...");

        session.close();
        return Optional.of(newTable);
    }

    @Override
    public Optional<Table> createTable(Table newTable) {
        return this.createTable(newTable.getNumberOfSeats());
    }

    @Override
    public void deleteTableById(int id) {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        Table tbl = getTableById(id).get();
        session.delete(tbl);
        session.getTransaction().commit();

        System.out.println("Successfully deleted " + tbl.toString());
    }


}
