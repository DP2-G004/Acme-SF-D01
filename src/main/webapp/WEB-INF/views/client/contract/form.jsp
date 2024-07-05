<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="client.contract.form.label.contractCode" path="contractCode" />
	<acme:input-textbox code="client.contract.form.label.providerName"
		path="providerName" />
	<acme:input-textbox code="client.contract.form.label.customerName"
		path="customerName" />
	<acme:input-textbox code="client.contract.form.label.goals" path="goals" />
	<acme:input-money code="client.contract.form.label.budget" path="budget" />
	<acme:input-select code="client.contract.form.label.project"
		path="project" choices="${projects}" />

	<jstl:choose>
		<jstl:when
			test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:input-moment code="client.contract.form.label.instantiation"
		path="instantiation"/>
			<acme:submit code="client.contract.form.button.update"
				action="/client/contract/update" />
			<acme:submit code="client.contract.form.button.delete"
				action="/client/contract/delete" />
			<acme:submit code="client.contract.form.button.publish"
				action="/client/contract/publish?id=${id}" />
			<acme:button code="client.contract.form.button.add-progress"
				action="/client/progress/create?contractId=${id}" />
			<acme:button code="client.contract.form.button.list_progress"
				action="/client/progress/list?contractId=${id}&draft=true" />
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:input-moment code="client.contract.form.label.instantiation"
				path="instantiation"/>
			<acme:submit code="client.contract.form.button.create"
				action="/client/contract/create" />
		</jstl:when>
		<jstl:otherwise> 
			<acme:input-moment code="client.contract.form.label.instantiation"
				path="instantiation" readonly = "true"/>
			<acme:button code="client.contract.form.button.list_progress"
				action="/client/progress/list?contractId=${id}&draft=false" />
		</jstl:otherwise>
	</jstl:choose>

</acme:form>