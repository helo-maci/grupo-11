import { Router } from 'express';
import knex from '../db/knex';

const router = Router();

const processarFotoPalestrante = (evento) => {
  if (evento && evento.foto_palestrante instanceof Buffer) {

    const base64Image = evento.foto_palestrante.toString('base64');
    
    evento.foto_palestrante_b64 = `data:image/jpeg;base64,${base64Image}`;
    
    delete evento.foto_palestrante; 
  } else {
    evento.foto_palestrante_b64 = null;
  }
  return evento;
};

router.get('/', async (req, res) => {
  const { curso } = req.query;

  try {
    const query = knex('eventos')
      .select(
        'eventos.*',
        'cursos.nome as nome_curso',
        'palestrantes.nome as nome_palestrante',
        'palestrantes.minicurriculo as mc_palestrante',
        'palestrantes.email as email_palestrante',
        'palestrantes.foto as foto_palestrante'
      )
      .join('cursos', 'eventos.id_curso', 'cursos.id')
      .join('palestrantes', 'eventos.id_palestrante', 'palestrantes.id');

    if (curso) {
      query.where('eventos.id_curso', curso);
    }

    let eventos = await query;

    eventos = eventos.map(processarFotoPalestrante);

    res.json({ eventos });
  } catch (error) {
    console.error('Erro ao buscar eventos:'); 
    res.status(500).json({ message: 'Erro ao buscar eventos' });
  }
});

router.get('/:slug', async (req, res) => {
  const { slug } = req.params;

  try {
    let evento = await knex('eventos')
      .select(
        'eventos.*',
        'cursos.nome as nome_curso',
        'palestrantes.nome as nome_palestrante',
        'palestrantes.minicurriculo as mc_palestrante',
        'palestrantes.email as email_palestrante',
        'palestrantes.foto as foto_palestrante' 
      )
      .join('cursos', 'eventos.id_curso', 'cursos.id')
      .join('palestrantes', 'eventos.id_palestrante', 'palestrantes.id')
      .where('eventos.slug', slug)
      .first();

    if (!evento) {
      res.status(404).json({ message: 'Evento não encontrado' });
      return;
    }

    evento = processarFotoPalestrante(evento);

    res.json({ evento });
  } catch (error) {
    console.error('Erro ao buscar evento:');
    res.status(500).json({ message: 'Erro ao buscar evento' });
    return;
  }
});

export default router;