import { Login } from '../support/login.js'
const loginPage = new Login();

describe('Edytowanie placówki medycznej stworzonej przez użytkownika', () => {
  before(() => {
    cy.clearCookies();
    cy.visit('/');
  });

  it('Zaloguj się do systemu zgodnie ze scenariuszem “Logowanie do systemu”', () => {
    loginPage.login();
  });

  it('Na karcie “Moje placówki” naciśnij ikonę ołówka na elemencie placówki Placówka medyczna test', () => {
    cy.fixture('facility').then(({ name }) => {
      cy.get('[data-tid="section-my-facilities"]').should('contain.text', name)
        .parent().parent()
        .find('[data-tid="edit-place"]').click();
    });
    cy.url().should('include', 'place/edit/')
  });

  it('Zmień nazwę placówki', () => {
    cy.fixture('facility').then(({ editName }) => {
      cy.get('[data-tid="name"] input').clear().type(editName)
        .should('have.value', editName);
      
      cy.get('[data-tid="submit"]').click();
      cy.wait(500);
      cy.get('body').should('contain.text', 'Placówka edytowana pomyślnie.');
      cy.get('body').should('contain.text', `Edytuj placówkę ${editName}`);
    });
  });
});