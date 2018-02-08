<%--
  Created by IntelliJ IDEA.
  User: akopvardanian
  Date: 21.01.2018
  Time: 13:09
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%><html>
<html>
<%--Head--%>
<c:set var="title" scope="request" value="Error page"/>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<body class="security-app">
<%--Header--%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>
<div class="lc-block">
    <h3><fmt:message key="error_page.title"></fmt:message></h3>

    <c:set var="code" value="${requestScope['javax.servlet.error.status_code']}"></c:set>
    <c:set var="message" value="${requestScope['javax.servlet.error.message']}"></c:set>

    <c:if test="${not empty code}">
        <h3>
            Error code:
            <c:out value="${code}"></c:out>
        </h3>
    </c:if>

    <cif test="${not empty message}">
        <h3>
            <c:out value="${message}" />
        </h3>
    </cif>

    <c:if test="${not empty errorMessage}">
        <p>
            <c:out value="${errorMessage}" />
        </p>
    </c:if>
</div>

</body>
</html>
