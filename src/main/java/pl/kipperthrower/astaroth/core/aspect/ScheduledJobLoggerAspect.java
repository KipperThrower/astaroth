package pl.kipperthrower.astaroth.core.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ScheduledJobLoggerAspect extends Pointcuts {

	private static final Logger LOGGER = Logger
			.getLogger(ScheduledJobLoggerAspect.class);

	@Around("scheduledAnnotation()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			LOGGER.info("Job started");
			return joinPoint.proceed();
		} finally {
			LOGGER.info("Job finished");
		}
	}

}
