
package acme.features.client.clientDashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.datatypes.Statistics;
import acme.entities.contract.Contract;
import acme.entities.contract.Progress;
import acme.forms.ClientDashboard;
import acme.roles.client.Client;

@Service
public class ClientDashboardShowService extends AbstractService<Client, ClientDashboard> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected ClientDashboardRepository repository;

	// AbstractService Interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		Principal principal = super.getRequest().getPrincipal();
		int id = principal.getAccountId();
		Client client = this.repository.findClientById(id);
		status = client != null && principal.hasRole(Client.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();
		Collection<Progress> progressLogs;
		Collection<Contract> contracts;
		progressLogs = this.repository.findProgressByClientId(userAccountId);
		contracts = this.repository.findAllContractsByClientId(userAccountId);

		int totalNumProgressLogLessThan25 = 0;
		int totalNumProgressLogLessBetween25And50 = 0;
		int totalNumProgressLogLessBetween50And75 = 0;
		int totalNumProgressLogAbove75 = 0;
		if (!progressLogs.isEmpty()) {
			totalNumProgressLogLessThan25 = this.repository.totalNumProgressLogLessThan25(userAccountId);
			totalNumProgressLogLessBetween25And50 = this.repository.totalNumProgressLogLessBetween25And50(userAccountId);
			totalNumProgressLogLessBetween50And75 = this.repository.totalNumProgressLogLessBetween50And75(userAccountId);
			totalNumProgressLogAbove75 = this.repository.totalNumProgressLogAbove75(userAccountId);
		}

		double findAverageContractBudget = Double.NaN;
		double findDeviationContractBudget = Double.NaN;
		double findMaximumContractBudget = Double.NaN;
		double findMinimumContractBudget = Double.NaN;
		if (!contracts.isEmpty()) {
			findAverageContractBudget = this.repository.findAverageContractBudget(userAccountId);
			findDeviationContractBudget = this.repository.findDeviationContractBudget(userAccountId);
			findMaximumContractBudget = this.repository.findMaximumContractBudget(userAccountId);
			findMinimumContractBudget = this.repository.findMinimumContractBudget(userAccountId);
		}

		final Statistics contractTimeStatistics = new Statistics();
		contractTimeStatistics.setAverage(findAverageContractBudget);
		contractTimeStatistics.setDeviation(findDeviationContractBudget);
		contractTimeStatistics.setMaximum(findMaximumContractBudget);
		contractTimeStatistics.setMinimum(findMinimumContractBudget);

		final ClientDashboard dashboard = new ClientDashboard();

		dashboard.setTotalNumProgressLogLessThan25(totalNumProgressLogLessThan25);
		dashboard.setTotalNumProgressLogLessBetween25And50(totalNumProgressLogLessBetween25And50);
		dashboard.setTotalNumProgressLogLessBetween50And75(totalNumProgressLogLessBetween50And75);
		dashboard.setTotalNumProgressLogAbove75(totalNumProgressLogAbove75);
		dashboard.setContractTimeStatistics(contractTimeStatistics);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ClientDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumProgressLogLessThan25", "totalNumProgressLogLessBetween25And50", // 
			"totalNumProgressLogLessBetween50And75", "totalNumProgressLogAbove75", "contractTimeStatistics");

		super.getResponse().addData(dataset);
	}

}
