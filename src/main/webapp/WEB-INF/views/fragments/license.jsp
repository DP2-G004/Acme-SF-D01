<%--
- license.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
-
-You may use this system free of charge, as longlong as you carefully observe the
-existing aplicable laws. Please, note that we do not share any information
-about you with any other company. We store the following items in your computer:
-"JSESSIONID" to identify your work session, "remember" to let you authenticate
-automatically, "language" to store your lasnguage preferences, "XSRF-TOKEN" to
-deal with CSRF attacks, and "returnStack" to know wher to return when you press
-some buttoms.
-
-This is a student group project from the Desing and Texting subject, your information will
-only be used with learning purposes.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h1><acme:message code="master.license.title"/></h1>
<p><acme:message code="master.license.text"/></p>
