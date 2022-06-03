package acme.features.inventor.comema;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Comema;
import acme.entities.Quantity;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorComemaDeleteService implements AbstractDeleteService<Inventor, Comema> {

	@Autowired
	protected InventorComemaRepository repository;
			
	@Override
	public boolean authorise(final Request<Comema> request) {
		assert request != null;
		
		boolean result;
		int id;
		Comema chimpum;

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
	}
	
	@Override
	public void delete(final Request<Comema> request, final Comema entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
	}  
}