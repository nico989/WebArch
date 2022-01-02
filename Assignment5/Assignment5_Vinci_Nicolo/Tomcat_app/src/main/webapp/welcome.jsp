<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
    <h1>Welcome, make a reservation or check your reservation!</h1>
    <div>
        <form action="accommodation" method="post">
            Name:<input type="text" name="name"/>
            Surname:<input type="text" name="surname"/>
            Date From:<input type="text" name="dateFrom"/>
            Date To:<input type="text" name="dateTo"/>
            Number of persons:<input type="text" name="nPersons"/><br/><br/>
            <input type="submit" value="Show available accommodations"/>
            <input type="submit" value="Show your reservations"/><br/><br/>
        </form>
    </div>
</body>
</html>