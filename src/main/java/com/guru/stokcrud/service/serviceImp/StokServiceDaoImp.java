/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guru.stokcrud.service.serviceImp;

import com.guru.stokcrud.entitie.dao.StokKartiDao;
import com.guru.stokcrud.entitie.daoImp.StokKartiDaoImp;
import com.guru.stokcrud.entitie.model.Stok;
import com.guru.stokcrud.service.serviceDao.StokServiceDao;
import java.util.List;

/**
 *
 * @author User
 */
public class StokServiceDaoImp implements StokServiceDao {

    private StokKartiDaoImp stokService = new StokKartiDaoImp();

    @Override
    public String addStokKarti(Stok stokKarti) {
        return stokService.addStokKarti(stokKarti);
    }

    @Override
    public String removeStokKarti(String stokKodu) {
        return stokService.removeStokKarti(stokKodu);
    }

    @Override
    public String updateStokKarti(Stok stokKarti) {
        return stokService.updateStokKarti(stokKarti);
    }

    @Override
    public List<Stok> getListAllStokKarti() {
        return stokService.getListAllStokKarti();
    }

    @Override
    public Stok getStokbyStokKoduStokKarti(String stokKodu) {
        return stokService.getStokbyStokKoduStokKarti(stokKodu);
    }

    @Override
    public List<Stok> searchStokByKodu(String stokKodu) {
        return stokService.searchStokByKodu(stokKodu);
    }

}
