<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.sponsorship.list.label.code" path="code"/>
	<acme:list-column code="sponsor.sponsorship.list.label.amount" path="amount"/>
	<acme:list-column code="sponsor.sponsorship.list.label.type" path="type"/>
</acme:list>

<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="sponsor.sponsorship.list.button.create" action="/sponsor/sponsorship/create"/>
</jstl:if>
