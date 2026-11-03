
package com.hotel.controller;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.GuestDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Booking;
import com.hotel.model.Guest;
import com.hotel.model.Room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/bookings")
public class BookingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final BookingDAO bookingDAO = new BookingDAO();
    private final GuestDAO guestDAO = new GuestDAO();
    private final RoomDAO roomDAO = new RoomDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Booking> bookings = bookingDAO.getAllBookings();
        List<Guest> guests = guestDAO.getAllGuests();
        List<Room> rooms = roomDAO.getAllRooms();

        request.setAttribute("bookings", bookings);
        request.setAttribute("guests", guests);
        request.setAttribute("rooms", rooms);

        request.getRequestDispatcher("/bookings.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            Booking booking = new Booking();

            booking.setBookingId(Integer.parseInt(
                request.getParameter("bookingId")));
            booking.setGuestId(Integer.parseInt(
                request.getParameter("guestId")));
            booking.setRoomId(Integer.parseInt(
                request.getParameter("roomId")));
            booking.setCheckIn(Date.valueOf(
                request.getParameter("checkIn")));
            booking.setCheckOut(Date.valueOf(
                request.getParameter("checkOut")));

            if (booking.getCheckOut().compareTo(
                    booking.getCheckIn()) <= 0) {
                response.sendRedirect(
                    request.getContextPath()
                    + "/bookings?error=dates");
                return;
            }

            boolean added = bookingDAO.addBooking(booking);

            if (added) {
                response.sendRedirect(
                    request.getContextPath()
                    + "/bookings?added=1");
            } else {
                response.sendRedirect(
                    request.getContextPath()
                    + "/bookings?error=save");
            }

        } catch (IllegalArgumentException e) {
            response.sendRedirect(
                request.getContextPath()
                + "/bookings?error=input");
        }
    }
}
