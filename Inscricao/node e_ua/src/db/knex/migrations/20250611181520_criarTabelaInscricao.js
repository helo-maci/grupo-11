/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = function (knex) {
  return knex.schema.createTable('inscricoes', (table) => {
    table.increments('id');
    table.string('nome', 100).notNullable()
    table.string('cpf', 11).notNullable().unique()
    table.string('email', 100).notNullable()
    table.string('telefone', 11).notNullable()
    table.timestamp('created_at').defaultTo(knex.fn.now());
    table.timestamp('updated_at').defaultTo(knex.fn.now());
  }).then(() => {
    console.log('Criado a tabela de inscricao');
  });
};
/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = function(knex) {
  return knex.schema.dropTable('inscricoes').then(() => {
    console.log('Deletado tabela de inscricao')
  })
};
