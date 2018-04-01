import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Medidor e2e test', () => {

    let navBarPage: NavBarPage;
    let medidorDialogPage: MedidorDialogPage;
    let medidorComponentsPage: MedidorComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Medidors', () => {
        navBarPage.goToEntity('medidor');
        medidorComponentsPage = new MedidorComponentsPage();
        expect(medidorComponentsPage.getTitle())
            .toMatch(/systemAguaPotableApp.medidor.home.title/);

    });

    it('should load create Medidor dialog', () => {
        medidorComponentsPage.clickOnCreateButton();
        medidorDialogPage = new MedidorDialogPage();
        expect(medidorDialogPage.getModalTitle())
            .toMatch(/systemAguaPotableApp.medidor.home.createOrEditLabel/);
        medidorDialogPage.close();
    });

    it('should create and save Medidors', () => {
        medidorComponentsPage.clickOnCreateButton();
        medidorDialogPage.setNumeromedidorInput('5');
        expect(medidorDialogPage.getNumeromedidorInput()).toMatch('5');
        medidorDialogPage.setFechaadquirioInput(12310020012301);
        expect(medidorDialogPage.getFechaadquirioInput()).toMatch('2001-12-31T02:30');
        medidorDialogPage.setFechaactualInput(12310020012301);
        expect(medidorDialogPage.getFechaactualInput()).toMatch('2001-12-31T02:30');
        medidorDialogPage.usuarioSelectLastOption();
        medidorDialogPage.sectorSelectLastOption();
        medidorDialogPage.clasificacionSelectLastOption();
        medidorDialogPage.save();
        expect(medidorDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MedidorComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-medidor div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MedidorDialogPage {
    modalTitle = element(by.css('h4#myMedidorLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    numeromedidorInput = element(by.css('input#field_numeromedidor'));
    fechaadquirioInput = element(by.css('input#field_fechaadquirio'));
    fechaactualInput = element(by.css('input#field_fechaactual'));
    usuarioSelect = element(by.css('select#field_usuario'));
    sectorSelect = element(by.css('select#field_sector'));
    clasificacionSelect = element(by.css('select#field_clasificacion'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNumeromedidorInput = function(numeromedidor) {
        this.numeromedidorInput.sendKeys(numeromedidor);
    };

    getNumeromedidorInput = function() {
        return this.numeromedidorInput.getAttribute('value');
    };

    setFechaadquirioInput = function(fechaadquirio) {
        this.fechaadquirioInput.sendKeys(fechaadquirio);
    };

    getFechaadquirioInput = function() {
        return this.fechaadquirioInput.getAttribute('value');
    };

    setFechaactualInput = function(fechaactual) {
        this.fechaactualInput.sendKeys(fechaactual);
    };

    getFechaactualInput = function() {
        return this.fechaactualInput.getAttribute('value');
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
