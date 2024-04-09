<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<%-- ESTO ES UN COMENTARIO; ESTO ES LA COPIA DEL FORM:JSP DE BANNER; HAY QUE MODIFICARLO --%>

<acme:list>
	<acme:list-column code="administrator.risk.list.label.impact" path="impact"/>	
	<acme:list-column code="administrator.risk.list.label.probability" path="probability"/>
	<acme:list-column code="administrator.risk.list.label.description" path="description"/>
</acme:list>