<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="any.project.form.label.code" path="code" readonly="true"/>
	<acme:input-textbox code="any.project.form.label.title" path="title" readonly="true"/>
	<acme:input-textbox code="any.project.form.label.abstract" path="abstract$" readonly="true"/>
	<acme:input-url code="any.project.form.label.link" path="link" readonly="true"/>
	<acme:input-integer code="any.project.form.label.totalCost" path="totalCost" readonly="true"/>				
</acme:form>
