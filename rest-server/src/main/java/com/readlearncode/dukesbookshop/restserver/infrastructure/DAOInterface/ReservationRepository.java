package com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface;

import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.domain.TimeSpan;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by navid on 1/23/18.
 */
public interface ReservationRepository {
    Reservation saveReserve(Table table, int csId, Date date, TimeSpan ts);

    Reservation getByResId(int resId, Date date);

    ArrayList<Reservation> getAllResBetweenDates(Date startDate, Date endDate) throws Exception;

    Reservation getByResIdOnly(int resId);

    ArrayList<Reservation> getByTableId(int tableId, Date date);

    ArrayList<Reservation> getByTableId(int tableId, Date date, TimeSpan ts);

    ArrayList<Reservation> getAllResForDate(Date date);
}
