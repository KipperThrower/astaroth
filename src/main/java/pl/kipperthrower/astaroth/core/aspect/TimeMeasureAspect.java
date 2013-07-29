package pl.kipperthrower.astaroth.core.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

@Aspect
public class TimeMeasureAspect extends Pointcuts {

	private static final Logger LOGGER = Logger
			.getLogger(TimeMeasureAspect.class);

	@Around("timeMeasureAnnotation()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch sw = new StopWatch(joinPoint.getClass().getSimpleName());
		try {
			sw.start(joinPoint.getSignature().getName());
			return joinPoint.proceed();
		} finally {
			sw.stop();
			LOGGER.debug(sw.prettyPrint());
		}
	}

}
