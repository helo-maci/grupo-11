/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.up = function(knex) {
    return knex.schema.createTable('inscricoes', function(table) {
    table.increments('id').primary();
    table.integer('id_usuario').unsigned().notNullable();
    table.integer('id_evento').notNullable();
    table.timestamp('data_inscricao').defaultTo(knex.fn.now());
    table.unique(['id_usuario', 'id_evento'], 'inscricoes_id_usuario_id_evento_unique');
    table.index('id_evento', 'inscricoes_id_evento_foreign');
    table.foreign('id_usuario', 'inscricoes_id_usuario_foreign')
         .references('id')
         .inTable('usuarios')
         .onDelete('CASCADE');
    table.foreign('id_evento', 'inscricoes_id_evento_foreign')
         .references('id')
         .inTable('eventos')
         .onDelete('CASCADE');
 }).then(() => {
    console.log('Criado a tabela de Inscricao')

  })
};

/**
 * @param { import("knex").Knex } knex
 * @returns { Promise<void> }
 */
exports.down = function(knex) {
    return knex.schema.dropTable('inscricoes').then(() => {
    console.log('Deletado tabela de inscricoes')
  })
};
