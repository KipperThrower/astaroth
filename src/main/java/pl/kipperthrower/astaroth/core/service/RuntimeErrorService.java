package pl.kipperthrower.astaroth.core.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Throwables;

import pl.kipperthrower.astaroth.core.dao.DaoFactory;
import pl.kipperthrower.astaroth.core.domain.RuntimeError;

@Service
public class RuntimeErrorService {
	
	private static final Logger LOGGER = Logger.getLogger(RuntimeErrorService.class);
	
	@Autowired
	private DaoFactory daoFactory;
	@Autowired
	private UserService userService;
	
	@Transactional
	public void logError(HttpServletRequest request, Exception ex) {
		RuntimeError re = mapToRuntimeError(request, ex);
		daoFactory.getDao(RuntimeError.class).save(re);
	}
	
	private RuntimeError mapToRuntimeError(HttpServletRequest request, Exception ex) {
		RuntimeError re = new RuntimeError();
		re.setDate(new Date());
		re.setUrl(request.getRequestURL().toString());
		re.setQueryString(request.getQueryString());
		re.setIp(request.getRemoteAddr());
		re.setHttpMethod(request.getMethod());
		re.setExceptionClass(ex.getClass().getCanonicalName());
		re.setStack(Throwables.getStackTraceAsString(ex));
		re.setUser(userService.getLoggedUser());
		re.setCharset(request.getCharacterEncoding());
		try {
			String body = IOUtils.toString(request.getInputStream(), Charsets.UTF_8);
			re.setRequestBody(body);
		} catch (IOException e) {
			LOGGER.error(e, e);
		}
		return re;
	}

}
