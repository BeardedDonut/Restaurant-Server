package com.readlearncode.dukesbookshop.restserver.infrastructure.DAOImplementation;

import com.readlearncode.dukesbookshop.restserver.DatabaseConfig;
import com.readlearncode.dukesbookshop.restserver.domain.Customer;
import com.readlearncode.dukesbookshop.restserver.domain.Reservation;
import com.readlearncode.dukesbookshop.restserver.domain.Table;
import com.readlearncode.dukesbookshop.restserver.domain.TimeSpan;
import com.readlearncode.dukesbookshop.restserver.infrastructure.DAOInterface.ReservationRepository;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.ejb.Stateless;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by navid on 11/24/17.
 */
@Stateless(name = "ReservationRepositoryDAOImp")
public class ReservationRepositoryDAOImp implements ReservationRepository {

    @Override
    public Reservation saveReserve
            (Table table,
             Customer customer,
             TimeSpan ts,
             Date submissionDate,
             Date reservationDate,
             String otherReq) {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        Reservation res = new Reservation();
        res.setRelatedTable(table);
        res.setRelatedCustomer(customer);
        res.setReservationTime(ts);
        res.setSubmissionDate(submissionDate);
        res.setReservationDate(reservationDate);
        res.setOtherRequirements(otherReq);

        session.beginTransaction();
        session.save(res);
        session.getTransaction().commit();
        session.close();

        return res;
    }


    @Override
    public Reservation getByResId
            (Integer resId) {

        Session session = DatabaseConfig.createSessionFactory().openSession();

        Reservation reservation = (Reservation) session.get(Reservation.class, resId);

        session.close();

        if (reservation != null) {
            return reservation;
        }

        return null;
    }

    @Override
    public Reservation getByResId
            (Integer resId, Date date) {

        Session session = DatabaseConfig.createSessionFactory().openSession();

        String hql = "FROM reservation AS Res " +
                "WHERE Res.id = :resId AND " +
                "reservationDate = :resDate";

        Query query = session.
                createQuery(hql).
                setParameter("resId", resId).
                setParameter("resDate", date);

        @SuppressWarnings("unchecked")
        List<Reservation> reservations = query.list();
        session.close();

        if (reservations != null && reservations.size() > 0) {
            return reservations.get(0);
        }

        return null;
    }

    @Override
    public List<Reservation> getByTableId
            (Integer tableId, Date date) {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        String hql = "FROM reservation AS Res " +
                "WHERE Res.relatedTable.id = :tblId AND " +
                "Res.reservationDate = :resDate";

        Query query = session.
                createQuery(hql).
                setParameter("tblId", tableId).
                setParameter("resDate", date);

        String q = query.getQueryString();

        @SuppressWarnings("unchecked")
        List<Reservation> reservation = query.list();
        session.close();

        if (reservation != null && reservation.size() > 0) {
            return reservation;
        }

        return null;
    }

    @Override
    public List<Reservation> getAllResForDate
            (Date date) {

        Session session = DatabaseConfig.createSessionFactory().openSession();

        String hql = "FROM reservation " +
                "WHERE reservationDate = :resDate";

        Query query = session.
                createQuery(hql).
                setParameter("resDate", date);

        @SuppressWarnings("unchecked")
        List<Reservation> reservation = query.list();
        session.close();

        if (reservation != null && reservation.size() > 0) {
            return reservation;
        }

        return null;
    }

    @Override
    public List<Reservation> getAllResBetweenDates
            (Date startDate, Date endDate)
            throws
            Exception {
        Session session = DatabaseConfig.createSessionFactory().openSession();

        String hql = "FROM reservation " +
                "WHERE reservationDate >= :startDate AND " +
                "reservationDate <= :endDate";

        Query query = session.
                createQuery(hql).
                setParameter("startDate", startDate).
                setParameter("endDate", endDate);

        @SuppressWarnings("unchecked")
        List<Reservation> reservations = query.list();
        session.close();

        return reservations;
    }

    @Override
    public ArrayList<Reservation> getByTableId
            (Integer tableId, Date date, TimeSpan ts) {
        //TODO
        return null;
    }
}
