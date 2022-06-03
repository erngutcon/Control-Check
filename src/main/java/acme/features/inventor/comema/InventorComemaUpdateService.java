package acme.features.inventor.comema;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Comema;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;

@Service
public class InventorComemaUpdateService implements AbstractUpdateService<Inventor, Comema> {
	
	@Autowired
	protected InventorComemaRepository repository;
	
	@Override
	public boolean authorise(final Request<Comema> request) {
		assert request != null;
		
		boolean result;
		Comema chimpum;
		int id;
		
		id = request.getModel().getInteger("id");
		chimpum = this.repository.findOneComemaById(id);
		result = chimpum.getItem().getInventor().getId() == request.getPrincipal().getActiveRoleId();
		return result;
	}
	
	@Override
	public void bind(final Request<Comema> request, final Comema entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "code", "subject", "explanation", "startPeriod", "finishPeriod", "income", "moreInfo");
	}
	
	@Override
	public void unbind(final Request<Comema> request, final Comema entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "subject", "explanation", "startPeriod", "finishPeriod", "income", "moreInfo");
	}
	
	@Override
	public Comema findOne(final Request<Comema> request) {
		assert request != null;

		Comema result;
		int id;
		id = request.getModel().getInteger("id");
		result = this.repository.findOneComemaById(id);

		return result;
	}
	
	@Override
	public void validate(final Request<Comema> request, final Comema entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		if (!errors.hasErrors("startPeriod")) {
			Calendar calendar;

			calendar = new GregorianCalendar();
			calendar.add(Calendar.MONTH, 1);
			errors.state(request, entity.getStartPeriod().after(calendar.getTime()), "startPeriod", "inventor.comema.form.error.too-close-start-period");
		}
		if (!errors.hasErrors("finishPeriod")) {
			Calendar calendar;
			Date finish;
			calendar = new GregorianCalendar();
			calendar.setTime(entity.getStartPeriod());
			calendar.add(Calendar.DAY_OF_MONTH, 7);
			finish = calendar.getTime();
			errors.state(request, entity.getFinishPeriod().after(finish), "finishPeriod", "inventor.comema.form.error.one-week");
		}
		if(!errors.hasErrors("income")) {
			final String upperCaseCurrency = entity.getIncome().getCurrency().toUpperCase();
			boolean accepted = false;
			
			// Manage likely currencies
			for (final String acceptedCurrency : this.repository.findConfiguration().getAcceptedCurrencies().toUpperCase().split("[.]")) {
				if (upperCaseCurrency.equals(acceptedCurrency)) {
					accepted = true;
					break;
				}
			}
			errors.state(request, entity.getIncome().getAmount() > 0, "income", "inventor.comema.form.error.negative-budget");
			errors.state(request, accepted, "income", "inventor.comema.form.error.non-accepted-currency");
		}
	}
	
	@Override
	public void update(final Request<Comema> request, final Comema entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}
}