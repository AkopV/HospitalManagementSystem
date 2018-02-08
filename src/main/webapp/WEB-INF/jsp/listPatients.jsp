<%--
  Created by IntelliJ IDEA.
  User: akopvardanian
  Date: 20.01.2018
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<%--Custom tag--%>
<%@ taglib prefix="patient" uri="/WEB-INF/patientCustomTag.tld" %>
<%@ taglib prefix="font" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<%--Head--%>
<c:set var="title" scope="request" value="Patients"></c:set>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body class="security-app">
<%--Header--%>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="lc-block">

    <%--Menu--%>
    <c:if test="${userRole == 'ADMIN'}">
        <%@ include file="/WEB-INF/jspf/adminMenu.jspf" %>
    </c:if>
    <c:if test="${userRole == 'DOCTOR'}">
        <div>
            <a href="controller?command=listDischargedPatients">
                <button class="dropbtn" type="button">
                    <fmt:message key="hospital_card.button.discharged_patients"></fmt:message>
                </button>
            </a>
            <a href="controller?command=listPatientsByDoctorId">
                <button class="dropbtn" type="button">
                    <fmt:message key="hospital_card.button.patients"></fmt:message>
                </button>
            </a>
        </div>
        </br>
    </c:if>

    <c:if test="${not empty patients}">
        <table id="patients" class="tablesorter">
            <thead>
            <tr>
                <th><fmt:message key="patients.table.first_name"/></th>
                <th><fmt:message key="patients.table.last_name"/></th>
                <th><fmt:message key="patients.table.birthday"/></th>
                <th>count</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="patient" items="${patients}">
                <tr>
                        <%--Custom tag--%>
                    <patient:patientTable patient="${patient}"/>
                    <td>
                        <c:if test="${userRole == 'ADMIN' && patient.doctorId == 0}">
                            <a href="controller?command=listDoctors&patientId=${patient.id}">
                                <button class="button red small" type="button">
                                    <fmt:message key="patients.button.set_doctor"/>
                                </button>
                            </a>

                            <a href="controller?command=listDoctors&patientId=${patient.id}">
                                <button class="button red small" type="button">
                                    <fmt:message key="patients.button.set_doctor"/>
                                </button>
                            </a>

                            <a href="controller?command=listPatientsByBirthday">
                                <button class="dropbtn" type="button">
                                    Patients Adult and Child
                                </button>
                            </a>
                        </c:if>

                        <c:if test="${userRole == 'DOCTOR' || userRole == 'NURSE'}">
                            <form action="controller" method="get">
                                <input type="hidden" name="command" value="hospitalCard">
                                <input type="hidden" name="hospitalCardId" value="${patient.cardId}">
                                <fmt:message key="patients.submit.hospital_card" var="hospitalCard"/>
                                <input type="submit" value="${hospitalCard}" class="button red small">
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<script type="text/javascript">
    $(document).ready(function () {
            $("#patients").tablesorter();
        }
    );
</script>
</body>
</html>