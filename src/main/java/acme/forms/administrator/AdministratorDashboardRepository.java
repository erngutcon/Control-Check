package acme.forms.administrator;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.patronage.PatronageReport;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {


	// Manage COMPONENTS
	@Query("select count(i) from Item i where i.type = 'COMPONENT'")
	Integer totalNumberOfComponents();

	@Query("select i.retailPrice.currency, i.retailPrice.currency, avg(i.retailPrice.amount) from Item i where i.type = 'COMPONENT' group by i.retailPrice.currency")
	List<Object> averageRetailPriceOfComponents();

	@Query("select i.retailPrice.currency, stddev(i.retailPrice.amount) from Item i where i.type = 'COMPONENT' group by i.retailPrice.currency")
	List<Object> deviationRetailPriceOfComponents();
	
	@Query("select i.retailPrice.currency, min(i.retailPrice.amount) from Item i where i.type = 'COMPONENT' group by i.retailPrice.currency")
	List<Object> minimumRetailPriceOfComponents();

	@Query("select i.retailPrice.currency, max(i.retailPrice.amount) from Item i where i.type = 'COMPONENT' group by i.retailPrice.currency")
	List<Object> maximumRetailPriceOfComponents();
	
	// Manage TOOLS
	@Query("select count(i) from Item i where i.type = 'TOOL'")
	Integer totalNumberOfTools();
	
	@Query("select i.retailPrice.currency, avg(i.retailPrice.amount) from Item i where i.type = 'TOOL' group by i.retailPrice.currency")
	List<Object> averageRetailPriceOfTools();

	@Query("select i.retailPrice.currency, stddev(i.retailPrice.amount) from Item i where i.type = 'TOOL' group by i.retailPrice.currency")
	List<Object> deviationRetailPriceOfTools();

	@Query("select i.retailPrice.currency, min(i.retailPrice.amount) from Item i where i.type = 'TOOL' group by i.retailPrice.currency")
	List<Object> minimumRetailPriceOfTools();
	
	@Query("select i.retailPrice.currency, max(i.retailPrice.amount) from Item i where i.type = 'TOOL' group by i.retailPrice.currency")
	List<Object> maximumRetailPriceOfTools();
	
	// Manage TOTALS
	@Query("select count(p) from Patronage p where p.status = 'PROPOSED'")
	Integer totalNumberOfProposedPatronages();

	@Query("select count(p) from Patronage p where p.status = 'ACCEPTED'")
	Integer totalNumberOfAcceptedPatronages();

	@Query("select count(p) from Patronage p where p.status = 'DENIED'")
	Integer totalNumberOfDeniedPatronages();

	// Manage PROPOSED
	@Query("select p.budget.currency, avg(p.budget.amount) from Patronage p where p.status = 'PROPOSED' group by p.budget.currency")
	List<Object> averageBudgetOfProposedPatronages();

	@Query("select p.budget.currency, stddev(p.budget.amount) from Patronage p where p.status = 'PROPOSED' group by p.budget.currency")
	List<Object> deviationBudgetOfProposedPatronages();

	@Query("select p.budget.currency, min(p.budget.amount) from Patronage p where p.status = 'PROPOSED' group by p.budget.currency")
	List<Object> minimumBudgetOfProposedPatronages();

	@Query("select p.budget.currency, max(p.budget.amount) from Patronage p where p.status = 'PROPOSED' group by p.budget.currency")
	List<Object> maximumBudgetOfProposedPatronages();

	// Manage ACCEPTED
	@Query("select p.budget.currency, avg(p.budget.amount) from Patronage p where p.status = 'ACCEPTED' group by p.budget.currency")
	List<Object> averageBudgetOfAcceptedPatronages();

	@Query("select p.budget.currency, stddev(p.budget.amount) from Patronage p where p.status = 'ACCEPTED' group by p.budget.currency")
	List<Object> deviationBudgetOfAcceptedPatronages();

	@Query("select p.budget.currency, min(p.budget.amount) from Patronage p where p.status = 'ACCEPTED' group by p.budget.currency")
	List<Object> minimumBudgetOfAcceptedPatronages();

	@Query("select p.budget.currency, max(p.budget.amount) from Patronage p where p.status = 'ACCEPTED' group by p.budget.currency")
	List<Object> maximumBudgetOfAcceptedPatronages();

	// Manage DENIED
	@Query("select p.budget.currency, avg(p.budget.amount) from Patronage p where p.status = 'DENIED' group by p.budget.currency")
	List<Object> averageBudgetOfDeniedPatronages();

	@Query("select p.budget.currency, stddev(p.budget.amount) from Patronage p where p.status = 'DENIED' group by p.budget.currency")
	List<Object> deviationBudgetOfDeniedPatronages();

	@Query("select p.budget.currency, min(p.budget.amount) from Patronage p where p.status = 'DENIED' group by p.budget.currency")
	List<Object> minimumBudgetOfDeniedPatronages();

	@Query("select p.budget.currency, max(p.budget.amount) from Patronage p where p.status = 'DENIED' group by p.budget.currency")
	List<Object> maximumBudgetOfDeniedPatronages();
	
	//Control Check
	@Query("select 1.0 * count(c)/ (select count(i) FROM Item i) from Comema c, Item i where i.id=c.item.id and c.item.type = 'COMPONENT' and c.item.id!=NULL")
	Double ratioOfComponentsWithComemas();
	
	@Query("select c.income.currency, avg(c.income.amount) from Comema c group by c.income.currency")
	List<Object> averageIncomeOfComemas();

	@Query("select c.income.currency, stddev(c.income.amount) from Comema c group by c.income.currency")
	List<Object> deviationIncomeOfComemas();

	@Query("select c.income.currency, min(c.income.amount) from Comema c group by c.income.currency")
	List<Object> minimumIncomeOfComemas();

	@Query("select c.income.currency, max(c.income.amount) from Comema c group by c.income.currency")
	List<Object> maximumIncomeOfComemas();
	
}