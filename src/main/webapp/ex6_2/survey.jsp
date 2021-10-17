<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Murach's Java Servlets and JSP</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
<form action="survey" method="post">
    <h1>Thanks for taking our survey!</h1>
    <p>Here is the information that you entered:</p>

    <label class="pad_top">Email</label>
    <p>${user.email}</p>
    <label class="pad_top">First Name</label>
    <p>${user.firstName}</p>
    <label class="pad_top">Last Name</label>
    <p>${user.lastName}</p>
    <label class="pad_top">Heard from</label>
    <p>${user.heardFrom}</p>
    <label class="pad_top">Updates</label>
    <p>${user.wantsUpdates}</p>
    <c:if test="${user.wantsUpdates == 'Yes'}">
        <label class="pad_top">Contact Via</label>
        <p>${user.contactVia}</p>
    </c:if>

</form>
</body>
</html>