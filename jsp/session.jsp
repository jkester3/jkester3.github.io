<%
if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
    response.sendRedirect("/CS2340Servlet/jsp/index.jsp");
}
%>