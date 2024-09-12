/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guru.stokcrud.gui;

import java.awt.event.ActionListener;
import java.sql.Timestamp;
import javax.swing.JTable;

/**
 *
 * @author User
 */
public interface IHomePage extends IGeneral {

    void buttonKaydet(ActionListener actionListener);

    void buttonGuncelle(ActionListener actionListener);

    void buttonSil(ActionListener actionListener);
    
    void buttonAra(ActionListener actionListener);
    
    void buttonKopyala(ActionListener actionListener);

    String getTarih();

    void setTarih(Timestamp time);

    String getStokKodu();

    void setStokKodu(String stokKodu);

    String getStokBarkodu();

    void setStokBarkodu(String stokBarkodu);

    String getStokAdi();

    void setStokAdi(String stokAdi);
    
    int getStokTipi();
   
    void setStokTipi(String stokTipi);
     
    String getStokBirimi();

    void setStokBirimi(String stokBirimi);
    
    Double getKdvTipi();
    
    void setKdvTipi(String kdvTipi);
    
     String getAciklama();
     
     void setAciklama(String aciklama);
     
     JTable getStokTable();
     
     
    
    
    
    
    
    
    

}
