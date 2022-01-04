<%@ page import="it.unitn.disi.vinci.entities.Hotel" %>
<%@ page import="java.util.List" %>
<%@ page import="it.unitn.disi.vinci.entities.Apartment" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Accommodations</title>
    <link rel="stylesheet" href="accommodationCSS.css" />
</head>
<body>
    <jsp:include page="banner.jsp"></jsp:include>
    <h2>Available Accommodations</h2>
    <% if(Objects.nonNull(request.getAttribute("hotels"))) { %>
        <div>
            <h3>Hotels</h3>
            <form action="singleAccommodation" method="post">
                <table>
                    <tr>
                        <th>Pick</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Extra Half Board</th>
                        <th>Stars</th>
                        <th>Places</th>
                    </tr>

                    <% List<Hotel> hotels = (List<Hotel>) request.getAttribute("hotels"); %>
                    <% for (Hotel hotel: hotels) { %>
                    <tr>
                        <td>
                            <label>
                                <input type="checkbox" name="hotelId" value="<%=hotel.getId()%>">
                            </label>
                            <input type="hidden" name="type" value="hotel">
                        </td>
                        <td><%=hotel.getName()%></td>
                        <td><%=hotel.getPrice()%></td>
                        <td><%=hotel.getExtraHalfBoard()%></td>
                        <td><%=hotel.getStars()%></td>
                        <td><%=hotel.getPlaces()%></td>
                        <td>
                            <input type="checkbox" id="extraHalfBoard" name="extraHalfBoard" value="chosen">
                            <label for="extraHalfBoard">Extra Half Board</label>
                        </td>
                    </tr>
                    <% } %>
                </table><br/>
                <input type="submit" value="Book Hotel">
            </form>
        </div>
    <% } else { %>
        <% if(Objects.nonNull(request.getAttribute("emptyHotels"))) { %>
            <div>
                <p><b><%=request.getAttribute("emptyHotels")%></b></p>
            </div>
        <% } %>
    <% } %>
    <% if(Objects.nonNull(request.getAttribute("apartments"))) { %>
    <div>
            <h3>Apartments</h3>
            <form action="singleAccommodation" method="post">
                <table>
                    <tr>
                        <th>Pick</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Final Cleaning</th>
                        <th>Max Persons</th>
                    </tr>
                    <% List<Apartment> apartments = (List<Apartment>) request.getAttribute("apartments"); %>
                    <% for (Apartment apartment: apartments) { %>
                        <tr>
                            <td>
                                <label>
                                    <input type="checkbox" name="apartmentId" value="<%=apartment.getId()%>">
                                </label>
                                <input type="hidden" name="type" value="apartment">
                            </td>
                            <td><%=apartment.getName()%></td>
                            <td><%=apartment.getPrice()%></td>
                            <td><%=apartment.getFinalCleaning()%></td>
                            <td><%=apartment.getMaxPersons()%></td>
                        </tr>
                    <% } %>
                </table><br/>
                <input type="submit" value="Book Apartment">
            </form>
        </div>
    <% } else { %>
        <% if(Objects.nonNull(request.getAttribute("emptyApartments"))) { %>
            <div>
                <p><b><%=request.getAttribute("emptyApartments")%></b></p>
            </div>
        <% } %>
    <% } %>
    <jsp:include page="inputError.jsp"></jsp:include>
</body>
</html>
