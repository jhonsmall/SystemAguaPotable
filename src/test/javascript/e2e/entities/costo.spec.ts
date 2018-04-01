import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Costo e2e test', () => {

    let navBarPage: NavBarPage;
    let costoDialogPage: CostoDialogPage;
    let costoComponentsPage: CostoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Costos', () => {
        navBarPage.goToEntity('costo');
        costoComponentsPage = new CostoComponentsPage();
        expect(costoComponentsPage.getTitle())
            .toMatch(/systemAguaPotableApp.costo.home.title/);

    });

    it('should load create Costo dialog', () => {
        costoComponentsPage.clickOnCreateButton();
        costoDialogPage = new CostoDialogPage();
        expect(costoDialogPage.getModalTitle())
            .toMatch(/systemAguaPotableApp.costo.home.createOrEditLabel/);
        costoDialogPage.close();
    });

    it('should create and save Costos', () => {
        costoComponentsPage.clickOnCreateButton();
        costoDialogPage.setCuotaInput('5');
        expect(costoDialogPage.getCuotaInput()).toMatch('5');
        costoDialogPage.setFechaInput(12310020012301);
        expect(costoDialogPage.getFechaInput()).toMatch('2001-12-31T02:30');
        costoDialogPage.servicioSelectLastOption();
        costoDialogPage.sectorSelectLastOption();
        costoDialogPage.clasificacionSelectLastOption();
        costoDialogPage.save();
        expect(costoDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CostoComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-costo div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CostoDialogPage {
    modalTitle = element(by.css('h4#myCostoLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    cuotaInput = element(by.css('input#field_cuota'));
    fechaInput = element(by.css('input#field_fecha'));
    servicioSelect = element(by.css('select#field_servicio'));
    sectorSelect = element(by.css('select#field_sector'));
    clasificacionSelect = element(by.css('select#field_clasificacion'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCuotaInput = function(cuota) {
        this.cuotaInput.sendKeys(cuota);
    };

    getCuotaInput = function() {
        return this.cuotaInput.getAttribute('value');
    };

    setFechaInput = function(fecha) {
        this.fechaInput.sendKeys(fecha);
    };

    getFechaInput = function() {
        return this.fechaInput.getAttribute('value');
    };

    servicioSelectLastOption = function() {
        this.servicioSelect.all(by.tagName('option')).last().click();
    };

    servicioSelectOption = function(option) {
        this.servicioSelect.sendKeys(option);
    };

    getServicioSelect = function() {
        return this.servicioSelect;
    };

    getServicioSelectedOption = function() {
        return this.servicioSelect.element(by.css('option:checked')).getText();
    };

    sectorSelectLastOption = function() {
        this.sectorSelect.all(by.tagName('option')).last().click();
    };

    sectorSelectOption = function(option) {
        this.sectorSelect.sendKeys(option);
    };

    getSectorSelect = function() {
        return this.sectorSelect;
    };

    getSectorSelectedOption = function() {
        return this.sectorSelect.element(by.css('option:checked')).getText();
    };

    clasificacionSelectLastOption = function() {
        this.clasificacionSelect.all(by.tagName('option')).last().click();
    };

    clasificacionSelectOption = function(option) {
        this.clasificacionSelect.sendKeys(option);
    };

    getClasificacionSelect = function() {
        return this.clasificacionSelect;
    };

    getClasificacionSelectedOption = function() {
        return this.clasificacionSelect.element(by.css('option:checked')).getText();
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
