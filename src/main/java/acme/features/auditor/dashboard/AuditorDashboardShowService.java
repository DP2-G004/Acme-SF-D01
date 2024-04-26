
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
		averageNumAuditRecords = this.repository.averageAuditingRecords(auditorId) != null ? this.repository.averageAuditingRecords(auditorId) : 0.;
		deviationNumAuditRecords = this.computeDeviation(auditingRecordsPerAudit) != null ? this.computeDeviation(auditingRecordsPerAudit) : 0.;
		minNumAuditRecords = this.repository.minAuditingRecords(auditorId) != null ? this.repository.minAuditingRecords(auditorId) : 0;
		maxNumAuditRecords = this.repository.maxAuditingRecords(auditorId) != null ? this.repository.maxAuditingRecords(auditorId) : 0;
		averagePeriodLength = this.repository.averageRecordPeriod(auditorId) != null ? this.repository.averageRecordPeriod(auditorId) : 0.;
		deviationPeriodLength = this.repository.deviationRecordPeriod(auditorId) != null ? this.repository.deviationRecordPeriod(auditorId) : 0.;
		minPeriodLength = this.repository.minimumRecordPeriod(auditorId) != null ? this.repository.minimumRecordPeriod(auditorId) : 0;
		maxPeriodLength = this.repository.maximumRecordPeriod(auditorId) != null ? this.repository.maximumRecordPeriod(auditorId) : 0;

		dashboard = new AuditorDashboard();
		dashboard.setNumAuditsPorTipo(numAuditsPorTipo);
		dashboard.setNumTotalCodeAudits(numTotalCodeAudits);
		dashboard.setAverageNumAuditRecords(averageNumAuditRecords);
		dashboard.setDeviationNumAuditRecords(deviationNumAuditRecords);
		dashboard.setMinNumAuditRecords(minNumAuditRecords);
		dashboard.setMaxNumAuditRecords(maxNumAuditRecords);
		dashboard.setAveragePeriodLength(averagePeriodLength);
		dashboard.setDeviationPeriodLength(deviationPeriodLength);
		dashboard.setMinPeriodLength(minPeriodLength);
		dashboard.setMaxPeriodLength(maxPeriodLength);

		super.getBuffer().addData(dashboard);
	}
	@Override
	public void unbind(final AuditorDashboard object) {
		assert object != null;

		Dataset dataset;
		Integer totalNumCodeAuditsStatic;
		Integer totalNumCodeAuditsDynamic;

		totalNumCodeAuditsStatic = object.getNumAuditsPorTipo().get(CodeAuditType.STATYC) != null ? object.getNumAuditsPorTipo().get(CodeAuditType.STATYC) : 0;
		totalNumCodeAuditsDynamic = object.getNumAuditsPorTipo().get(CodeAuditType.DINAMYC) != null ? object.getNumAuditsPorTipo().get(CodeAuditType.DINAMYC) : 0;

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
