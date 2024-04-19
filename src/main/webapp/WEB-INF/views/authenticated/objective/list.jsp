<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.objective.list.label.title" path="title"/>	
	<acme:list-column code="authenticated.objective.list.label.description" path="description"/>	
	<acme:list-column code="authenticated.objective.list.label.status" path="status"/>	
	<acme:list-column code="authenticated.objective.list.label.link" path="link"/>	
</acme:list>

<!-- _command == 'list-mine'  -->
<jstl:if test="${true}">
	<acme:button code="authenticated.objective.list.button.create" action='/authenticated/objective/create'/>
</jstl:if>
