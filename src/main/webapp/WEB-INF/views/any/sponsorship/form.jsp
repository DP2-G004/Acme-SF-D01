<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="false">
	<acme:input-textbox code="any.sponsorship.form.label.code" path="code" readonly="True"/>
	<acme:input-moment code="any.sponsorship.form.label.moment" path="moment" readonly="True"/>
	<acme:input-moment code="any.sponsorship.form.label.startDate" path="startDate" readonly="True"/>
	<acme:input-moment code="any.sponsorship.form.label.endDate" path="endDate" readonly="True"/>
	<acme:input-money code="any.sponsorship.form.label.amount" path="amount" readonly="True"/>
	<acme:input-select code="any.sponsorship.form.label.type" path="type" choices="${types}" readonly="True"/>
	<acme:input-email code="any.sponsorship.form.label.email" path="email" readonly="True"/>
	<acme:input-url code="any.sponsorship.form.label.link" path="link" readonly="True"/>
	<acme:input-select code="any.sponsorship.form.label.project" path="project" choices="${projects}" readonly="True"/>
    
</acme:form>