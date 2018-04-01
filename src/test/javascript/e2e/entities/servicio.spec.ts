import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Servicio e2e test', () => {

    let navBarPage: NavBarPage;
    let servicioDialogPage: ServicioDialogPage;
    let servicioComponentsPage: ServicioComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Servicios', () => {
        navBarPage.goToEntity('servicio');
        servicioComponentsPage = new ServicioComponentsPage();
        expect(servicioComponentsPage.getTitle())
            .toMatch(/systemAguaPotableApp.servicio.home.title/);

    });

    it('should load create Servicio dialog', () => {
        servicioComponentsPage.clickOnCreateButton();
        servicioDialogPage = new ServicioDialogPage();
        expect(servicioDialogPage.getModalTitle())
            .toMatch(/systemAguaPotableApp.servicio.home.createOrEditLabel/);
        servicioDialogPage.close();
    });

    it('should create and save Servicios', () => {
        servicioComponentsPage.clickOnCreateButton();
        servicioDialogPage.setNombreInput('nombre');
        expect(servicioDialogPage.getNombreInput()).toMatch('nombre');
        servicioDialogPage.setNormaInput('norma');
        expect(servicioDialogPage.getNormaInput()).toMatch('norma');
        servicioDialogPage.tipoSelectLastOption();
        servicioDialogPage.save();
        expect(servicioDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ServicioComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-servicio div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ServicioDialogPage {
    modalTitle = element(by.css('h4#myServicioLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nombreInput = element(by.css('input#field_nombre'));
    normaInput = element(by.css('input#field_norma'));
    tipoSelect = element(by.css('select#field_tipo'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNombreInput = function(nombre) {
        this.nombreInput.sendKeys(nombre);
    };

    getNombreInput = function() {
        return this.nombreInput.getAttribute('value');
    };

    setNormaInput = function(norma) {
        this.normaInput.sendKeys(norma);
    };

    getNormaInput = function() {
        return this.normaInput.getAttribute('value');
    };

    setTipoSelect = function(tipo) {
        this.tipoSelect.sendKeys(tipo);
    };

    getTipoSelect = function() {
        return this.tipoSelect.element(by.css('option:checked')).getText();
    };

    tipoSelectLastOption = function() {
        this.tipoSelect.all(by.tagName('option')).last().click();
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
