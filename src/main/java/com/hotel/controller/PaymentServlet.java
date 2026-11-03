
package com.hotel.controller;

import com.hotel.dao.PaymentDAO;
import com.hotel.model.Payment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/payments")
public class PaymentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PaymentDAO paymentDAO = new PaymentDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
            "payments", paymentDAO.getAllPayments());

        request.getRequestDispatcher("/payments.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            Payment payment = new Payment();

            payment.setPaymentId(Integer.parseInt(
                request.getParameter("paymentId")));
            payment.setBookingId(Integer.parseInt(
                request.getParameter("bookingId")));

            double amount = Double.parseDouble(
                request.getParameter("amount"));

            if (!Double.isFinite(amount) || amount <= 0) {
                response.sendRedirect(
                    request.getContextPath()
                    + "/payments?error=input");
                return;
            }

            payment.setAmount(amount);
            payment.setPaymentMethod(
                request.getParameter("paymentMethod"));
            payment.setPaymentDate(Date.valueOf(
                request.getParameter("paymentDate")));
            payment.setStatus("Paid");

            boolean added = paymentDAO.addPayment(payment);

            if (added) {
                response.sendRedirect(
                    request.getContextPath()
                    + "/payments?added=1");
            } else {
                response.sendRedirect(
                    request.getContextPath()
                    + "/payments?error=save");
            }

        } catch (IllegalArgumentException e) {
            response.sendRedirect(
                request.getContextPath()
                + "/payments?error=input");
        }
    }
}
