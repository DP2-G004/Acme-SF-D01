<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.contract.list.label.code" path="code"/>
	<acme:list-column code="authenticated.contract.list.label.draftMode" path="draftMode"/>
</acme:list>