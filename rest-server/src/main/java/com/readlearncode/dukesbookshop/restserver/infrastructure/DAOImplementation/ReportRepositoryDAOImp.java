package com.readlearncode.dukesbookshop.restserver.infrastructure.DAOImplementation;

import com.readlearncode.dukesbookshop.restserver.DatabaseConfig;
import com.readlearncode.dukesbookshop.restserver.dataTransferObjects.TotalIncomeDTO;
import com.readlearncode.dukesbookshop.restserver.dataTransferObjects.TotalSaleDTO;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.ReportRepository;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by navid on 1/30/18.
 */
@Stateless(name = "ReportRepositoryDAOImp")
public class ReportRepositoryDAOImp implements ReportRepository {

    @Override
    public Optional<TotalSaleDTO> getTotalSale() {
        // TODO: add get Total sale!
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<TotalSaleDTO> getTotalSale(Date startDate, Date endDate) {
        // TODO: add total sale report for a given date
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<TotalIncomeDTO> getTotalIncome() {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        session.beginTransaction();
        SQLQuery query = session.createSQLQuery("CALL totalIncome()");


        TotalIncomeDTO ttlInc = new TotalIncomeDTO();
        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.list();
        for (Object row : rows) {
            ttlInc.setTotalIncome(Double.parseDouble(row.toString()));
            System.out.println(ttlInc);
        }

        session.close();
        return Optional.ofNullable(ttlInc);
    }
}
