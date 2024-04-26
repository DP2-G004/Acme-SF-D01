<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>	
	<acme:list-column code="developer.trainingModule.list.label.code" path="code"/>
	<acme:list-column code="developer.trainingModule.list.label.difficultyLevel" path="difficultyLevel"/>
	<acme:list-column code="developer.trainingModule.list.label.totalTime" path="totalTime"/>
	<acme:list-column code="developer.trainingModule.list.label.draft-mode" path="draftMode"/>
</acme:list>

<acme:button code="developer.trainingModule.list.button.create" action="/developer/training-module/create"/>	