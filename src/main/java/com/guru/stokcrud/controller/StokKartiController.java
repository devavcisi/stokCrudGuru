/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.guru.stokcrud.controller;

import com.guru.stokcrud.entitie.model.Stok;
import com.guru.stokcrud.gui.IHomePage;
import com.guru.stokcrud.service.serviceImp.StokServiceDaoImp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author User
 */
public class StokKartiController {

    private final IHomePage homeInf;
    StokServiceDaoImp stokService = new StokServiceDaoImp();
    private JTable stokTable;

    String stokKodu;
    String stokBarkodu;
    String stokAdi;
    int stokTipi;
    double kdvTipi;
    String stokBirimi;
    String aciklama;
    Timestamp timestamp;

    private List<Stok> stokList = new ArrayList<>();
    Stok selectedStok = null;

    public StokKartiController(IHomePage homeInf) {
        this.homeInf = homeInf;
        stokTable = homeInf.getStokTable();

        LocalDateTime now = LocalDateTime.now();
        timestamp = Timestamp.valueOf(now);
        homeInf.setTarih(timestamp);

        tabloyuDoldur();
        tabloyuSectir();
        stokkaydet();
        stokGuncelle();
        aramaYap();

    }

    private void tabloyuDoldur() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Object[] columnsName = new Object[8];
        columnsName[0] = "Stok Kodu";
        columnsName[1] = "Stok Adı";
        columnsName[2] = "Stok Tipi";
        columnsName[3] = "Stok Birimi";
        columnsName[4] = "Stok Barkodu";
        columnsName[5] = "Kdv Tipi";
        columnsName[6] = "Aciklama";
        columnsName[7] = "Tarih";

        model.setColumnIdentifiers(columnsName);
        stokTable.setModel(model);

        stokList.clear();

