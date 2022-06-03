package acme.features.inventor.comema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Comema;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorComemaShowService implements AbstractShowService<Inventor, Comema> {

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
	public Comema findOne(final Request<Comema> request) {
		assert request != null;

		int id;
		Comema result;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneComemaById(id);

		return result;
	}
	
	@Override
	public void unbind(final Request<Comema> request, final Comema entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "creationMoment", "subject", "explanation", "startPeriod", "finishPeriod", "income", "moreInfo");
	}
}