package com.readlearncode.dukesbookshop.restserver.infrastructure;

import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.domain.TimeSpan;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ejb.Stateless;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by navid on 11/24/17.
 */
@Stateless
public class ReservationRepository {

    private static int reservationNumber = 1;
    //e.g. <"2017-02-03", <resId:23531, Reservation:resObj>>
    private HashMap<String, HashMap<Integer, Reservation>> reservations = new HashMap<>();

    public Reservation saveReserve(Table table, int csId, Date date, TimeSpan ts) {
        HashMap<Integer, Reservation> resMap = reservations.get(date.toString());

        if (resMap == null) {
            resMap = new HashMap<Integer, Reservation>();
            reservations.put(date.toString(), resMap);
        }

        Reservation newRes = new Reservation(reservationNumber++, csId, table.getTableId(), date, ts, "");

        resMap.put(newRes.getReservationId(), newRes);

        return newRes;
    }

    public Reservation getByResId(int resId, Date date) {
        return reservations.get(date.toString()).get(resId);
    }

    public ArrayList<Reservation> getAllResBetweenDates(Date startDate, Date endDate) throws Exception {
        /*
            NOTE:   since we will eventually use an dbms to fetch data from
                    and since we have not yet get to there we are going to just
                    throw an exception..
         */
        //TODO:
        throw new NotImplementedException();
    }

    //TODO : test
    public Reservation getByResIdOnly(int resId) {
        HashMap<Integer, Reservation> resMap = (HashMap<Integer, Reservation>) reservations.values();
        if (resMap != null) {
            return resMap.get(resId);
        }
        return null;
    }

    public ArrayList<Reservation> getByTableId(int tableId, Date date) {
        String dateToString = date.toString();
        HashMap<Integer, Reservation> resMap = reservations.get(date.toString());

        if (resMap != null) {
            ArrayList<Reservation> resForTheGivenDate = new ArrayList<>(resMap.values());

            ArrayList<Reservation> wantedResvs = new ArrayList<>();

            for (Reservation resv : resForTheGivenDate) {
                if (resv.getTableId() == tableId) {
                    wantedResvs.add(resv);
                }
            }
            return wantedResvs;
        } else {
            HashMap<Integer, Reservation> newResByDate = new HashMap<Integer, Reservation>();
            reservations.put(date.toString(), newResByDate);
            return null;
        }

    }

    public ArrayList<Reservation> getByTableId(int tableId, Date date, TimeSpan ts) {
        //TODO
        return null;
    }

    public ArrayList<Reservation> getAllResForDate(Date date) {
        return new ArrayList<>(reservations.get(date.toString()).values());
    }
}
