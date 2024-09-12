/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guru.stokcrud.entitie.daoImp;

import com.guru.stokcrud.entitie.dao.StokKartiDao;
import com.guru.stokcrud.entitie.model.Stok;
import com.guru.stokcrud.main.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author User
 */
public class StokKartiDaoImp implements StokKartiDao {

    @Override
    public String addStokKarti(Stok stokKarti) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

         
            String stokKodu = stokKarti.getStokKodu();
            String hql = "FROM Stok WHERE stokKodu = :stokKodu";
            Query<Stok> query = session.createQuery(hql, Stok.class);
            query.setParameter("stokKodu", stokKodu);

            Stok existingStok = query.uniqueResult();

            if (existingStok == null) {
            
                session.save(stokKarti);
                transaction.commit();
                return "Stok başarıyla eklendi.";
            } else {
        
                transaction.rollback();
                return "Hata: Aynı stok koduna sahip bir stok zaten mevcut.";
            }

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            return "Ekleme işlemi sırasında bir hata oluştu: " + e.getMessage();
        } finally {
            session.close();
        }
    }

    @Override
    public String removeStokKarti(String stokKodu) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Query query = session.createQuery("DELETE FROM Stok WHERE stokKodu = :stokKodu");
            query.setParameter("stokKodu", stokKodu);
            int result = query.executeUpdate();

            tx.commit();
            return "Stok Başarıyla Silindi ";
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return "Ürün Silinemedi!" + e.getMessage();
        } finally {
            session.close();
        }
    }

    @Override
    public String updateStokKarti(Stok stokKarti) {
        try {
            if (stokKarti == null) {
                return "Hata! Güncellenecek stok kartı bulunamadı.";
            }

            Session s = HibernateUtil.getSessionFactory().openSession();
            s.beginTransaction();

            String hql = "FROM Stok WHERE stokKodu = :stokKodu";
            Stok mevcutStokKarti = (Stok) s.createQuery(hql)
                    .setParameter("stokKodu", stokKarti.getStokKodu())
                    .uniqueResult();

            if (mevcutStokKarti == null) {
                return "Hata! Veritabanında bu stok kartı bulunamadı.";
            }

         
            boolean degisiklikVar = false;

           
            if (!mevcutStokKarti.getStokAdi().equals(stokKarti.getStokAdi())) {
                mevcutStokKarti.setStokAdi(stokKarti.getStokAdi());
                degisiklikVar = true;
            }
            if (!mevcutStokKarti.getBarkodu().equals(stokKarti.getBarkodu())) {
                mevcutStokKarti.setBarkodu(stokKarti.getBarkodu());
                degisiklikVar = true;
            }
            if (!mevcutStokKarti.getBirimi().equals(stokKarti.getBirimi())) {
                mevcutStokKarti.setBirimi(stokKarti.getBirimi());
                degisiklikVar = true;
            }
            if (!mevcutStokKarti.getAciklama().equals(stokKarti.getAciklama())) {
                mevcutStokKarti.setAciklama(stokKarti.getAciklama());
                degisiklikVar = true;
            }
            if (mevcutStokKarti.getKdvTipi() != stokKarti.getKdvTipi()) {
                mevcutStokKarti.setKdvTipi(stokKarti.getKdvTipi());
                degisiklikVar = true;
            }
            if (mevcutStokKarti.getStokTipi() != stokKarti.getStokTipi()) {
                mevcutStokKarti.setStokTipi(stokKarti.getStokTipi());
                degisiklikVar = true;
            }

         
            if (!degisiklikVar) {
                return "Değişiklik yapılmadı, güncelleme işlemi gerçekleşmedi.";
            }

            s.update(mevcutStokKarti);
            s.getTransaction().commit();
            return "Seçili Stok Başarıyla Güncellendi";
        } catch (Exception e) {
            return "Hata! " + e.getMessage();
        }
    }

    @Override
    public List<Stok> getListAllStokKarti() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        List<Stok> stokList = s.createQuery("FROM Stok", Stok.class).list();
        s.getTransaction().commit();
        s.close(); 
        return stokList;
    }

    @Override
    public Stok getStokbyStokKoduStokKarti(String stokKodu) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        Stok stok = s.get(Stok.class, stokKodu); 
        s.getTransaction().commit();
        s.close();
        return stok;
    }

    @Override
    public List<Stok> searchStokByKodu(String stokKodu) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "FROM Stok WHERE stokKodu LIKE :stokKodu";
        Query<Stok> query = session.createQuery(hql, Stok.class);
        query.setParameter("stokKodu", "%" + stokKodu + "%");
        List<Stok> result = query.list();
        session.close();
        return result;
    }

}
