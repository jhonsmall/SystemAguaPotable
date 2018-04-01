import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('CostoMedidor e2e test', () => {

    let navBarPage: NavBarPage;
    let costoMedidorDialogPage: CostoMedidorDialogPage;
    let costoMedidorComponentsPage: CostoMedidorComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CostoMedidors', () => {
        navBarPage.goToEntity('costo-medidor');
        costoMedidorComponentsPage = new CostoMedidorComponentsPage();
        expect(costoMedidorComponentsPage.getTitle())
            .toMatch(/systemAguaPotableApp.costoMedidor.home.title/);

    });

    it('should load create CostoMedidor dialog', () => {
        costoMedidorComponentsPage.clickOnCreateButton();
        costoMedidorDialogPage = new CostoMedidorDialogPage();
        expect(costoMedidorDialogPage.getModalTitle())
            .toMatch(/systemAguaPotableApp.costoMedidor.home.createOrEditLabel/);
        costoMedidorDialogPage.close();
    });

    it('should create and save CostoMedidors', () => {
        costoMedidorComponentsPage.clickOnCreateButton();
        costoMedidorDialogPage.setFechaInput(12310020012301);
        expect(costoMedidorDialogPage.getFechaInput()).toMatch('2001-12-31T02:30');
        costoMedidorDialogPage.estadoSelectLastOption();
        costoMedidorDialogPage.costoSelectLastOption();
        costoMedidorDialogPage.medidorSelectLastOption();
        costoMedidorDialogPage.save();
        expect(costoMedidorDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CostoMedidorComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-costo-medidor div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CostoMedidorDialogPage {
    modalTitle = element(by.css('h4#myCostoMedidorLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    fechaInput = element(by.css('input#field_fecha'));
    estadoSelect = element(by.css('select#field_estado'));
    costoSelect = element(by.css('select#field_costo'));
    medidorSelect = element(by.css('select#field_medidor'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setFechaInput = function(fecha) {
        this.fechaInput.sendKeys(fecha);
    };

    getFechaInput = function() {
        return this.fechaInput.getAttribute('value');
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
    costoSelectLastOption = function() {
        this.costoSelect.all(by.tagName('option')).last().click();
    };

    costoSelectOption = function(option) {
        this.costoSelect.sendKeys(option);
    };

    getCostoSelect = function() {
        return this.costoSelect;
    };

    getCostoSelectedOption = function() {
        return this.costoSelect.element(by.css('option:checked')).getText();
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
