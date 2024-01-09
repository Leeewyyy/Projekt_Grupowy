describe('template spec', () => {
  it('passes', () => {
    cy.visit('/');
    cy.get('[name="search-address"]').type('Gdańsk centrum');
    cy.log('hello');
  })
})