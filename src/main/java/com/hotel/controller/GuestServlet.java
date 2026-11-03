package com.hotel.controller;
import com.hotel.dao.GuestDAO;
import com.hotel.model.Guest;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/guests")
public class GuestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final GuestDAO guestDAO = new GuestDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Guest> guests = guestDAO.getAllGuests();
        request.setAttribute("guests", guests);

        request.getRequestDispatcher("/guests.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            Guest guest = new Guest();
            guest.setGuestId(Integer.parseInt(
                    request.getParameter("guestId")));
            guest.setName(request.getParameter("name"));
            guest.setPhone(request.getParameter("phone"));
            guest.setEmail(request.getParameter("email"));
            guest.setAddress(request.getParameter("address"));

            if (guestDAO.addGuest(guest)) {
                response.sendRedirect(
                        request.getContextPath() + "/guests?added=1");
            } else {
                response.sendRedirect(
                        request.getContextPath() + "/guests?error=1");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath() + "/guests?error=1");
        }
    }
}
