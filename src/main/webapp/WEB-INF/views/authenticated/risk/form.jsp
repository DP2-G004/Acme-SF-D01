<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form >
	<acme:input-textbox code="administrator.risk.form.label.description" path="description"/>
	<acme:input-url code="administrator.risk.form.label.link" path="link"/>
	<acme:input-double code="administrator.risk.form.label.probability" path="probability"/>
	<acme:input-double code="administrator.risk.form.label.impact" path="impact"/>
	<acme:input-moment code="administrator.risk.form.label.identificationDate" path="identificationDate"/>
</acme:form>