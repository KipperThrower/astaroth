package pl.kipperthrower.astaroth.core.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;

import pl.kipperthrower.astaroth.core.dao.DaoFactory;
import pl.kipperthrower.astaroth.core.domain.RuntimeError;

@Service
public class RuntimeErrorService {
	
	@Autowired
	private DaoFactory daoFactory;
	@Autowired
	private UserService userService;
	
	public void logError(HttpServletRequest request, Exception ex) {
		RuntimeError re = mapToRuntimeError(request, ex);
		daoFactory.getDao(RuntimeError.class).save(re);
	}
	
	private RuntimeError mapToRuntimeError(HttpServletRequest request, Exception ex) {
		RuntimeError re = new RuntimeError();
		re.setDate(new Date());
		re.setUrl(request.getRequestURL().toString());
		re.setIp(request.getRemoteAddr());
		re.setHttpMethod(request.getMethod());
		re.setExceptionClass(ex.getClass().getCanonicalName());
		re.setStack(Throwables.getStackTraceAsString(ex));
		re.setUser(userService.getLoggedUser());
		return re;
	}

}
