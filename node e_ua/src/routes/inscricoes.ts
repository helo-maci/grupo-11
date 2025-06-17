import { Router } from 'express';
import knex from '../db/knex';

const router = Router();

router.get('/usuario/:idUsuario', async (req, res) => {
    const { idUsuario } = req.params;
    try {
        const inscricoesDoUsuario = await knex('inscricoes')
            .select(
                'eventos.id',
                'eventos.titulo',
                'eventos.slug',
                'eventos.descricao',
                'eventos.data',
                'eventos.hora',
                'cursos.nome as nome_curso',
                'palestrantes.nome as nome_palestrante',
                'palestrantes.minicurriculo as mc_palestrante',
                'palestrantes.email as email_palestrante',
                'inscricoes.data_inscricao',
                knex.raw('CASE WHEN presenca_evento.id IS NOT NULL THEN TRUE ELSE FALSE END as tem_presenca')
            )
            .join('eventos', 'inscricoes.id_evento', 'eventos.id')
            .join('cursos', 'eventos.id_curso', 'cursos.id')
            .join('palestrantes', 'eventos.id_palestrante', 'palestrantes.id')

            .leftJoin('presenca_evento', function() {
                this.on('inscricoes.id_usuario', '=', 'presenca_evento.id_usuario')
                    .andOn('inscricoes.id_evento', '=', 'presenca_evento.id_evento');
            })
            .where('inscricoes.id_usuario', idUsuario)

        res.json({ inscricoes: inscricoesDoUsuario });
    } catch (error) {
        console.error('Erro ao buscar inscrições do usuário:', error);
        res.status(500).json({ message: 'Erro ao buscar suas inscrições', error });
    }
});

router.post('/', async (req, res) => {

  const { id_usuario, id_evento } = req.body;

  try {
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
