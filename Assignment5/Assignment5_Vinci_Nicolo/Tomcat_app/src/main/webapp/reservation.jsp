<%@ page import="it.unitn.disi.vinci.entities.ReservationApartment" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unitn.disi.vinci.entities.ReservationHotel" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservations</title>
</head>
<body>
    <jsp:include page="banner.jsp"></jsp:include>
    <% if ((boolean) request.getAttribute("summary")) { %>
        <jsp:include page="inputError.jsp"></jsp:include>
        <% if(Objects.nonNull(request.getAttribute("exception"))) { %>
            <div>
                <p><b><%=request.getAttribute("exception")%></b></p>
            </div>
        <% } else { %>
            <h2>You booking was successful!</h2>
            <h2>Check your reservations</h2>
            <form action="reservation" method="get">
                Name:<input type="text" name="name"/>
                Surname:<input type="text" name="surname"/><br/><br/>
                <input type="submit" value="Show your reservations"/>
            </form>
        <% } %>
    <% } else { %>
        <% if (Objects.nonNull(request.getAttribute("reservationsHotel"))) { %>
            <h2>Your Reservations for Hotel</h2>
            <% List<ReservationHotel> reservationsHotel = (List<ReservationHotel>) request.getAttribute("reservationsHotel"); %>
            <ul>
                <% for (ReservationHotel reservationHotel: reservationsHotel) { %>
                    <li>
                        Accommodation name: <%=reservationHotel.getAccommodation().getName()%>
                        <br/>
                        Extra Half board: <%=reservationHotel.getHalfBoard()%>
                        <br/>
                        Date from: <%=reservationHotel.getDateFrom()%>
                        <br/>
                        Date to: <%=reservationHotel.getDateTo()%>
                    </li>
                <% } %>
            </ul>
        <% } else { %>
            <% if(Objects.nonNull(request.getAttribute("emptyReservationsHotel"))) { %>
            <div>
                <p><b><%=request.getAttribute("emptyReservationsHotel")%></b></p>
            </div>
            <% } %>
        <% } %>
        <% if(Objects.nonNull(request.getAttribute("reservationsApartment"))) { %>
            <h2>Your Reservations for Apartment</h2>
            <% List<ReservationApartment> reservationsApartment = (List<ReservationApartment>) request.getAttribute("reservationsApartment"); %>
            <ul>
                <% for (ReservationApartment reservationApartment: reservationsApartment) { %>
                    <li>
                        Accommodation name: <%=reservationApartment.getAccommodation().getName()%>
                        <br/>
                        Date from: <%=reservationApartment.getDateFrom()%>
                        <br/>
                        Date to: <%=reservationApartment.getDateTo()%>
                    </li>
                <% } %>
            </ul>
        <% } else { %>
            <% if(Objects.nonNull(request.getAttribute("emptyReservationsApartment"))) { %>
            <div>
                <p><b><%=request.getAttribute("emptyReservationsApartment")%></b></p>
            </div>
            <% } %>
        <% } %>
    <% } %>
</body>
</html>
