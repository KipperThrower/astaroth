package pl.kipperthrower.astaroth.core.aspect;

import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import pl.kipperthrower.astaroth.core.domain.User;
import pl.kipperthrower.astaroth.core.service.RuntimeErrorService;
import pl.kipperthrower.astaroth.core.service.UserService;


@ControllerAdvice
public class BaseControllerAdvice {
	
	private static  final Logger LOGGER = Logger.getLogger(BaseControllerAdvice.class);
	
	@Value( "#{wiringProperties['format.date']}" )
	private String dateFormat;
	@Value( "#{wiringProperties['buildEnv']}" )
	private String buildEnv;
	@Autowired
	private UserService userService;
	@Autowired
	private RuntimeErrorService runtimeErrorService;

	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(HttpServletRequest request, Exception ex) {
		runtimeErrorService.logError(request, ex);
		LOGGER.error(ex, ex);
		return null;
	}
	
	@InitBinder
	public void initBinding( WebDataBinder binder ) {
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
	public String getUsername() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if ( username.equals("anonymousUser")) {
			return null;
		} else {
			return username;
		}
	}
	
	@ModelAttribute("user")
	public User getUser() {
		return userService.getLoggedUser();
	}
	
	
}
