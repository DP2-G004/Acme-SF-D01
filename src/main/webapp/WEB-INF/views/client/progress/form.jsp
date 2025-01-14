<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	

	<acme:input-textbox code="client.progress.form.label.record" path="record"/>
	<acme:input-double code="client.progress.form.label.completeness" path="completeness"/>
	<acme:input-textbox code="client.progress.form.label.comment" path="comment"/>
	<acme:input-textbox code="client.progress.form.label.responsable" path="responsable"/>
	<acme:input-moment code="client.progress.form.label.registration" path="registration"/>
		
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="client.progress.form.button.update" action="/client/progress/update"/>
			<acme:submit code="client.progress.form.button.delete" action="/client/progress/delete"/>
			<acme:submit code="client.progress.form.button.publish" action="/client/progress/publish?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="client.progress.form.button.create" action="/client/progress/create?contractId=${contractId}"/>
		</jstl:when>
	</jstl:choose>
			
</acme:form>