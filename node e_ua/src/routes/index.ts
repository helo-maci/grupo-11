import {Router} from 'express'

import usuarios from './usuarios'

const router = Router()

router.use('/usuarios', usuarios)

export default router