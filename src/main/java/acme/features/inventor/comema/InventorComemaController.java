package acme.features.inventor.comema;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Comema;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorComemaController extends AbstractController<Inventor, Comema> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected InventorComemaListService listService;

	@Autowired
	protected InventorComemaShowService showService;
	
	@Autowired
	protected InventorComemaCreateService createService;
	
	@Autowired
	protected InventorComemaUpdateService updateService;
	
	@Autowired
	protected InventorComemaDeleteService deleteService;
	
	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
		super.addCommand("create", this.createService);
		super.addCommand("update", this.updateService);
		super.addCommand("delete", this.deleteService);
	}
}