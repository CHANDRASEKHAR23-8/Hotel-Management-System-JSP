package com.hotel.dao;
import com.hotel.model.Room;
import com.hotel.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class RoomDAO {

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();

        String sql = "SELECT ROOM_ID, ROOM_NUMBER, ROOM_TYPE, "
                   + "CAPACITY, RENT, AVAILABLE "
                   + "FROM ROOMS ORDER BY ROOM_ID";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Room room = new Room();

                room.setRoomId(rs.getInt("ROOM_ID"));
                room.setRoomNumber(rs.getString("ROOM_NUMBER"));
                room.setRoomType(rs.getString("ROOM_TYPE"));
                room.setCapacity(rs.getInt("CAPACITY"));
                room.setRent(rs.getDouble("RENT"));
                room.setAvailable(rs.getInt("AVAILABLE"));

                rooms.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
