<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
    <h1>Welcome, check your reservation or make a new one!</h1>
    <div>
        <form action="accommodation" method="post">
            Date From:<input type="text" name="dateFrom"/>
            Date To:<input type="text" name="dateTo"/>
            Number of persons:<input type="text" name="nPersons"/><br/><br/>
            <input type="submit" value="Show available accommodations"/>
        </form>
    </div>
    <br/><br/>
    <div>
        <form action="reservation" method="get">
            Name:<input type="text" name="name"/>
            Surname:<input type="text" name="surname"/><br/><br/>
            <input type="submit" value="Show your reservations"/>
        </form>
    </div>
</body>
</html>
