package ru.cybern.kinoserver.mobileapi.db.impl;

import ru.cybern.kinoserver.mobileapi.db.IPerformerDAO;
import ru.cybern.kinoserver.mobileapi.db.entities.PerformerEntity;


/**
 * Created by virtuozzo on 19.02.14.
 */
public class PerformerDAO extends HibernateGenericDAO<PerformerEntity, Integer>
        implements IPerformerDAO {
}