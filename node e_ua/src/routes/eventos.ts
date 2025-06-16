import { Router } from 'express';
import knex from '../db/knex';

const router = Router();

router.get('/', async (req, res) => {
  const { curso } = req.query;

  try {
    const query = knex('eventos')
      .select('eventos.*', 'cursos.nome as nome_curso', 'palestrantes.nome as nome_palestrante', 'palestrantes.minicurriculo as mc_palestrante', 'palestrantes.email as email_palestrante') 
      .join('cursos', 'eventos.id_curso', 'cursos.id') 
      .join('palestrantes', 'eventos.id_palestrante', 'palestrantes.id');

    if (curso) {
      query.where('eventos.id_curso', curso);
    }

    const eventos = await query;
    res.json({ eventos });
  } catch (error) {
    res.status(500).json({ message: 'Erro ao buscar eventos', error });
  }
});


router.get('/:slug', async (req, res) => {
  const { slug } = req.params;

  try {
    const evento = await knex('eventos')
      .select('eventos.*', 'cursos.nome as nome_curso', 'palestrantes.nome as nome_palestrante', 'palestrantes.minicurriculo as mc_palestrante', 'palestrantes.email as email_palestrante')
      .join('cursos', 'eventos.id_curso', 'cursos.id') 
      .join('palestrantes', 'eventos.id_palestrante', 'palestrantes.id') 
      .where('eventos.slug', slug)
      .first(); 

    if (!evento) {
      res.status(400).json({ message: 'Evento não encontrado' });
      return
    }

    res.json({ evento });
  } catch (error) {
    res.status(500).json({ message: 'Erro ao buscar evento', error });
    return
  }
});

export default router;
