/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guru.stokcrud.entitie.dao;

import com.guru.stokcrud.entitie.model.Stok;
import java.util.List;


/**
 *
 * @author User
 */
public interface StokKartiDao {
    
       String addStokKarti(Stok stokKarti);
       String removeStokKarti(String stokKodu);
       String updateStokKarti(Stok stokKarti);
       List<Stok> getListAllStokKarti();
       Stok getStokbyStokKoduStokKarti(String stokKodu);
       List<Stok> searchStokByKodu(String stokKodu);
       
       
       
}
