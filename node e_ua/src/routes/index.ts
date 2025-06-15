import { Router } from 'express';
import usuarios from './usuarios';
import cursos from './cursos';
import eventos from './eventos';
import inscricoes from './inscricoes';
import veriUsuarios from './veriUsuarios'

const router = Router();

router.use('/usuarios', usuarios);
router.use('/cursos', cursos);
router.use('/eventos', eventos);
router.use('/inscricoes', inscricoes);
router.use('/veriUsuarios', veriUsuarios)

export default router;
