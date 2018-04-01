import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Recibo e2e test', () => {

    let navBarPage: NavBarPage;
    let reciboDialogPage: ReciboDialogPage;
    let reciboComponentsPage: ReciboComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Recibos', () => {
        navBarPage.goToEntity('recibo');
        reciboComponentsPage = new ReciboComponentsPage();
        expect(reciboComponentsPage.getTitle())
            .toMatch(/systemAguaPotableApp.recibo.home.title/);

    });

    it('should load create Recibo dialog', () => {
        reciboComponentsPage.clickOnCreateButton();
        reciboDialogPage = new ReciboDialogPage();
        expect(reciboDialogPage.getModalTitle())
            .toMatch(/systemAguaPotableApp.recibo.home.createOrEditLabel/);
        reciboDialogPage.close();
    });

    it('should create and save Recibos', () => {
        reciboComponentsPage.clickOnCreateButton();
        reciboDialogPage.setNumeroInput('5');
        expect(reciboDialogPage.getNumeroInput()).toMatch('5');
        reciboDialogPage.estadoSelectLastOption();
        reciboDialogPage.setPagoanteriorInput('5');
        expect(reciboDialogPage.getPagoanteriorInput()).toMatch('5');
        reciboDialogPage.setPagoactualInput('5');
        expect(reciboDialogPage.getPagoactualInput()).toMatch('5');
        reciboDialogPage.setTotalInput('5');
        expect(reciboDialogPage.getTotalInput()).toMatch('5');
        reciboDialogPage.setFechageneraInput(12310020012301);
        expect(reciboDialogPage.getFechageneraInput()).toMatch('2001-12-31T02:30');
        reciboDialogPage.setFechapagaInput(12310020012301);
        expect(reciboDialogPage.getFechapagaInput()).toMatch('2001-12-31T02:30');
        reciboDialogPage.setAnioInput('5');
        expect(reciboDialogPage.getAnioInput()).toMatch('5');
        reciboDialogPage.setMesInput('5');
        expect(reciboDialogPage.getMesInput()).toMatch('5');
        reciboDialogPage.usuarioSelectLastOption();
        reciboDialogPage.save();
        expect(reciboDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ReciboComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-recibo div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ReciboDialogPage {
    modalTitle = element(by.css('h4#myReciboLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    numeroInput = element(by.css('input#field_numero'));
    estadoSelect = element(by.css('select#field_estado'));
    pagoanteriorInput = element(by.css('input#field_pagoanterior'));
    pagoactualInput = element(by.css('input#field_pagoactual'));
    totalInput = element(by.css('input#field_total'));
    fechageneraInput = element(by.css('input#field_fechagenera'));
    fechapagaInput = element(by.css('input#field_fechapaga'));
    anioInput = element(by.css('input#field_anio'));
    mesInput = element(by.css('input#field_mes'));
    usuarioSelect = element(by.css('select#field_usuario'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNumeroInput = function(numero) {
        this.numeroInput.sendKeys(numero);
    };

    getNumeroInput = function() {
        return this.numeroInput.getAttribute('value');
    };

    setEstadoSelect = function(estado) {
        this.estadoSelect.sendKeys(estado);
    };

    getEstadoSelect = function() {
        return this.estadoSelect.element(by.css('option:checked')).getText();
    };

    estadoSelectLastOption = function() {
        this.estadoSelect.all(by.tagName('option')).last().click();
    };
    setPagoanteriorInput = function(pagoanterior) {
        this.pagoanteriorInput.sendKeys(pagoanterior);
    };

    getPagoanteriorInput = function() {
        return this.pagoanteriorInput.getAttribute('value');
    };

    setPagoactualInput = function(pagoactual) {
        this.pagoactualInput.sendKeys(pagoactual);
    };

    getPagoactualInput = function() {
        return this.pagoactualInput.getAttribute('value');
    };

    setTotalInput = function(total) {
        this.totalInput.sendKeys(total);
    };

    getTotalInput = function() {
        return this.totalInput.getAttribute('value');
    };

    setFechageneraInput = function(fechagenera) {
        this.fechageneraInput.sendKeys(fechagenera);
    };

    getFechageneraInput = function() {
        return this.fechageneraInput.getAttribute('value');
    };

    setFechapagaInput = function(fechapaga) {
        this.fechapagaInput.sendKeys(fechapaga);
    };

    getFechapagaInput = function() {
        return this.fechapagaInput.getAttribute('value');
    };

    setAnioInput = function(anio) {
        this.anioInput.sendKeys(anio);
    };

    getAnioInput = function() {
        return this.anioInput.getAttribute('value');
    };

    setMesInput = function(mes) {
        this.mesInput.sendKeys(mes);
    };

    getMesInput = function() {
        return this.mesInput.getAttribute('value');
    };

    usuarioSelectLastOption = function() {
        this.usuarioSelect.all(by.tagName('option')).last().click();
    };

    usuarioSelectOption = function(option) {
        this.usuarioSelect.sendKeys(option);
    };

    getUsuarioSelect = function() {
        return this.usuarioSelect;
    };

    getUsuarioSelectedOption = function() {
        return this.usuarioSelect.element(by.css('option:checked')).getText();
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
