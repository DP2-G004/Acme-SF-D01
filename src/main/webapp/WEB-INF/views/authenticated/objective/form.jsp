<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form >
	<acme:input-moment code="authenticated.objective.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textbox code="authenticated.objective.form.label.title" path="title"/>	
	<acme:input-textbox code="authenticated.objective.form.label.description" path="description"/>
	<acme:input-textbox code="authenticated.objective.form.label.priority" path="priority"/>	
	<acme:input-textbox code="authenticated.objective.form.label.status" path="status"/>	
	<acme:input-moment code="authenticated.objective.form.label.startDate" path="startDate"/>	
	<acme:input-moment code="authenticated.objective.form.label.endDate" path="endDate"/>	
	<acme:input-url code="authenticated.objective.form.label.link" path="link"/>
</acme:form>

<jstl:if test="${_command == 'create'}">
	<acme:submit code="authenticated.objective.form.button.create" action="/authenticated/objective/create"/>
</jstl:if>	