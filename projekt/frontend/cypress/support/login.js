export class Login {
  Login() { };

  clickLoginLink() {
    cy.get('[data-tid="header-login-button"]').click();
    cy.url().should('include', '/login');
  };

  fillForm() {
    cy.fixture('user').then(({ email, password }) => {
      cy.get('[data-tid="login"] input').type(email)
        .should('have.value', email);
      cy.get('[data-tid="password"] input').type(password)  
        .should('have.value', password);
    });
  };

  submitForm() {
    cy.get('[data-tid="submit"]').click();
    cy.url().should('include', '/user-panel');
  };

  login() {
    this.clickLoginLink();
    this.fillForm();
    this.submitForm();
  };
};