describe('Wysyłanie maila przez użytkownika do administracji systemu', () => {
  before(() => {
    cy.clearCookies();
    cy.visit('/');
  });

  it('W sekcji nawigacji, na górze strony naciśnij “Napisz do nas”', () => {
    cy.get('[data-tid="contact"]').click();
    cy.url().should('include', '/contact');
  });

  it('Wypełnij pola formularza', () => {
    cy.get('[data-tid="subject"] input').type('test temat').should('have.value', 'test temat');
    cy.get('[data-tid="email"] input').type('email@testemail.com').should('have.value', 'email@testemail.com');
    cy.get('[data-tid="description"] input').type('testowy opis').should('have.value', 'testowy opis');
  });

  it('Naciśnij przełącznik obok zgody na przetwarzanie danych osobowych', () => {
    cy.changeSwitch('[data-tid="switch-consent"]', true);
  });

  it('Naciśnij przycisk “Wyślj wiadomośc”', () => {
    cy.get('[data-tid="submit"]').click();
    cy.wait(500);
    cy.get('body').should('contain.text', 'Wiadomość wysłana pomyślnie');
  });
})