import { Login } from '../support/login.js'
const loginPage = new Login();

describe('Dodawanie placówki medycznej do swojego konta użytkownika', () => {
  before(() => {
    cy.clearCookies();
    cy.visit('/');
  });

  it('Zaloguj się do systemu zgodnie ze scenariuszem “Logowanie do systemu”', () => {
    loginPage.login();
  });

  it('Na karcie “Moje placówki” naciśnij przycisk “Dodaj +”', () => {
    cy.get('[data-tid="add-place"]').click();
    cy.url().should('include', 'place/edit/0');
  });

  it('Wypełnij pole “Nazwa”', () => {
    cy.fixture('facility').then(({ name }) => {
      cy.get('[data-tid="name"] input').type(name)
        .should('have.value', name);
    });
  });

  it('W polu Adres wpisz “Gdańsk, Wielkopolska 2”', () => {
    cy.fixture('facility').then(({ addressToType }) => {
      cy.searchAddressAndCheckResults(addressToType);
    });
  });

  it('Z listy pod polem Adres wybierz “Gdańsk, Wielkopolska 2, 80-180”', () => {
    cy.fixture('facility').then(({ address }) => {
      cy.get('[data-tid="address-result"]').contains(address).click()
        .should('have.class', 'active');
    });
   
  });

  it('Wybierz element z listy “Typ placówki”', () => {
    cy.fixture('facility').then(({ type }) => {
      cy.get('[data-tid="place-type"]').click();
      cy.get('[data-tid="place-type"]').contains(type).click();
      cy.get('[data-tid="place-type"]').find('.multiselect__tags').should('contain.text', type);
    });
  });

  it('Wypełnij pole “Opis placówki”', () => {
    cy.fixture('facility').then(({ description }) => {
      cy.get('[data-tid="description"] textarea').type(description)
        .should('have.value', description);
    });
  });

  it('Wybierz zdjęcia', () => {
    cy.attachFiles('[data-tid="images"] input[type=file]', 'cy_example1.png', 'cy_example2.png');
  });

  it('W sekcji “Informacje dodatkowe” zmień przełącznik “Czy placówka obsługuje finansowanie przez Narodowy Fundusz Zdrowia?” na pozycję w lewo (“Nie”).', () => {
    cy.changeSwitch('[data-tid="nfz-switch"]', false);
  });

  it('Naciśnij przycisk “Zapisz placówkę”', () => {
      cy.get('[data-tid="submit"]').click();
      cy.wait(500);
      cy.get('body').should('contain.text', 'Placówka zapisana pomyślnie. Teraz możesz ją edytować');
      cy.fixture('facility').then(({ name }) => {
        cy.get('body').should('contain.text', `Edytuj placówkę ${name}`);
      })
  });
});