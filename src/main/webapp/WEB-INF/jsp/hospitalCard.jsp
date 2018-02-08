<%--
  Created by IntelliJ IDEA.
  User: akopvardanian
  Date: 21.01.2018
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<%@ taglib prefix="treatment" tagdir="/WEB-INF/tags"%>

<html>
<%--Head--%>
<c:set var="title" scope="request" value="Hospital card"/>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body class="security-app">
<%--Header--%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<div class="lc-block">

    <%--Menu--%>
        <c:if test="${userRole == 'DOCTOR'}">
            <div>
                <a href="controller?command=listDischargedPatients" ><button class="dropbtn" type="button"><fmt:message key="hospital_card.button.discharged_patients"/></button></a>
                <a href="controller?command=listPatientsByDoctorId"><button class="dropbtn" type="button"><fmt:message key="hospital_card.button.patients"/></button></a>
            </div>
        </c:if>
        <c:if test="${userRole == 'NURSE'}">
            <a href="controller?command=listPatients"><button class="dropbtn" type="button"><fmt:message key="hospital_card.button.patients"/></button></a>
        </c:if>
        <br>
        <br>

    <%--Error message--%>
        <c:if test="${not empty errorMessage}">
            <p>
                <c:out value="${requestScope.errorMessage}" />
            </p>
        </c:if>

        <h3><fmt:message key="hospital_card.diagnosis"/> : ${hospitalCard.diagnosis} </h3>

    <%--Doctor actions--%>
<c:if test="${userRole == 'DOCTOR'}">
    <fmt:message key="hospital_card.button.change" var="change"/>
    <input type="button" name="ok" value="${change}" onclick="showField();" class="button red small">

    <form action="controller" method="post">
    <input type="hidden" name="command" value="hospitalCard">

    <div align="center">
    <input type="text" name='diagnosis' id="field" value="${hospitalCard.diagnosis}" style="display:none; text-align:center">

    <fmt:message key="hospital_card.submit.edit" var="edit"/>
    <input type="submit" id="submit" value="${edit}" style="display:none;" class="button red small">
    </div>
    </form>
    <br>

    <%--Add treatment form--%>
    <fmt:message key="hospital_card.button.add_treatment" var="addTreatment"/>
    <input type="button" name="ok" value="${addTreatment}" onclick="showAddTreatment();" class="button red small"><br><br>
    <div id="addTreatment" style="display:none;">
    <form action="controller" method="post">
    <input type="hidden" name="command" value="addTreatment">

    <fmt:message key="hospital_card.select_type_of_treatment"/>:<br>
    <select id="select" name="typeOfTreatmentId">
    <c:forEach var="typeOfTreatment" items="${typesOfTreatments}">
        <option value="${typeOfTreatment.id}">${typeOfTreatment}</option>
    </c:forEach>
    </select><br><br>
    <fmt:message key="hospital_card.pills"/>:<br>
    <div align="center">
    <input id="fieldAdd" type="text" name="pills" style="text-align:center">
    <fmt:message key="hospital_card.submit.add" var="add"/>
    <input id="submitAdd" type="submit" value="${add}" class="button red small"><br>
    </div>
    </form>
</div>

        <form action="controller" method="post">
            <input type="hidden" name="command" value="completeCourseOfTreatment">
            <fmt:message key="hospital_card.submit.complete" var="complete"/>
            <input type="submit" value="${complete}" class="button green middle">
        </form>
        <br>
        </c:if>

        <br>
    <%--Table of treatments--%>
        <c:if test="${not empty treatments}">
            <fmt:message key="hospital_card.treatments"/>:<br>
        <table>
            <thead>
            <tr>
                <th><fmt:message key="hospital_card.table.type_of_treatment"/></th>
                <th><fmt:message key="hospital_card.table.pills"/></th>
                <th><fmt:message key="hospital_card.table.done"/></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="treatment" items="${treatments}">
                <tr>
                    <treatment:treatment treatment="${treatment}"/>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </c:if>
</div>
</body>
<script>
    function showField() {
        document.getElementById('field').style.display='block';
        document.getElementById('submit').style.display='block';
    }

    function showAddTreatment() {
        document.getElementById('addTreatment').style.display='block';
    }
</script>
</html>
