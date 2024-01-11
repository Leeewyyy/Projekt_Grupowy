import { Login } from '../support/login.js'
const loginPage = new Login();

describe('Usuwanie placówki medycznej stworzonej przez użytkownika', () => {
  before(() => {
    cy.clearCookies();
    cy.visit('/');
  });

  it('Zaloguj się do systemu zgodnie ze scenariuszem “Logowanie do systemu”', () => {
    loginPage.login();
  });

  it('Na karcie “Moje placówki” naciśnij ikonę śmietniczka na elemencie placówki “Placówka medyczna test edit”  ', () => {
    cy.fixture('facility').then(({ editName }) => {
      cy.get('[data-tid="section-my-facilities"]').should('contain.text', editName)
        .parent().parent()
        .find('[data-tid="delete-place-button"]').click();
    });
  });

  it('W oknie dialogowym naciśnij “OK”', () => {
    cy.on('window:confirm', () => true);
    cy.get('body').should('contain.text', 'Placówka usunięta pomyślnie');
    cy.get('[data-tid="section-my-facilities"]').should('contain.text', 'Nie znaleziono żadnych miejsc spełniających kryteria.');
  });
});