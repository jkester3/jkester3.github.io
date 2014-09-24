<%@ page import="database.User" %>
<%
    User currentUser = (User) session.getAttribute("currentUser");
	session.setAttribute("userid", currentUser);
	response.sendRedirect("itinerary_overview.jsp");
%>