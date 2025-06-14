import { Router } from 'express'
import knex from '../db/knex'
import { z } from 'zod'
import { hash } from 'bcrypt'

const router = Router()

router.get('/', (req, res) => {
  knex('usuarios').then((users) => {
    res.json({ usuarios: users })
  })
})

router.post("/", async (req, res) => {
  const registerBodySchema = z.object({
    nome: z.string().min(3, { message: "Nome precisa ter pelo menos 3 caracteres" }),
    email: z.string().email({ message: "Email inválido" }),
    cpf: z.string().min(11, {message: "Cpf Inválido"}),
    senha: z.string().min(6, { message: "Senha precisa ter pelo menos 6 caracteres" })
  })

  const objSalvar = registerBodySchema.parse(
    req.body
  )

  objSalvar.senha = await hash(objSalvar.senha, 8)

  const id_usuario = await knex('usuarios').insert(objSalvar)

  const usuarios = await knex('usuarios')
    .where({
      id: id_usuario[0]
    })

  res.json({
    usuario: usuarios
  })

})

export default router
