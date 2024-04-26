<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.training-session.list.label.code" path="code"/>
	<acme:list-column code="developer.training-session.list.label.location" path="location"/>
	<acme:list-column code="developer.training-session.list.label.instructor" path="instructor"/>		
	<acme:list-column code="developer.training-session.list.label.draft-mode" path="draftMode"/>			
</acme:list>

<jstl:if test="${_command == 'list-by-training-module'}">
	<acme:button code="developer.training-session.list.button.create" action="/developer/training-session/create?trainingModuleId=${trainingModuleId}"/>
</jstl:if>