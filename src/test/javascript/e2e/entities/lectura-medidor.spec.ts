import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('LecturaMedidor e2e test', () => {

    let navBarPage: NavBarPage;
    let lecturaMedidorDialogPage: LecturaMedidorDialogPage;
    let lecturaMedidorComponentsPage: LecturaMedidorComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load LecturaMedidors', () => {
        navBarPage.goToEntity('lectura-medidor');
        lecturaMedidorComponentsPage = new LecturaMedidorComponentsPage();
        expect(lecturaMedidorComponentsPage.getTitle())
            .toMatch(/systemAguaPotableApp.lecturaMedidor.home.title/);

    });

    it('should load create LecturaMedidor dialog', () => {
        lecturaMedidorComponentsPage.clickOnCreateButton();
        lecturaMedidorDialogPage = new LecturaMedidorDialogPage();
        expect(lecturaMedidorDialogPage.getModalTitle())
            .toMatch(/systemAguaPotableApp.lecturaMedidor.home.createOrEditLabel/);
        lecturaMedidorDialogPage.close();
    });

    it('should create and save LecturaMedidors', () => {
        lecturaMedidorComponentsPage.clickOnCreateButton();
        lecturaMedidorDialogPage.setLecturainicialInput('5');
        expect(lecturaMedidorDialogPage.getLecturainicialInput()).toMatch('5');
        lecturaMedidorDialogPage.setLecturafinalInput('5');
        expect(lecturaMedidorDialogPage.getLecturafinalInput()).toMatch('5');
        lecturaMedidorDialogPage.estadoSelectLastOption();
        lecturaMedidorDialogPage.setFechaInput(12310020012301);
        expect(lecturaMedidorDialogPage.getFechaInput()).toMatch('2001-12-31T02:30');
        lecturaMedidorDialogPage.setAnioInput('5');
        expect(lecturaMedidorDialogPage.getAnioInput()).toMatch('5');
        lecturaMedidorDialogPage.setMesInput('5');
        expect(lecturaMedidorDialogPage.getMesInput()).toMatch('5');
        lecturaMedidorDialogPage.setDescripcionInput('descripcion');
        expect(lecturaMedidorDialogPage.getDescripcionInput()).toMatch('descripcion');
        // lecturaMedidorDialogPage.lecturamedidorReciboSelectLastOption();
        lecturaMedidorDialogPage.medidorSelectLastOption();
        lecturaMedidorDialogPage.save();
        expect(lecturaMedidorDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class LecturaMedidorComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-lectura-medidor div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class LecturaMedidorDialogPage {
    modalTitle = element(by.css('h4#myLecturaMedidorLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    lecturainicialInput = element(by.css('input#field_lecturainicial'));
    lecturafinalInput = element(by.css('input#field_lecturafinal'));
    estadoSelect = element(by.css('select#field_estado'));
    fechaInput = element(by.css('input#field_fecha'));
    anioInput = element(by.css('input#field_anio'));
    mesInput = element(by.css('input#field_mes'));
    descripcionInput = element(by.css('input#field_descripcion'));
    lecturamedidorReciboSelect = element(by.css('select#field_lecturamedidorRecibo'));
    medidorSelect = element(by.css('select#field_medidor'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setLecturainicialInput = function(lecturainicial) {
        this.lecturainicialInput.sendKeys(lecturainicial);
    };

    getLecturainicialInput = function() {
        return this.lecturainicialInput.getAttribute('value');
    };

    setLecturafinalInput = function(lecturafinal) {
        this.lecturafinalInput.sendKeys(lecturafinal);
    };

    getLecturafinalInput = function() {
        return this.lecturafinalInput.getAttribute('value');
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
    setFechaInput = function(fecha) {
        this.fechaInput.sendKeys(fecha);
    };

    getFechaInput = function() {
        return this.fechaInput.getAttribute('value');
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

    setDescripcionInput = function(descripcion) {
        this.descripcionInput.sendKeys(descripcion);
    };

    getDescripcionInput = function() {
        return this.descripcionInput.getAttribute('value');
    };

    lecturamedidorReciboSelectLastOption = function() {
        this.lecturamedidorReciboSelect.all(by.tagName('option')).last().click();
    };

    lecturamedidorReciboSelectOption = function(option) {
        this.lecturamedidorReciboSelect.sendKeys(option);
    };

    getLecturamedidorReciboSelect = function() {
        return this.lecturamedidorReciboSelect;
    };

    getLecturamedidorReciboSelectedOption = function() {
        return this.lecturamedidorReciboSelect.element(by.css('option:checked')).getText();
    };

    medidorSelectLastOption = function() {
        this.medidorSelect.all(by.tagName('option')).last().click();
    };

    medidorSelectOption = function(option) {
        this.medidorSelect.sendKeys(option);
    };

    getMedidorSelect = function() {
        return this.medidorSelect;
    };

    getMedidorSelectedOption = function() {
        return this.medidorSelect.element(by.css('option:checked')).getText();
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
