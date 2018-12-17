package usm.hrs.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.UUID;
import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import usm.hrs.model.BookingDao;
/**
 * DataStax Academy Sample Application
 *
 * Copyright 2013 DataStax
 *
 */
@WebServlet("/getBookings")
public class GetAllBookingsServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		BookingDao bookingDao = new BookingDao();
		HttpSession session = request.getSession();
		session.setAttribute("bookings",bookingDao.getBookingDetails((String) session.getAttribute("user")));
		response.sendRedirect("reservations.jsp");
	}
}
