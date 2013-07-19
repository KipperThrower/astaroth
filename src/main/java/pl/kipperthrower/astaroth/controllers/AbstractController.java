package pl.kipperthrower.astaroth.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import pl.kipperthrower.astaroth.services.ApplicationCacheService;

import freemarker.log.Logger;

public abstract class AbstractController {

	protected Logger log = Logger.getLogger(this.getClass().getName());
	
	@Value( "#{wiringProperties['format.date']}" )
	protected String dateFormat;
	@Value( "#{wiringProperties['buildEnv']}" )
	protected String buildEnv;

	@Autowired
	protected ApplicationCacheService cache;

	@InitBinder
	public void allowEmptyDateBinding( WebDataBinder binder ) {
	    binder.registerCustomEditor( Date.class, new CustomDateEditor( new SimpleDateFormat( dateFormat), true ));
	    binder.registerCustomEditor( String.class, new StringTrimmerEditor( true ));
	}
	
	@ModelAttribute("buildEnv")
	public String getBuildEnv() {
		if ( buildEnv == null ) {
			buildEnv = "Unknown";
		}
		return buildEnv;
	}
	@ModelAttribute("username")
	public String setUsername() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if ( username.equals("anonymousUser")) {
			return null;
		} else {
			return username;
		}
	}
}