        List<Stok> stoklar = null;
        try {
            stoklar = stokService.getListAllStokKarti();
        } catch (RuntimeException e) {
            System.out.println("Stoklar Çekilemedi " + e.getMessage());
        }
        if (stoklar != null && !stoklar.isEmpty()) {
            for (Stok stok : stoklar) {
                Object[] rowData = new Object[8];
                rowData[0] = stok.getStokKodu();
                rowData[1] = stok.getStokAdi();
                rowData[2] = stok.getStokTipi();
                rowData[3] = stok.getBirimi();
                rowData[4] = stok.getBarkodu();
                rowData[5] = stok.getKdvTipi();
                rowData[6] = stok.getAciklama();
                rowData[7] = stok.getOlusturmaZamani();

                model.addRow(rowData);
                stokList.add(stok);
            }
        }

    }

    private void tabloyuSectir() {

        homeInf.buttonSil(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = stokTable.getSelectedRow();

                if (selectedRowIndex != -1) {

                    String stokKodu = stokTable.getModel().getValueAt(selectedRowIndex, 0).toString();

                    for (Stok stok : stokList) {
                        if (stok.getStokKodu().equals(stokKodu)) {
                            selectedStok = stok;
                            break;
                        }
                    }

                    if (selectedStok != null) {

                        try {
                            String a = stokService.removeStokKarti(selectedStok.getStokKodu());
                            JOptionPane.showMessageDialog(null, a);

                            tabloyuDoldur();
                        } catch (RuntimeException es) {
                            JOptionPane.showMessageDialog(null, es.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Seçili stok bulunamadı.");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Herhangi bir satır seçilmedi.");
                }
            }
        });

        homeInf.buttonKopyala(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = stokTable.getSelectedRow();

                if (selectedRowIndex != -1) {

                    String stokKodu = stokTable.getModel().getValueAt(selectedRowIndex, 0).toString();

                    for (Stok stok : stokList) {
                        if (stok.getStokKodu().equals(stokKodu)) {
                            selectedStok = stok;
                            break;
                        }
                    }
                    if (selectedStok != null) {

                        copyFromFields(selectedStok);
                        getStokFromFields();

                    } else {
                        JOptionPane.showMessageDialog(null, "Seçili stok bulunamadı.");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Kopyalamak  bir satır seçilmedi.");
                }
            }
        });

        stokTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                if (evt.getClickCount() == 2 && stokTable.getSelectedRow() != -1) {
                    int selectedRowIndex = stokTable.getSelectedRow();

                    String stokKodu = stokTable.getModel().getValueAt(selectedRowIndex, 0).toString();

                    for (Stok stok : stokList) {
                        if (stok.getStokKodu().equals(stokKodu)) {
                            selectedStok = stok;
                            break;
                        }
                    }

                    if (selectedStok != null) {

                        updateFormFields(selectedStok);

                    } else {
                        JOptionPane.showMessageDialog(null, "Seçilen stok bulunamadı.");
                    }
                }
            }
        });

    }

    private void stokGuncelle() {
        homeInf.buttonGuncelle(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                getStokFromFields();
                if (stokKodu != null) {
                    try {

                        Stok stok = new Stok(stokKodu, stokAdi, stokTipi, stokBirimi, stokBarkodu, kdvTipi, aciklama, timestamp);
                        String guncellemeCevabi = stokService.updateStokKarti(stok);
                        JOptionPane.showMessageDialog(null, guncellemeCevabi);
                        tabloyuDoldur();

                    } catch (RuntimeException es) {
                        JOptionPane.showMessageDialog(null, es.getMessage());
                    }

                } else {

                }

            }
        });
    }

    private void aramaYap() {
        homeInf.buttonAra(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stokKodu = homeInf.getStokKodu();
                if (!stokKodu.isEmpty()) {
                    try {
                        List<Stok> stoklar = stokService.searchStokByKodu(stokKodu);

                        if (stoklar.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Stok bulunamadı.");
                        } else if (stoklar.size() == 1) {

                            Stok stok = stoklar.get(0);
                            JOptionPane.showMessageDialog(null,
                                    "Stok Kodu: " + stok.getStokKodu()
                                    + "\nStok Adı: " + stok.getStokAdi()
                                    + "\nStok Tipi: " + stok.getStokTipi()
                                    + "\nStok Birimi: " + stok.getBirimi()
                                    + "\nStok Barkodu: " + stok.getBarkodu()
                                    + "\nKDV Tipi: " + stok.getKdvTipi()
                                    + "\nAçıklama: " + stok.getAciklama()
                                    + "\nTarih: " + stok.getOlusturmaZamani()
                            );
                            selectStokInTable(stok.getStokKodu());

                        } else {

                            String[] options = new String[stoklar.size()];
                            for (int i = 0; i < stoklar.size(); i++) {
                                options[i] = stoklar.get(i).getStokKodu() + " - " + stoklar.get(i).getStokAdi();
                            }

                            String selectedOption = (String) JOptionPane.showInputDialog(
                                    null,
                                    "Bir stok seçin:",
                                    "Stok Seçimi",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options,
                                    options[0]
                            );

                            if (selectedOption != null) {

                                String selectedStokKodu = selectedOption.split(" - ")[0];
                                selectStokInTable(selectedStokKodu);

                            }
                        }
                    } catch (RuntimeException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Stok Kodu Girin!");
                }
            }
        });
    }

    private void getStokFromFields() {

        LocalDateTime now = LocalDateTime.now();
        timestamp = Timestamp.valueOf(now);

        stokKodu = homeInf.getStokKodu();
        stokBarkodu = homeInf.getStokBarkodu();
        stokAdi = homeInf.getStokAdi();
        stokTipi = homeInf.getStokTipi();
        kdvTipi = homeInf.getKdvTipi();
        stokBirimi = homeInf.getStokBirimi();
        aciklama = homeInf.getAciklama();

    }

    private void updateFormFields(Stok stok) {

        homeInf.setStokKodu(stok.getStokKodu());
        homeInf.setStokBarkodu(stok.getBarkodu());
        homeInf.setStokAdi(stok.getStokAdi());
        homeInf.setAciklama(stok.getAciklama());

        String stokTipiStr = Integer.toString(stok.getStokTipi());
        homeInf.setStokTipi(stokTipiStr);

        String kdvTipiStr = Double.toString(stok.getKdvTipi());
        homeInf.setKdvTipi(kdvTipiStr);

        homeInf.setStokBirimi(stok.getBirimi());
    }

    private void copyFromFields(Stok stok) {

        homeInf.setStokKodu(null);
        homeInf.setStokBarkodu(stok.getBarkodu());
        homeInf.setStokAdi(stok.getStokAdi());
        homeInf.setAciklama(stok.getAciklama());

        String stokTipiStr = Integer.toString(stok.getStokTipi());
        homeInf.setStokTipi(stokTipiStr);

        String kdvTipiStr = Double.toString(stok.getKdvTipi());
        homeInf.setKdvTipi(kdvTipiStr);

        homeInf.setStokBirimi(stok.getBirimi());

    }

    private void selectStokInTable(String stokKodu) {
        DefaultTableModel model = (DefaultTableModel) stokTable.getModel();
        Stok selectedStok = null;

        for (int i = 0; i < model.getRowCount(); i++) {
            String tableStokKodu = model.getValueAt(i, 0).toString();
            if (tableStokKodu.equals(stokKodu)) {
                stokTable.setRowSelectionInterval(i, i);
                stokTable.scrollRectToVisible(stokTable.getCellRect(i, 0, true));

                stokAdi = model.getValueAt(i, 1).toString();
                stokTipi = Integer.parseInt(model.getValueAt(i, 2).toString());
                stokBirimi = model.getValueAt(i, 3).toString();
                stokBarkodu = model.getValueAt(i, 4).toString();
                kdvTipi = Double.parseDouble(model.getValueAt(i, 5).toString());
                aciklama = model.getValueAt(i, 6).toString();
                timestamp = Timestamp.valueOf(model.getValueAt(i, 7).toString());

                selectedStok = new Stok(stokKodu, stokAdi, stokTipi, stokBirimi, stokBarkodu, kdvTipi, aciklama, timestamp);
                break;
            }
        }

        if (selectedStok != null) {

            updateFormFields(selectedStok);
        } else {
            JOptionPane.showMessageDialog(null, "Stok bulunamadı.");
        }

    }

    private void stokkaydet() {
        homeInf.buttonKaydet(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                getStokFromFields();

                if (stokKodu == null || stokKodu.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Stok Kodu girilmelidir!");
                } else if (stokBarkodu == null || stokBarkodu.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Stok Barkodu girilmelidir!");
                } else if (stokAdi == null || stokAdi.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Stok Adı girilmelidir!");
                } else if (stokBirimi == null || stokBirimi.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Stok Birimi girilmelidir!");
                } else if (kdvTipi == 0 || stokTipi == 0) {
                    JOptionPane.showMessageDialog(null, "KDV tipi ve Stok Tipleri Girilmelidir!");
                } else {

                    try {
                        Stok stokkarti = new Stok(stokKodu, stokAdi, stokTipi, stokBirimi, stokBarkodu, kdvTipi, aciklama, timestamp);
                        String a = stokService.addStokKarti(stokkarti);
                        JOptionPane.showMessageDialog(null, a);
                        tabloyuDoldur();
                    } catch (RuntimeException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });
    }

}
