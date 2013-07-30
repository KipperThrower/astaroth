package pl.kipperthrower.astaroth.core.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
	
	@Pointcut("execution(public * pl.kipperthrower.astaroth.*.service.*(..))")
	public void getServicePublicMethods() {}
	
	@Pointcut("execution(public * pl.kipperthrower.astaroth.*.controller.*(..))")
	public void getControllerPublicMethods() {}
	
	@Pointcut("execution(@pl.kipperthrower.astaroth.core.annotation.LogExecution * *(..))")
	public void logAnnotation() {}
	
	@Pointcut("execution(@pl.kipperthrower.astaroth.core.annotation.MeasureTime * *(..))")
	public void timeMeasureAnnotation() {}
	
	@Pointcut("execution(@org.springframework.scheduling.annotation.Scheduled * *(..))")
	public void scheduledAnnotation() {}

}
