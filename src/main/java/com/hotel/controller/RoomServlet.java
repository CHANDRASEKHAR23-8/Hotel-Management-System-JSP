package com.hotel.controller;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rooms")
public class RoomServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final RoomDAO roomDAO = new RoomDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Room> rooms = roomDAO.getAllRooms();

        request.setAttribute("rooms", rooms);

        request.getRequestDispatcher("/rooms.jsp")
               .forward(request, response);
    }
}
