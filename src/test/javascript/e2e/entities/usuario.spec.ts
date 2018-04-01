import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Usuario e2e test', () => {

    let navBarPage: NavBarPage;
    let usuarioDialogPage: UsuarioDialogPage;
    let usuarioComponentsPage: UsuarioComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Usuarios', () => {
        navBarPage.goToEntity('usuario');
        usuarioComponentsPage = new UsuarioComponentsPage();
        expect(usuarioComponentsPage.getTitle())
            .toMatch(/systemAguaPotableApp.usuario.home.title/);

    });

    it('should load create Usuario dialog', () => {
        usuarioComponentsPage.clickOnCreateButton();
        usuarioDialogPage = new UsuarioDialogPage();
        expect(usuarioDialogPage.getModalTitle())
            .toMatch(/systemAguaPotableApp.usuario.home.createOrEditLabel/);
        usuarioDialogPage.close();
    });

    it('should create and save Usuarios', () => {
        usuarioComponentsPage.clickOnCreateButton();
        usuarioDialogPage.estadoSelectLastOption();
        usuarioDialogPage.documentoSelectLastOption();
        usuarioDialogPage.setNumeroInput('numero');
        expect(usuarioDialogPage.getNumeroInput()).toMatch('numero');
        usuarioDialogPage.setNombresInput('nombres');
        expect(usuarioDialogPage.getNombresInput()).toMatch('nombres');
        usuarioDialogPage.setApellidosInput('apellidos');
        expect(usuarioDialogPage.getApellidosInput()).toMatch('apellidos');
        usuarioDialogPage.sexoSelectLastOption();
        usuarioDialogPage.setDireccionInput('direccion');
        expect(usuarioDialogPage.getDireccionInput()).toMatch('direccion');
        usuarioDialogPage.setTelefonoInput('telefono');
        expect(usuarioDialogPage.getTelefonoInput()).toMatch('telefono');
        usuarioDialogPage.setEmailInput('email');
        expect(usuarioDialogPage.getEmailInput()).toMatch('email');
        usuarioDialogPage.save();
        expect(usuarioDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class UsuarioComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-usuario div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class UsuarioDialogPage {
    modalTitle = element(by.css('h4#myUsuarioLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    estadoSelect = element(by.css('select#field_estado'));
    documentoSelect = element(by.css('select#field_documento'));
    numeroInput = element(by.css('input#field_numero'));
    nombresInput = element(by.css('input#field_nombres'));
    apellidosInput = element(by.css('input#field_apellidos'));
    sexoSelect = element(by.css('select#field_sexo'));
    direccionInput = element(by.css('input#field_direccion'));
    telefonoInput = element(by.css('input#field_telefono'));
    emailInput = element(by.css('input#field_email'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setEstadoSelect = function(estado) {
        this.estadoSelect.sendKeys(estado);
    };

    getEstadoSelect = function() {
        return this.estadoSelect.element(by.css('option:checked')).getText();
    };

    estadoSelectLastOption = function() {
        this.estadoSelect.all(by.tagName('option')).last().click();
    };
    setDocumentoSelect = function(documento) {
        this.documentoSelect.sendKeys(documento);
    };

    getDocumentoSelect = function() {
        return this.documentoSelect.element(by.css('option:checked')).getText();
    };

    documentoSelectLastOption = function() {
        this.documentoSelect.all(by.tagName('option')).last().click();
    };
    setNumeroInput = function(numero) {
        this.numeroInput.sendKeys(numero);
    };

    getNumeroInput = function() {
        return this.numeroInput.getAttribute('value');
    };

    setNombresInput = function(nombres) {
        this.nombresInput.sendKeys(nombres);
    };

    getNombresInput = function() {
        return this.nombresInput.getAttribute('value');
    };

    setApellidosInput = function(apellidos) {
        this.apellidosInput.sendKeys(apellidos);
    };

    getApellidosInput = function() {
        return this.apellidosInput.getAttribute('value');
    };

    setSexoSelect = function(sexo) {
        this.sexoSelect.sendKeys(sexo);
    };

    getSexoSelect = function() {
        return this.sexoSelect.element(by.css('option:checked')).getText();
    };

    sexoSelectLastOption = function() {
        this.sexoSelect.all(by.tagName('option')).last().click();
    };
    setDireccionInput = function(direccion) {
        this.direccionInput.sendKeys(direccion);
    };

    getDireccionInput = function() {
        return this.direccionInput.getAttribute('value');
    };

    setTelefonoInput = function(telefono) {
        this.telefonoInput.sendKeys(telefono);
    };

    getTelefonoInput = function() {
        return this.telefonoInput.getAttribute('value');
    };

    setEmailInput = function(email) {
        this.emailInput.sendKeys(email);
    };

    getEmailInput = function() {
        return this.emailInput.getAttribute('value');
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
