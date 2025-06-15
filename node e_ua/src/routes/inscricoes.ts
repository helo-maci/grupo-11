import { Router } from 'express';
import knex from '../db/knex';
import autenticacao from '../middlewares/autenticacao';

const router = Router();

router.post('/', async (req, res) => {
  console.log('Body recebido:', req.body);

  const { id_usuario, id_evento } = req.body;

  if (!id_usuario || !id_evento) {
     res.status(400).json({ message: 'id_usuario e id_evento são obrigatórios' });
     return
  }

  try {
    const inscricaoExistente = await knex('inscricoes')
      .where({ id_usuario, id_evento })
      .first();

    if (inscricaoExistente) {
       res.status(400).json({ message: 'Você já se inscreveu nesse evento' });
       return
    }

    await knex('inscricoes').insert({ id_usuario, id_evento });

     res.status(200).json({ success: true, message: 'Inscrição realizada com sucesso' });
     return
  } catch (error) {
    console.log(error);
     res.status(500).json({ message: 'Erro ao realizar inscrição' });
    return
  }
});

export default router;
