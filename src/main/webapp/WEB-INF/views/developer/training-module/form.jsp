<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="developer.trainingModule.form.label.code" path="code"/>
	<acme:input-moment code="developer.trainingModule.form.label.creationMoment" path="creationMoment"/>
	<acme:input-textbox code="developer.trainingModule.form.label.details" path="details"/>
	<acme:input-select code="developer.trainingModule.form.label.difficultyLevel" path="difficultyLevel" choices="${difficultyLevels}"/>
	<acme:input-moment code="developer.trainingModule.form.label.updateMoment" path="updateMoment"/>	
	<acme:input-textbox code="developer.trainingModule.form.label.link" path="link"/>	
	<acme:input-integer code="developer.trainingModule.form.label.totalTime" path="totalTime"/>	
	<acme:input-select code="developer.training-module.form.label.project" path="project" choices="${projects}"/>
	
        <jstl:if test="${_command == 'create'}">
            <acme:submit code="developer.trainingModule.form.button.create" action="/developer/training-module/create"/>
        </jstl:if>
        
        <jstl:if test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
            <acme:submit code="developer.trainingModule.form.button.update" action="/developer/training-module/update"/>
            <acme:submit code="developer.trainingModule.form.button.delete" action="/developer/training-module/delete"/>
            <acme:submit code="developer.trainingModule.form.button.publish" action="/developer/training-module/publish"/>      
        </jstl:if>
</acme:form>