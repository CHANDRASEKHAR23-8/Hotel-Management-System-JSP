
package com.hotel.dao;

import com.hotel.model.Payment;
import com.hotel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();

        String sql =
            "SELECT PAYMENT_ID, BOOKING_ID, AMOUNT, " +
            "PAYMENT_METHOD, PAYMENT_DATE, STATUS " +
            "FROM PAYMENTS ORDER BY PAYMENT_ID DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Payment p = new Payment();

                p.setPaymentId(rs.getInt("PAYMENT_ID"));
                p.setBookingId(rs.getInt("BOOKING_ID"));
                p.setAmount(rs.getDouble("AMOUNT"));
                p.setPaymentMethod(
                    rs.getString("PAYMENT_METHOD"));
                p.setPaymentDate(rs.getDate("PAYMENT_DATE"));
                p.setStatus(rs.getString("STATUS"));

                payments.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }

    public boolean addPayment(Payment payment) {
        String sql =
            "INSERT INTO PAYMENTS " +
            "(PAYMENT_ID, BOOKING_ID, AMOUNT, " +
            "PAYMENT_METHOD, PAYMENT_DATE, STATUS) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, payment.getPaymentId());
            ps.setInt(2, payment.getBookingId());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getPaymentMethod());
            ps.setDate(5, payment.getPaymentDate());
            ps.setString(6, payment.getStatus());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
