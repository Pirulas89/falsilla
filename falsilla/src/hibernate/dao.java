package hibernate;

package com.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hibernate.model.Medicamento;
import com.hibernate.model.SinStock;
import com.hibernate.util.HibernateUtil;

public class MedicamentoDAO {
	

	public void insertMed(Medicamento m) {
		Transaction transaction=null;
			try (Session session=HibernateUtil.getSessionFactory().openSession()) {
				transaction=session.beginTransaction();
				session.persist(m);
				transaction.commit();
			} catch (Exception e) {
				if (transaction!=null) {
					transaction.rollback();
				}
			}
		}
	
	public void deleteMed(int idMedicamentos) {
		Transaction transaction=null;
		Medicamento m=null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction=session.beginTransaction();
			m=session.get(Medicamento.class, idMedicamentos);
			session.remove(m);
			transaction.commit();
		} catch (Exception e) {
			if (transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	public void updateMed(Medicamento m) {
		Transaction transaction=null;
			try (Session session=HibernateUtil.getSessionFactory().openSession()) {
				transaction=session.beginTransaction();
				session.merge(m);
				transaction.commit();
			} catch (Exception e) {
				if (transaction!=null) {
					transaction.rollback();
				}
			}
		}
	
	public static List<Medicamento> selectAllMed() {
		Transaction transaction=null;
		List<Medicamento> productos=null;
		try (Session session=HibernateUtil.getSessionFactory().openSession()) {
			transaction=session.beginTransaction();
			productos=session.createQuery("FROM Medicamento",Medicamento.class).getResultList();
			transaction.commit();
		} catch (Exception e) {
			if (transaction!=null) {
				transaction.rollback();
			}
		}
		return productos;
	}
	
	public void insertSinStock(SinStock ss) {
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        session.persist(ss);
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) transaction.rollback();
	    }
	}

	public void deleteSinStock(int id) {
	    Transaction transaction = null;
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        transaction = session.beginTransaction();
	        SinStock ss = session.get(SinStock.class, id);
	        if (ss != null) session.remove(ss);
	        transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) transaction.rollback();
	    }
	}

	public static List<SinStock> selectAllSinStock() {
	    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	        return session.createQuery("FROM SinStock", SinStock.class).getResultList();
	    } catch (Exception e) {
	        return null;
	    }
	}

}