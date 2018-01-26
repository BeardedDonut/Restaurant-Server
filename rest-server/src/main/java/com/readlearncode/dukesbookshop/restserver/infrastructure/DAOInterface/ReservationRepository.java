package com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface;

import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.domain.TimeSpan;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by navid on 1/23/18.
 */
public interface ReservationRepository {
    Reservation saveReserve(Table table,
                            Customer customer,
                            TimeSpan ts,
                            Date submissionDate,
                            Date reservationDate,
                            String otherReq);

    Reservation getByResId(Integer resId, Date date);

    Reservation getByResId(Integer resId);

    ArrayList<Reservation> getByTableId(Integer tableId, Date date, TimeSpan ts);

    List<Reservation> getByTableId(Integer tableId, Date date);

    List<Reservation> getAllResBetweenDates(Date startDate, Date endDate) throws Exception;

    List<Reservation> getAllResForDate(Date date);
}
