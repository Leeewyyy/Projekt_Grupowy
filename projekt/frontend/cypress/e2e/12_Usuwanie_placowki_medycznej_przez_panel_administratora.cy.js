import { Login } from '../support/login.js'
const loginPage = new Login('admin');

describe('Usuwanie placówki medycznej przez panel administratora', () => {
  before(() => {
    cy.clearCookies();
    cy.visit('/');
  });

  it('Zaloguj się do systemu zgodnie ze scenariuszem “Logowanie do systemu”, używając danych administratora', () => {
    loginPage.login();
  });

  it('Przejdź do linku /admin', () => {
    cy.visit('/admin');
    cy.url().should('include', 'admin');
  });

  it('W sekcji Nawigacja naciśnij przycisk “Zarządzanie placówkami”', () => {
    cy.get('[data-tid="places-nav"]').click();

    cy.wait(1000);
    cy.get('[data-tid="places-nav"]').should('have.class', 'nav-link--active');
    cy.url().should('include', 'admin/facilities');
  });

  it('Na liście placówek naciśnij przycisk Usuń na pierwszym elemencie listy', () => {
    cy.get('[data-tid="facilities-table"]').find('tbody tr').first().find('[data-tid="delete-user"]').click();
  });

  it('W oknie dialogowym naciśnij “OK”', () => {
    cy.on('window:confirm', () => true);
    cy.get('body').should('contain.text', 'została usunięta');
  });
});