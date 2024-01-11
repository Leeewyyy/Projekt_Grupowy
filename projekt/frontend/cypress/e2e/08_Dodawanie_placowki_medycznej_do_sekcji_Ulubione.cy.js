import { Login } from '../support/login.js'
const loginPage = new Login();

describe('Dodawanie placówki medycznej do sekcji Ulubione', () => {
  before(() => {
    cy.clearCookies();
    cy.visit('/');
  });

  it('Zaloguj się do systemu zgodnie ze scenariuszem “Logowanie do systemu”', () => {
    loginPage.login();
  });

  it('Wypełnij pole “Wyszukaj placówkę” i naciśnij przycisk “Szukaj” obok pola ', () => {
    cy.fixture('facility').then(({ name }) => {
      cy.navigationPlaceSearchType(name);
    });
  });

  it('Naciśnij ikonę serduszka na karcie placówki', () => {
    cy.fixture('facility').then(({ name }) => {
      cy.get('#placeList')
        .contains(name).parent().parent()
        .find('[data-tid="toggle-favourite"]').click();
    });

    cy.get('body').should('contain.text', 'Dodano placówkę do listy ulubionych');
  });

  it('Naciśnij na przycisk konta w górnym prawym rogu “Jan Kowalski”  ', () => {
    cy.get('[data-tid="header-account-panel"]').click();
    cy.url().should('include', '/user-panel');
    cy.fixture('facility').then(({ name }) => {
      cy.get('[data-tid="section-favourites"]').should('contain.text', name);
    });
  })
});