
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>
<%--Head--%>
<c:set var="title" value="Login"></c:set>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body class="security-app" align="justify">

<%--===========================================================================
This is the HEADER, containing a top menu.
header.jspf contains all necessary functionality for it.
Just included it in this JSP document.
===========================================================================--%>

    <%-- HEADER --%>
    <%@ include file="/WEB-INF/jspf/header.jspf"%>
    <%-- HEADER --%>
<%--===========================================================================
This is the CONTENT, containing the main part of the page.
===========================================================================--%>
    <div class="lc-block">
        <c:if test="${not empty error}">
            <c:out value="${error}"></c:out>
        </c:if>
        <form action="controller" method="post">
            <input type="hidden" name="command" value="login" />

            <div class="wrapper">
                <div class="material-textfield blue">
                    <fmt:message key="login.login" var="login" />
                    <input type="text" name="login"  style="text-align:center" required /> <label data-content="${login}">login</label>
                </div>

                <div class="material-textfield red">
                    <fmt:message key="login.password" var="password" />
                    <input type="password" name="password"  style="text-align:center" required /> <label data-content="${password}">password</label>
                </div>
            </div>

            <fmt:message key="login.submit" var="submit" />
            <input type="submit" value="${submit}" class="dropbtn">
        </form>

    </div>

</body>
</html>
