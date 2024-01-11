function fakeUserLocation (latitude, longitude) {
  return {
    onBeforeLoad (window) {
      cy.stub(window.navigator.geolocation, 'getCurrentPosition').callsFake((cb, err) => {
        if (latitude && longitude) {
          return cb({
            coords: {
              latitude,
              longitude,
            },
          });
        }

        throw err({ code: 1 });
      });
    }
  };
};

describe('Wyszukiwanie placówek medycznych przy użyciu lokalizacji użytkownika i typu specjalisty Kardiolog', () => {
  before(() => {
    cy.visit('/', fakeUserLocation(54.37730, 18.60800));
  });

  it('Na polu wyszukiwania adresów naciśnij ikonę z celownikiem', () => {
    cy.get('[data-tid="address-search-input"]').contains('my_location').click();
    cy.get('[data-tid="user-location"]').should('be.visible');
  });

  it('Na karcie z filtrami wybierz z listy specjalizacji lekarza element "Kardiolog"', () => {
    cy.get('[data-tid="specialization-select"]').click();
    cy.get('.multiselect__option').contains('KARDIOLOG').click();
    cy.get('.multiselect__single').should('contain', 'KARDIOLOG');
  });

  it('Na karcie z filtrami naciśnij przycisk "Szukaj"', () => {
    cy.submitPlaceSearchAndCheckPlaces();
    cy.get('[data-tid="place-list-item"]').should('contain', 'KARDIOLOG');
  });
});