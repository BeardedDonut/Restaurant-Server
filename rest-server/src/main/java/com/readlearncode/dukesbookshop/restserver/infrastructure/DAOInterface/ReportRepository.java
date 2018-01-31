package com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface;

import com.readlearncode.dukesbookshop.restserver.dataTransferObjects.TotalIncomeDTO;
import com.readlearncode.dukesbookshop.restserver.dataTransferObjects.TotalSaleDTO;

import java.sql.Date;
import java.util.Optional;

/**
 * Created by navid on 1/30/18.
 */
public interface ReportRepository {

    Optional<TotalSaleDTO> getTotalSale();

    Optional<TotalSaleDTO> getTotalSale(Date startDate, Date endDate);

    Optional<TotalIncomeDTO> getTotalIncome();
}
