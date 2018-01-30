package com.readlearncode.dukesbookshop.restserver.infrastructure.DAOImplementation;

import com.readlearncode.dukesbookshop.restserver.dataTransferObjects.CheckRequestDTO;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.domain.TimeSpan;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.ReservationRepository;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.TableRepository;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by navid on 11/24/17.
 */
@Stateless(name = "ReservationManager")
public class ReservationManager {

    @EJB
    TableRepository tableRepo;

    @EJB
    ReservationRepository reserveRepo;

    public Reservation reserveResult
            (CheckRequestDTO r) {

        Reservation resResult = processRequest(r);

        if (resResult != null) {
            System.out.println("reserved !!!");
            System.out.println(resResult.toString());
            return resResult;
        }

        return null;
    }

    public List<Reservation> retrieveAllReservation
            (Date date) {

        return reserveRepo.getAllResForDate(date);
    }

    public Reservation processRequest
            (CheckRequestDTO r) {

        List<Table> fittingTables = tableRepo.getTableBySeatSorted(r.getNumberOfSeats());

        // check if they are available for the given time
        Table availableTable = isAvailable(r.getDate(), fittingTables, r.getTs());

        if (availableTable != null) {        // reserve it if you can
             /*
                    1- reserve the availableTable
                    2- update the table state
                    3- return true
             */
            Date now = Date.valueOf(LocalDate.now());
            return reserveRepo.saveReserve(
                    availableTable,
                    r.getRelatedCustomer(),
                    r.getTs(),
                    now,
                    r.getDate(),
                    "");

        }
        return null;
    }

    public Table isAvailable
            (Date date,
             List<Table> fittingTables,
             TimeSpan reqTimeSpan) {
         /*
            check for each table whether it is available or not:
                return the first one that is not even booked for the given date.

                check for the timespan intersection if the table was booked already:
                    if it doesn't intersect with requested timespan then :
                        return true.
                    else :
                        return false.
         */

        for (Table table : fittingTables) {
            /* fetch all the reservation for this table */
            List<Reservation> allResForThisTable = reserveRepo.getByTableId(table.getId(), date);

            if (allResForThisTable == null || allResForThisTable.size() == 0) {    // if not reserved before
                return table;
            } else {    // if reserved already check for TS intersection
                for (Reservation resv : allResForThisTable) {
                    if (!resv.doesTimeSpanConflicts(reqTimeSpan)) {
                        return table;
                    }
                }
            }
        }

        //TODO: throw an exceptions
        return null;
    }

}
