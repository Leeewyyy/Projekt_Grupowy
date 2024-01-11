describe('Wyszukiwanie publicznych placówek medycznych po typie placówki Szpital w całej Polsce', () => {
  before(() => {
    cy.visit('/');
  });

  it('Wpisz w pole wyszukiwania adresu "Gdańsk centrum"', () => {
    cy.searchAddressAndCheckResults('Gdańsk Centrum');
  });

  it('Z listy adresów wybierz "Gdańsk, Plac Dominikański 7, 80-884"', () => {
    cy.selectFoundAddressAndCheckFilters('Gdańsk, Plac Dominikański 7, 80-884');
  });

  it('Na karcie z filtrami ustaw przełącznik NFZ na pozycję w prawo', () => {
    cy.changeSwitch('[data-tid="nfz-switch"]', true);
  });

  it('Na karcie z filtrami wybierz z listy Typ placówki element "Szpital"', () => {
    cy.get('[data-tid="place-type-select"]').click();
    cy.get('.multiselect__option').contains('SZPITAL').click();
    cy.get('.multiselect__single').should('contain', 'SZPITAL');
  });

  it('Na karcie z filtrami wybierz z przycisków na dole karty max. odległość "Cała Polska"', () => {
    cy.get('[data-tid="distance-button"]').contains('Cała Polska').click();
    cy.get('[data-tid="distance-button"]').contains('Cała Polska').should('have.class', 'Button--active');
  });

  it('Na karcie z filtrami naciśnij przycisk "Szukaj"', () => {
    cy.submitPlaceSearchAndCheckPlaces();
    cy.get('[data-tid="nfz-status-none"]').should('have.length', 0);
  });
});