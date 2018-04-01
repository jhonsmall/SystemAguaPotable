import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('EscalasDelMedidor e2e test', () => {

    let navBarPage: NavBarPage;
    let escalasDelMedidorDialogPage: EscalasDelMedidorDialogPage;
    let escalasDelMedidorComponentsPage: EscalasDelMedidorComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load EscalasDelMedidors', () => {
        navBarPage.goToEntity('escalas-del-medidor');
        escalasDelMedidorComponentsPage = new EscalasDelMedidorComponentsPage();
        expect(escalasDelMedidorComponentsPage.getTitle())
            .toMatch(/systemAguaPotableApp.escalasDelMedidor.home.title/);

    });

    it('should load create EscalasDelMedidor dialog', () => {
        escalasDelMedidorComponentsPage.clickOnCreateButton();
        escalasDelMedidorDialogPage = new EscalasDelMedidorDialogPage();
        expect(escalasDelMedidorDialogPage.getModalTitle())
            .toMatch(/systemAguaPotableApp.escalasDelMedidor.home.createOrEditLabel/);
        escalasDelMedidorDialogPage.close();
    });

    it('should create and save EscalasDelMedidors', () => {
        escalasDelMedidorComponentsPage.clickOnCreateButton();
        escalasDelMedidorDialogPage.setInicioInput('5');
        expect(escalasDelMedidorDialogPage.getInicioInput()).toMatch('5');
        escalasDelMedidorDialogPage.setFinInput('5');
        expect(escalasDelMedidorDialogPage.getFinInput()).toMatch('5');
        escalasDelMedidorDialogPage.setFechaInput(12310020012301);
        expect(escalasDelMedidorDialogPage.getFechaInput()).toMatch('2001-12-31T02:30');
        escalasDelMedidorDialogPage.clasificacionSelectLastOption();
        escalasDelMedidorDialogPage.save();
        expect(escalasDelMedidorDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class EscalasDelMedidorComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-escalas-del-medidor div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class EscalasDelMedidorDialogPage {
    modalTitle = element(by.css('h4#myEscalasDelMedidorLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    inicioInput = element(by.css('input#field_inicio'));
    finInput = element(by.css('input#field_fin'));
    fechaInput = element(by.css('input#field_fecha'));
    clasificacionSelect = element(by.css('select#field_clasificacion'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setInicioInput = function(inicio) {
        this.inicioInput.sendKeys(inicio);
    };

    getInicioInput = function() {
        return this.inicioInput.getAttribute('value');
    };

    setFinInput = function(fin) {
        this.finInput.sendKeys(fin);
    };

    getFinInput = function() {
        return this.finInput.getAttribute('value');
    };

    setFechaInput = function(fecha) {
        this.fechaInput.sendKeys(fecha);
    };

    getFechaInput = function() {
        return this.fechaInput.getAttribute('value');
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
