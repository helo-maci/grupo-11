import {Router} from 'express'

import inscricoes from './inscricoes'

const router = Router()

router.use('/inscricoes', inscricoes)

export default router