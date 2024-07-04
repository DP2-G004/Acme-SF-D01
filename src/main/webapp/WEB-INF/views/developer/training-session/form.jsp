<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="developer.trainingSession.form.label.code" path="code"/>
	<acme:input-moment code="developer.trainingSession.form.label.startPeriod" path="startPeriod"/>
	<acme:input-moment code="developer.trainingSession.form.label.endPeriod" path="endPeriod"/>
	<acme:input-textbox code="developer.trainingSession.form.label.location" path="location"/>
	<acme:input-textbox code="developer.trainingSession.form.label.instructor" path="instructor"/>
	<acme:input-email code="developer.trainingSession.form.label.email" path="email"/>
	<acme:input-url code="developer.trainingSession.form.label.link" path="link"/>
	
	 <jstl:if test="${_command == 'create'}">
            <acme:submit code="developer.trainingSession.form.button.create" action="/developer/training-session/create?trainingModuleId=${trainingModuleId}"/>
     </jstl:if>
     
     <jstl:if test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
            <acme:submit code="developer.trainingSession.form.button.update" action="/developer/training-session/update"/>
            <acme:submit code="developer.trainingSession.form.button.delete" action="/developer/training-session/delete"/>
            <acme:submit code="developer.trainingSession.form.button.publish" action="/developer/training-session/publish"/>      
        </jstl:if>
        
</acme:form>