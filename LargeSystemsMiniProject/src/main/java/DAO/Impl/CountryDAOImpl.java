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

    public void delete(Country country) {
        getCurrentSession().delete(country);
    }

    public Country get(int id) {
        return (Country) getCurrentSession().createCriteria(Country.class).
                add(Restrictions.eq("id", id)).
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
