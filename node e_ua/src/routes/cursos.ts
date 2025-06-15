import { Router } from 'express';
import knex from '../db/knex';

const router = Router();

router.get('/', async (req, res) => {
  try {
    const cursos = await knex('cursos').select('*');
    res.json({ cursos });
  } catch (error) {
    res.status(500).json({ message: 'Erro ao buscar cursos', error });
  }
});

export default router;
