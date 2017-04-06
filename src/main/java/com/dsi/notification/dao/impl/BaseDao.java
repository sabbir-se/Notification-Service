package com.dsi.notification.dao.impl;

import com.dsi.notification.util.SessionUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * Created by sabbir on 6/15/16.
 */
public class BaseDao {

    private static final Logger logger = Logger.getLogger(BaseDao.class);

    public Session getSession(){
        Session session = SessionUtil.getSession();
        session.beginTransaction();
        return session;
    }

    public void close(Session session){
        session.getTransaction().commit();
        session.close();
    }

    public void save(Object object) {
        Session session = null;
        try {
            session = getSession();
            session.save(object);

        } catch (Exception e) {
            logger.error("Database error occurs when save: " + e.getMessage());

        } finally {
            if(session != null) {
                close(session);
            }
        }
    }

    public void update(Object object) {
        Session session = null;
        try {
            session = getSession();
            session.update(object);

        } catch (Exception e) {
            logger.error("Database error occurs when update: " + e.getMessage());

        } finally {
            if(session != null) {
                close(session);
            }
        }
    }
}
