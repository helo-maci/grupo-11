import { Router } from 'express';
import knex from '../db/knex';

const router = Router();

router.post('/', async (req, res) => {
  console.log('Body recebido:', req.body);

  const { id_usuario, id_evento } = req.body;

  try {
    await knex('inscricoes').insert({ id_usuario, id_evento });

  } catch (error) {
    console.log(error);
     res.status(500).json({ message: 'Erro ao realizar inscrição' });
    return
  }
});

export default router;
