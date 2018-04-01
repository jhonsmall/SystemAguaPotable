import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Clasificacion e2e test', () => {

    let navBarPage: NavBarPage;
    let clasificacionDialogPage: ClasificacionDialogPage;
    let clasificacionComponentsPage: ClasificacionComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Clasificacions', () => {
        navBarPage.goToEntity('clasificacion');
        clasificacionComponentsPage = new ClasificacionComponentsPage();
        expect(clasificacionComponentsPage.getTitle())
            .toMatch(/systemAguaPotableApp.clasificacion.home.title/);

    });

    it('should load create Clasificacion dialog', () => {
        clasificacionComponentsPage.clickOnCreateButton();
        clasificacionDialogPage = new ClasificacionDialogPage();
        expect(clasificacionDialogPage.getModalTitle())
            .toMatch(/systemAguaPotableApp.clasificacion.home.createOrEditLabel/);
        clasificacionDialogPage.close();
    });

    it('should create and save Clasificacions', () => {
        clasificacionComponentsPage.clickOnCreateButton();
        clasificacionDialogPage.setNombreInput('nombre');
        expect(clasificacionDialogPage.getNombreInput()).toMatch('nombre');
        clasificacionDialogPage.estadoSelectLastOption();
        clasificacionDialogPage.save();
        expect(clasificacionDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ClasificacionComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-clasificacion div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ClasificacionDialogPage {
    modalTitle = element(by.css('h4#myClasificacionLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nombreInput = element(by.css('input#field_nombre'));
    estadoSelect = element(by.css('select#field_estado'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNombreInput = function(nombre) {
        this.nombreInput.sendKeys(nombre);
    };

    getNombreInput = function() {
        return this.nombreInput.getAttribute('value');
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
