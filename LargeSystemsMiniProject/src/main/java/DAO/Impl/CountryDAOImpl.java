package DAO.Impl;

import DAO.CountryDAO;
import Model.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 17-05-2015.
 */
public class CountryDAOImpl implements CountryDAO {
    @Resource
    private SessionFactory sessionFactory;

    public void delete(String name) {
        getCurrentSession().delete(get(name));
    }

    public Country get(String name) {
        return (Country) getCurrentSession().createCriteria(Country.class).
                add(Restrictions.eq("name", name)).
                uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Country> listAllCountries() {
        return (List<Country>) getCurrentSession().createCriteria(Country.class).list();
    }

    public void save(Country country) {
        getCurrentSession().saveOrUpdate(country);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
