<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Accommodation Chose</title>
</head>
<body>
    <jsp:include page="banner.jsp"></jsp:include>
    <h2>Price for accommodation</h2>
    <jsp:useBean id="userRequest" class="it.unitn.disi.vinci.app.model.UserRequest" scope="session"/>
    <p>Date From: <jsp:getProperty name="userRequest" property="dateFrom"/></p>
    <p>Date To: <jsp:getProperty name="userRequest" property="dateTo"/></p>
    <p>Number of Persons: <jsp:getProperty name="userRequest" property="nPersons"/></p>
    <p>Price: <%=request.getAttribute("totPrice")%></p>
    <div>
        <form action="reservation" method="post">
            Credit Card Number:<input type="text" name="creditCardNumber"/>
            Name:<input type="text" name="name"/>
            Surname:<input type="text" name="surname"/><br/><br/>
            <input type="submit" value="Confirm"/>
        </form>
    </div>

    <jsp:include page="inputError.jsp"></jsp:include>
    <jsp:include page="emptyResult.jsp"></jsp:include>
</body>
</html>