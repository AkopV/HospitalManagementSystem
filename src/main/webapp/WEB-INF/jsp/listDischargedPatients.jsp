<%--
  Created by IntelliJ IDEA.
  User: akopvardanian
  Date: 21.01.2018
  Time: 12:29
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%--Custom tag--%>
<%@ taglib uri="/WEB-INF/patientCustomTag.tld" prefix="patient" %>
<html>
<%--Head--%>
<c:set var="title" scope="request" value="Discharged patients"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body class="security-app">
<%--Header--%>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="lc-block">
    <%--Menu--%>
    <div>
        <a href="controller?command=listDischargedPatients">
            <button class="dropbtn" type="button">
                <fmt:message key="hospital_card.button.discharged_patients"/>
            </button>
        </a>
        <a href="controller?command=listPatientsByDoctorId">
            <button class="dropbtn" type="button">
                <fmt:message key="hospital_card.button.patients"/>
            </button>
        </a>
    </div>
    <br>

    <table id="patients" class="tablesorter">
        <thead>
        <tr>
            <th><fmt:message key="discharged_patients.table.first_name"/></th>
            <th><fmt:message key="discharged_patients.table.last_name"/></th>
            <th><fmt:message key="discharged_patients.table.birthday"/></th>
            <th></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach var="patient" items="${patients}">
            <tr>
                    <%--Custom Tag--%>
                <patient:patientTable patient="${patient}"/>
                <td>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="downloadFile">
                        <input type="hidden" name="firstName" value="${patient.firstName}">
                        <input type="hidden" name="lastName" value="${patient.lastName}">
                        <fmt:message key="discharged_patients.submit.download" var="download"/>
                        <input type="submit" value="${download}" class="button red small">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function()
        {
            $("#patients").tablesorter();
        }
    );
</script>

</html>
