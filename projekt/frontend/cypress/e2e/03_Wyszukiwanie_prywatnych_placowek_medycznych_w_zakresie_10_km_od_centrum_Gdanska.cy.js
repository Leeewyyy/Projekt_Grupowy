describe('Wyszukiwanie prywatnych placówek medycznych w zakresie 10 km od centrum Gdańska', () => {
  before(() => {
    cy.visit('/');
  });

  it('Wpisz w pole wyszukiwania adresu "Gdańsk centrum"', () => {
    cy.searchAddressAndCheckResults('Gdańsk Centrum');
  });

  it('Z listy adresów wybierz "Gdańsk, Plac Dominikański 7, 80-884"', () => {
    cy.selectFoundAddressAndCheckFilters('Gdańsk, Plac Dominikański 7, 80-884');
  });

  it('Na karcie z filtrami ustaw przełącznik NFZ na pozycję w lewo', () => {
    cy.changeSwitch('[data-tid="nfz-switch"]', false);
  });

  it('Na karcie z filtrami wybierz z przycisków na dole karty max. odległość "10 km"', () => {
    cy.get('[data-tid="distance-button"]').contains('10 km').click();
    cy.get('[data-tid="distance-button"]').contains('10 km').should('have.class', 'Button--active');
  });

  it('Na karcie z filtrami naciśnij przycisk "Szukaj"', () => {
    cy.submitPlaceSearchAndCheckPlaces();
    cy.get('[data-tid="nfz-status-full"]').should('have.length', 0);
    cy.get('[data-tid="nfz-status-partial"]').should('have.length', 0);
  });
});