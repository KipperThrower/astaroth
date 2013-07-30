package pl.kipperthrower.astaroth.core.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Service
public class TestTask {

	@Scheduled(cron = "0/1 * * * * ?")
	public void runTask() {
		System.out.println();
	}

}
