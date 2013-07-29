package pl.kipperthrower.astaroth.core.aspect;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect extends Pointcuts {

	private static final Logger LOGGER = Logger.getLogger(LoggingAspect.class);

	@Around("logAnnotation()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			LOGGER.debug(getFormattedMessage("start of execution of %s.%s(%s)",
					joinPoint));
			return joinPoint.proceed();
		} finally {
			LOGGER.debug(getFormattedMessage("end of execution of %s.%s(%s)",
					joinPoint));
		}
	}

	private String getFormattedMessage(String messageTemplate,
			ProceedingJoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		String className = joinPoint.getClass().getCanonicalName();
		String args = Arrays.toString(joinPoint.getArgs());
		return String.format(messageTemplate, className, methodName, args);
	}

}
