import { Login } from '../support/login.js';
const loginPage = new Login();

describe('Logowanie do systemu', () => {
  before(() => {
    cy.clearCookies();
    cy.visit('/');
  })

  it('Naciśnij przycisk “Zaloguj się”', () => {
    loginPage.clickLoginLink();
  });

  it('Wypełnij pola formularza', () => {
    loginPage.fillForm();
  });

  it('Zatwierdź formularz, klikając przycisk “Zaloguj się”  ', () => {
    loginPage.submitForm();
  });
});