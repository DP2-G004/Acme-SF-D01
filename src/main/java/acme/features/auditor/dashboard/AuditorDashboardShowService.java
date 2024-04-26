
package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.code_audit.CodeAuditType;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	@Autowired
	private AuditorDashboardRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		int auditorId;
		AuditorDashboard dashboard;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		Collection<Double> auditingRecordsPerAudit = this.repository.auditingRecordsPerAudit(auditorId);

		Map<CodeAuditType, Integer> numAuditsPorTipo;
		int numTotalCodeAudits;
		Double averageNumAuditRecords;
		Double deviationNumAuditRecords;
		Integer minNumAuditRecords;
		Integer maxNumAuditRecords;
		Double averagePeriodLength;
		Double deviationPeriodLength;
		Double minPeriodLength;
		Double maxPeriodLength;

		numAuditsPorTipo = this.repository.totalTypes(auditorId);
		numTotalCodeAudits = this.repository.totalCodeAudits(auditorId);
		averageNumAuditRecords = this.repository.averageAuditingRecords(auditorId);
		deviationNumAuditRecords = this.computeDeviation(auditingRecordsPerAudit);
		minNumAuditRecords = this.repository.minAuditingRecords(auditorId);
		maxNumAuditRecords = this.repository.maxAuditingRecords(auditorId);
		averagePeriodLength = this.repository.averageRecordPeriod(auditorId);
		deviationPeriodLength = this.repository.deviationRecordPeriod(auditorId);
		minPeriodLength = this.repository.minimumRecordPeriod(auditorId);
		maxPeriodLength = this.repository.maximumRecordPeriod(auditorId);

		dashboard = new AuditorDashboard();

		super.getBuffer().addData(dashboard);
	}
	@Override
	public void unbind(final AuditorDashboard object) {
		assert object != null;

		Dataset dataset;
		Integer totalNumCodeAuditsStatic;
		Integer totalNumCodeAuditsDynamic;

		totalNumCodeAuditsStatic = object.getNumAuditsPorTipo().get(CodeAuditType.STATYC);
		totalNumCodeAuditsDynamic = object.getNumAuditsPorTipo().get(CodeAuditType.DINAMYC);

		dataset = super.unbind(object, "numTotalCodeAudits", //
			"averageNumAuditRecords", "deviationNumAuditRecords", //
			"minNumAuditRecords", "maxNumAuditRecords", //
			"averagePeriodLength", "deviationPeriodLength", "minPeriodLength", "maxPeriodLength");

		dataset.put("totalNumCodeAuditsTypeDynamic", totalNumCodeAuditsDynamic);
		dataset.put("totalNumCodeAuditsTypeStatic", totalNumCodeAuditsStatic);

		super.getResponse().addData(dataset);
	}
	public Double computeDeviation(final Collection<Double> values) {
		Double res;
		Double aux;
		res = 0.0;
		if (!values.isEmpty()) {
			Double average = this.calculateAverage(values);
			aux = 0.0;
			for (final Double value : values)
				aux += Math.pow(value + average, 2);
			res = Math.sqrt(aux / values.size());
		}
		return res;
	}
	private Double calculateAverage(final Collection<Double> values) {
		double sum = 0.0;
		for (Double value : values)
			sum += value;
		return sum / values.size();
	}

}
