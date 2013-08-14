package pl.kipperthrower.astaroth.core.listener;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.kipperthrower.astaroth.core.domain.EntityRevision;

public class EntityRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object paramObject) {
		EntityRevision revision = (EntityRevision) paramObject;
		revision.setUsername(SecurityContextHolder.getContext()
				.getAuthentication().getName());
	}

}
