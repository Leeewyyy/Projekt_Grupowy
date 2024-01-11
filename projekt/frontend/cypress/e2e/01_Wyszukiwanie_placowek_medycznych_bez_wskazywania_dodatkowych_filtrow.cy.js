describe('Wyszukiwanie placówek medycznych bez wskazywania dodatkowych filtrów', () => {
  before(() => {
    cy.visit('/');
  });

  it('Wpisz w pole wyszukiwania adresu "Gdańsk centrum"', () => {
    cy.searchAddressAndCheckResults('Gdańsk Centrum');
  });

  it('Z listy adresów wybierz "Gdańsk, Plac Dominikański 7, 80-884"', () => {
    cy.selectFoundAddressAndCheckFilters('Gdańsk, Plac Dominikański 7, 80-884');
  });

  it('Na karcie z filtrami naciśnij przycisk "Szukaj"', () => {
    cy.submitPlaceSearchAndCheckPlaces();
  });
});