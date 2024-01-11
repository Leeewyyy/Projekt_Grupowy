import { Login } from '../support/login.js'
const loginPage = new Login();

describe('Wystawianie opinii dla placówki medycznej', () => {
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

  it('Kliknij w kartę placówki', () => {
    cy.fixture('facility').then(({ name }) => {
      cy.contains(name).click();
    });

    cy.url().should('include', 'place/');
    cy.get('[data-tid="place-details-container"]').should('contain.text', name);
  });

  it('W sekcji Opinie użytkowników wybierz ocenę', () => {
    cy.get('[data-tid="form-change-review"]').find('.stars_item:nth-child(3)').click();
    cy.get('[data-tid="form-change-review"]').find('.stars_item:nth-child(4)').should('contain.text', 'star_border');
  });

  it('W sekcji Opinie użytkowników wypełnij pole tekstowe', () => {
    cy.fixture('facility').then(({ review }) => {
      cy.get('[data-tid="form-change-review"]').find('input[name="reviewText"]').type(review);
    });
  });

  it('Naciśnij przycisk “Umieść opinię”', () => {
    cy.get('[data-tid="form-change-review"]').find('button[type="submit"]').click();
    cy.wait(500);
    cy.get('body').should('contain.text', 'Dodano opinię!');
    cy.fixture('facility').then(({ review }) => {
      cy.get('.PlaceReviews_list').contains(review).its('length').should('be.at.least', 1);
    });
  });
});