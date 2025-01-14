<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.audit-record.form.label.code" path="code" />
	<acme:input-url code="any.audit-record.form.label.link" path="link" />
	<acme:input-select code="any.audit-record.form.label.mark" path="mark" choices="${marks}" />
	<acme:input-moment code="any.audit-record.form.label.startInstant" path="startInstant" />
	<acme:input-moment code="any.audit-record.form.label.endInstant" path="endInstant" />
	<acme:input-select code="any.audit-record.form.label.code-audit" path="codeAudit" choices="${codeaudits}"/>
	
</acme:form>