<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.num-invoices-with-tax-less-or-equal-than-21"/>
		</th>
		<td>
			<acme:print value="${numInvoicesWithTaxLessOrEqualThan21}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.num-sponshorship-with-link"/>
		</th>
		<td>
			<acme:print value="${numSponshorshipWithLink}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.min-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${minInvoiceQuantity}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.max-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${maxInvoiceQuantity}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.min-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${minSponsorshipAmount}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.max-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${maxSponsorshipAmount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.avg-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${avgSponsorshipAmount}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.deviation-sponsorship-amount"/>
		</th>
		<td>
			<acme:print value="${deviationSponsorshipAmount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.avg-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${avgInvoiceQuantity}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="sponsor.dashboard.form.label.deviation-invoice-quantity"/>
		</th>
		<td>
			<acme:print value="${deviationInvoiceQuantity}"/>
		</td>
	</tr>
	
</table>
<acme:return/>