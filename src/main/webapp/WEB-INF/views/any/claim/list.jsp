<%--
- list.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.claim.list.label.code" path="code" width="10%"/>
	<acme:list-column code="any.claim.list.label.instantion-moment" path="instantiationMoment" width="10%"/>
	<acme:list-column code="any.claim.list.label.heading" path="heading" width="10%"/>	
	<acme:list-column code="any.claim.list.label.description" path="description" width="10%"/>	
	<acme:list-column code="any.claim.list.label.department" path="department" width="10%"/>	
	<acme:list-column code="any.claim.list.label.email" path="email" width="10%"/>	
	<acme:list-column code="any.claim.list.label.link" path="link" width="10%"/>	
	<acme:list-column code="any.claim.list.label.publishIndication" path="publishIndication" width="10%"/>			
</acme:list>

<acme:button code="any.claim.label.create" action="/any/claim/create"/>

