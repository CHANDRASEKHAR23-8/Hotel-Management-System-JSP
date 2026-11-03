
package com.hotel.dao;

import com.hotel.model.Guest;
import com.hotel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestDAO {

    public List<Guest> getAllGuests() {
        List<Guest> guests = new ArrayList<>();

        String sql = "SELECT GUEST_ID, NAME, PHONE, EMAIL, ADDRESS "
                   + "FROM GUESTS ORDER BY GUEST_ID";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Guest guest = new Guest();
                guest.setGuestId(rs.getInt("GUEST_ID"));
                guest.setName(rs.getString("NAME"));
                guest.setPhone(rs.getString("PHONE"));
                guest.setEmail(rs.getString("EMAIL"));
                guest.setAddress(rs.getString("ADDRESS"));
                guests.add(guest);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guests;
    }

    public boolean addGuest(Guest guest) {
        String sql = "INSERT INTO GUESTS "
                   + "(GUEST_ID, NAME, PHONE, EMAIL, ADDRESS) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, guest.getGuestId());
            ps.setString(2, guest.getName());
            ps.setString(3, guest.getPhone());
            ps.setString(4, guest.getEmail());
            ps.setString(5, guest.getAddress());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
