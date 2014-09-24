<%
    session.setAttribute("currentUser", null);
	session.setAttribute("userid", null);
	session.invalidate();
	response.sendRedirect("index.jsp");
%>