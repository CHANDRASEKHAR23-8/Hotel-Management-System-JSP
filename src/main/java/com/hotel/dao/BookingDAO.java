
package com.hotel.dao;

import com.hotel.model.Booking;
import com.hotel.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();

        String sql =
            "SELECT b.BOOKING_ID, b.GUEST_ID, b.ROOM_ID, " +
            "b.CHECK_IN, b.CHECK_OUT, b.TOTAL_AMOUNT, b.STATUS, " +
            "g.NAME AS GUEST_NAME, r.ROOM_NUMBER " +
            "FROM BOOKINGS b " +
            "JOIN GUESTS g ON b.GUEST_ID = g.GUEST_ID " +
            "JOIN ROOMS r ON b.ROOM_ID = r.ROOM_ID " +
            "ORDER BY b.BOOKING_ID DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("BOOKING_ID"));
                b.setGuestId(rs.getInt("GUEST_ID"));
                b.setRoomId(rs.getInt("ROOM_ID"));
                b.setCheckIn(rs.getDate("CHECK_IN"));
                b.setCheckOut(rs.getDate("CHECK_OUT"));
                b.setTotalAmount(rs.getDouble("TOTAL_AMOUNT"));
                b.setStatus(rs.getString("STATUS"));
                b.setGuestName(rs.getString("GUEST_NAME"));
                b.setRoomNumber(rs.getString("ROOM_NUMBER"));
                bookings.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public boolean addBooking(Booking booking) {
        String rentSql =
            "SELECT RENT, AVAILABLE FROM ROOMS WHERE ROOM_ID = ?";

        String insertSql =
            "INSERT INTO BOOKINGS " +
            "(BOOKING_ID, GUEST_ID, ROOM_ID, CHECK_IN, " +
            "CHECK_OUT, TOTAL_AMOUNT, STATUS) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                double rent;

                try (PreparedStatement ps =
                         con.prepareStatement(rentSql)) {
                    ps.setInt(1, booking.getRoomId());

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }

                        rent = rs.getDouble("RENT");

                        if (rs.getInt("AVAILABLE") != 1) {
                            con.rollback();
                            return false;
                        }
                    }
                }

                long nights =
                    (booking.getCheckOut().getTime()
                    - booking.getCheckIn().getTime())
                    / (24L * 60 * 60 * 1000);

                if (nights <= 0) {
                    con.rollback();
                    return false;
                }

                double total = rent * nights;

                try (PreparedStatement ps =
                         con.prepareStatement(insertSql)) {
                    ps.setInt(1, booking.getBookingId());
                    ps.setInt(2, booking.getGuestId());
                    ps.setInt(3, booking.getRoomId());
                    ps.setDate(4, booking.getCheckIn());
                    ps.setDate(5, booking.getCheckOut());
                    ps.setDouble(6, total);
                    ps.setString(7, "Booked");

                    ps.executeUpdate();
                }

                con.commit();
                return true;

            } catch (SQLException | RuntimeException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
