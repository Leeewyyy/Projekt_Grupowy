// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })

Cypress.Commands.add('searchAddressAndCheckResults', (address) => {
  cy.get('[name="search-address"]').type(address);
  cy.get('[data-tid="address-result"]').should('have.length.of.at.least', 1);
});

Cypress.Commands.add('selectFoundAddressAndCheckFilters', (address) => {
  cy.get('[data-tid="address-result"]').contains(address).click();
  cy.get('[data-tid="place-filters"]').should('be.visible');
});

Cypress.Commands.add('submitPlaceSearchAndCheckPlaces', () => {
  cy.get('[data-tid="main-submit"]').click();

  // Check if place count is visible, items in list show up and markers are visible on map
  cy.get('[data-tid="place-list-count"]').should('be.visible');
  cy.get('[data-tid="place-list-item"]').should('have.length.of.at.least', 1);
  cy.get('.leaflet-marker-icon').should('have.length.of.at.least', 1);
});

Cypress.Commands.add('attachFiles', (selector, ...fileNames) => {
  // selector has to be a referrer to an input[type=file]
  // only file names with extension from cypress/static/images directory
  // example: cy.attachFiles('[data-tid="images"] input[type=file]', 'cy_example1.png', 'cy_example2.png');

  const basePath = 'cypress/static/images';

  cy.get(selector).as('fileInput');
  const files = fileNames.map((name) => `${basePath}/${name}`);
  cy.get(selector).selectFile(files, { force: true });

  fileNames.forEach((fileName) => {
    cy.readFile(`${basePath}/${fileName}`, null).then((file) => {
      expect(Cypress.Buffer.isBuffer(file)).to.be.true
    })
  });
});

Cypress.Commands.add('changeSwitch', (selector, mode) => {
  // mode: true - switch to right, false - switch to left
  cy.get(`${selector} label`).click();
  
  if (mode === true) {
    cy.get(`${selector} input[type=checkbox]`).should('be.checked');
  } else {
    cy.get(`${selector} input[type=checkbox]`).should('not.be.checked');
  }
});

Cypress.Commands.add('navigationPlaceSearchType', (name, withSubmit = true) => {
  cy.get('[data-tid="top-place-search"] input').type(name, { force: true });
  if (withSubmit) {
    cy.get('[data-tid="top-search-submit"]').click();
    cy.url().should('include', 'places?search');
    cy.get('#placeList').should('contain.text', name);
  }
});