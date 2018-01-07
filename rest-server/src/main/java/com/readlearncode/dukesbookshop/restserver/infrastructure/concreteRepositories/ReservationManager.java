package com.readlearncode.dukesbookshop.restserver.infrastructure.concreteRepositories;

import com.readlearncode.dukesbookshop.restserver.domain.Request;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.domain.TimeSpan;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by navid on 11/24/17.
 */
@Stateless
public class ReservationManager {

    @EJB
    TableRepository tableRepo;

    @EJB
    ReservationRepository reserveRepo;

    public Reservation reserveResult(Request r) {
        Reservation resResult = processRequest(r);

        if (resResult != null) {
            System.out.println("reserved !!!");
            System.out.println(resResult.toString());
            return resResult;
        }

        return null;
    }

    public List<Reservation> retrieveAllReservation(Date date) {
        return reserveRepo.getAllResForDate(date);
    }

    private Reservation processRequest(Request r) {
        ArrayList<Table> fittingTables = tableRepo.getTableBySeatSorted(r.getSeats());

        Table availableTable = isAvailable(r.getDate(), fittingTables, r.getTs());// check if they are available for the given time

        if (availableTable != null) {        // reserve it if you can
             /*
                    1- reserve the availableTable
                    2- update the table state
                    3- return true
             */
            return reserveRepo.saveReserve(availableTable, r.getCustomerId(), r.getDate(), r.getTs());
        }
        return null;
    }

    private Table isAvailable(Date date, ArrayList<Table> fittingTables, TimeSpan reqTimeSpan) {
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
            ArrayList<Reservation> allResForThisTable = reserveRepo.getByTableId(table.getTableId(), date);

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

        //throw an exception
        return null;
    }

}
