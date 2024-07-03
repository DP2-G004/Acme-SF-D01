<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="client.contract.list.label.contractCode" path="contractCode"/>
	<acme:list-column code="client.contract.list.label.draftMode" path="draftMode"/>
	<acme:list-column code="client.contract.list.label.project" path="project" width="10%"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="client.contract.list.button.create" action="/client/contract/create"/>
</jstl:if>