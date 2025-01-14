<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="client.progress.list.label.record" path="record"/>
	<acme:list-column code="client.progress.list.label.draftMode" path="draftMode"/>
	<acme:list-column code="client.progress.list.label.project" path="contract" width="10%"/>
</acme:list>


<jstl:if test="${draft == true}">
	<acme:button code="client.contract.form.button.add-progress"
		action="/client/progress/create?contractId=${masterId}" />
</jstl:if>