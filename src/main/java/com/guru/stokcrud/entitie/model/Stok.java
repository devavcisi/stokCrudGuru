/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guru.stokcrud.entitie.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stok_karti")
public class Stok {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "stok_kodu", unique = true, length = 50)
    private String stokKodu;

    @Column(name = "stok_adi", length = 100)
    private String stokAdi;

    @Column(name = "stok_tipi")
    private int stokTipi;

    @Column(name = "birimi", length = 10)
    private String birimi;

    @Column(name = "barkodu", length = 30)
    private String barkodu;

    @Column(name = "kdv_tipi")
    private double kdvTipi;

    @Column(name = "aciklama", columnDefinition = "TEXT")
    private String aciklama;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "olusturma_zamani")
    private Date olusturmaZamani;

    public Stok() {
    }

    public Stok(String stokKodu, String stokAdi, int stokTipi, String birimi, String barkodu, double kdvTipi, String aciklama, Date olusturmaZamani) {
        this.stokKodu = stokKodu;
        this.stokAdi = stokAdi;
        this.stokTipi = stokTipi;
        this.birimi = birimi;
        this.barkodu = barkodu;
        this.kdvTipi = kdvTipi;
        this.aciklama = aciklama;
        this.olusturmaZamani = olusturmaZamani;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStokKodu() {
        return stokKodu;
    }

    public void setStokKodu(String stokKodu) {
        this.stokKodu = stokKodu;
    }

    public String getStokAdi() {
        return stokAdi;
    }

    public void setStokAdi(String stokAdi) {
        this.stokAdi = stokAdi;
    }

    public int getStokTipi() {
        return stokTipi;
    }

    public void setStokTipi(int stokTipi) {
        this.stokTipi = stokTipi;
    }

    public String getBirimi() {
        return birimi;
    }

    public void setBirimi(String birimi) {
        this.birimi = birimi;
    }

    public String getBarkodu() {
        return barkodu;
    }

    public void setBarkodu(String barkodu) {
        this.barkodu = barkodu;
    }

    public double getKdvTipi() {
        return kdvTipi;
    }

    public void setKdvTipi(double kdvTipi) {
        this.kdvTipi = kdvTipi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Date getOlusturmaZamani() {
        return olusturmaZamani;
    }

    public void setOlusturmaZamani(Date olusturmaZamani) {
        this.olusturmaZamani = olusturmaZamani;
    }
}
