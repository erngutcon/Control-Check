package acme.features.inventor.comema;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Comema;
import acme.entities.Item;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorComemaCreateService implements AbstractCreateService<Inventor, Comema> {
	
	@Autowired
	protected InventorComemaRepository repository;
	
	@Override
	public boolean authorise(final Request<Comema> request) {
		assert request != null;
		
		boolean result;
		
		result = request.getPrincipal().hasRole(Inventor.class);
		
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
		model.setAttribute("itemId", entity.getItem().getId());
	}
	
	@Override
	public Comema instantiate(final Request<Comema> request) {
		assert request != null;
		
		Comema result;
		Item item;
		int itemId;
		
		result = new Comema();
		
		// Manage unique code
		String code = "";

		code = this.createCode();
		result.setCode(code);
		
		Date moment = new Date(System.currentTimeMillis() - 1000);
		result.setCreationMoment(moment);
		
		itemId = request.getModel().getInteger("itemId");
		item = this.repository.findOneItemById(itemId);
		result.setItem(item);
		
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
	public void create(final Request<Comema> request, final Comema entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);			
	}
	
	
	
	
	// Other business methods -------------------------
	
		public String numbersSecuency() {
	
			final char[] elementos = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
			final char[] conjunto = new char[2];
	
			final String secuency;
	
			for (int i = 0; i < 2; i++) {
				final int el = (int) (Math.random() * 9);
				conjunto[i] = elementos[el];
			}
	
			secuency = new String(conjunto);
			return secuency;
	
		}
		
		public String lettersSecuency() {

			final char[] elementos = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
					'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

			final char[] conjunto = new char[2];

			final String secuency;

			for (int i = 0; i < 2; i++) {
				final int el = (int) (Math.random() * 25);
				conjunto[i] = elementos[el];
			}

			secuency = new String(conjunto).toUpperCase();
			return secuency;

		}

		public String createCode() {

			// The ticker must be as follow: YYMMDD
			String code = new String();

			// Get creation date
			final Calendar calendar = Calendar.getInstance();
			final String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
			String month = "";
			String day = "";

			if (calendar.get(Calendar.MONTH) + 1 < 10)
				month = "0" + (calendar.get(Calendar.MONTH) + 1);
			else
				month = String.valueOf(calendar.get(Calendar.MONTH) + 1);

			if (calendar.get(Calendar.DAY_OF_MONTH) < 10)
				day = "0" + calendar.get(Calendar.DAY_OF_MONTH);
			else
				day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

			code = lettersSecuency() + numbersSecuency() + lettersSecuency() + "-" + month + day + year ;

			return code;

		}
}