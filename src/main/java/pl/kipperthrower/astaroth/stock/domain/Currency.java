package pl.kipperthrower.astaroth.stock.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import pl.kipperthrower.astaroth.core.domain.AbstractEntity;

@Entity
@Table(name = "currencies")
public class Currency extends AbstractEntity {

}
