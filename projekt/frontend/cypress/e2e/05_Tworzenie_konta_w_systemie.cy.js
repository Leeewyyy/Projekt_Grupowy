describe('Tworzenie konta w systemie', () => {
  before(() => {
    cy.clearCookies();
    cy.visit('/');
  });
  
  it('Naciśnij przycisk "Zaloguj się"', () => {
    cy.get('[data-tid="header-login-button"]').click();
    cy.url().should('include', '/login');
  });

  it('Naciśnij przycisk "Nie masz konta? Zarejestruj się!"', () => {
    cy.get('[data-tid="login-go-to-register-button"]').click();
    cy.url().should('include', '/register');
  });

  it('Wypełnij pola formularza', () => {
    cy.fixture('user').then(({ fullName, email, password }) => {
      cy.get('[data-tid="register-fullname"] input').type(fullName).should('have.value', fullName);
      cy.get('[data-tid="register-email"] input').type(email).should('have.value', email);
      cy.get('[data-tid="register-password"] input').type(password).should('have.value', password);
      cy.get('[data-tid="register-password-confirm"] input').type(password).should('have.value', password);
    });
  });

  it('Zaakceptuj Regulamin oraz Politykę prywatności', () => {
    cy.get('[data-tid="register-consent-tos"] label').click();
    cy.get('[data-tid="register-consent-tos"] input[type=checkbox]').should('be.checked');
    cy.get('[data-tid="register-consent-pp"] label').click();
    cy.get('[data-tid="register-consent-pp"] input[type=checkbox]').should('be.checked');
  });

  it('Naciśnij przycisk "Zarejestruj się"', () => {
    cy.get('[data-tid="register-submit"]').click();
    cy.get('.notification-content').contains('Konto założone. Możesz się teraz zalogować.')
    cy.url().should('include', '/login');
  });
});