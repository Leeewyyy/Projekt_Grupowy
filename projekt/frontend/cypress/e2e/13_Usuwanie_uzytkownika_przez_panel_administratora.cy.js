import { Login } from '../support/login.js'
const loginPage = new Login('admin');

describe('Usuwanie użytkownika przez panel administratora', () => {
  before(() => {
    cy.clearCookies();
    cy.visit('/');
  });

  it('Zaloguj się do systemu zgodnie ze scenariuszem “Logowanie do systemu”, używając danych administratora', () => {
    loginPage.login();
  });

  it('Przejdź do linku /admin', () => {
    cy.visit('/admin');
    cy.url().should('include', 'admin/users');
    cy.get('[data-tid="users-nav"]').should('have.class', 'nav-link--active');
  });

  it('Na karcie Wyszukaj użytkownika wypełnij pole Adres e-mail', () => {
    cy.fixture('user').then(({ email }) => {
      cy.get('[data-tid="email"] input').type(email)
        .should('have.value', email);
    });
    cy.wait(1000);
  });

  it('Na liście placówek naciśnij przycisk Wyświetl na pierwszym elemencie listy', () => {
    cy.get('[data-tid="users-table"]').find('tbody tr').first()
      .find('[data-tid="show-user"]').click();

    cy.url().should('match', /\/admin\/users\/[0-9]*/);
  });

  it('Naciśnij przycisk “Usuń użytkownika” na dole strony', () => {
    cy.get('[data-tid="delete-user"]').click();
  });

  it('Naciśnij “OK”', () => {
    cy.on('window:confirm', () => true);
    cy.get('body').should('contain.text', 'został usunięty');
    cy.wait(1000);
    cy.url().should('include', 'admin/users');
  });
});