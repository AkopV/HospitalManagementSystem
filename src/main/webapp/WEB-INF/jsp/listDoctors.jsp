<%--
  Created by IntelliJ IDEA.
  User: akopvardanian
  Date: 17.01.2018
  Time: 23:14
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%@ taglib prefix="doctor" tagdir="/WEB-INF/tags" %>

<html>
<%--Head--%>
<c:set var="title" scope="request" value="Doctors"></c:set>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body class="security-app">
<%-- HEADER --%>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<%-- HEADER --%>
<div class="lc-block">
    <%-- Menu --%>
    <%@ include file="/WEB-INF/jspf/adminMenu.jspf" %>
    <%-- Menu --%>

    <%-- Doctors table --%>
    <table id="doctors" class="tablesorter">
        <thead>
        <tr>
            <th><fmt:message key="doctors.table.login"></fmt:message></th>
            <th><fmt:message key="doctors.table.first_name"></fmt:message></th>
            <th><fmt:message key="doctors.table.last_name"></fmt:message></th>
            <th><fmt:message key="doctors.table.specialization"></fmt:message></th>
            <th><fmt:message key="doctors.table.count_of_patients"></fmt:message></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="doctor" items="${doctors}">
            <tr>
                <doctor:doctor doctor="${doctor}"></doctor:doctor>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <%-- Doctors table --%>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function () {
            $("#doctors").tablesorter();
        }
    );

    $(document).ready(function () {
        $(".dropdown").on("hide.bs.dropdown", function () {
            $(".btn").html('Dropdown <span class="caret"></span>');
        });
        $(".dropdown").on("show.bs.dropdown", function () {
            $(".btn").html('Dropdown <span class="caret caret-up"></span>');
        });
    });
</script>
</html>
