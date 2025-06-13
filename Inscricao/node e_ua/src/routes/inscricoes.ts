import { Router } from 'express'
import knex from './../db/knex'
import { z, ZodError } from 'zod'

const router = Router()

router.get('/', (req, res) => {
  knex('inscricoes').then((inscricoes) => {
    res.json({ inscricao: inscricoes })
  })
})
router.post("/", async (req, res) => {
  const registerBodySchema = z.object({
    nome: z.string().min(3, "Nome é obrigatório"),
    cpf: z.string().min(11, "CPF inválido"),
    email: z.string().email("Email inválido"),
    telefone: z.string().min(8, "Telefone inválido"),
  })

  try {
    const objSalvar = registerBodySchema.parse(req.body)

    const id_inscricao = await knex('inscricoes').insert(objSalvar)
    const inscricoes = await knex('inscricoes')
    res.status(201).json({
      message: 'Inscrição realizada com sucesso',
      inscricao: inscricoes
    })
  } catch (error) {
    if (error instanceof ZodError) {
        res.status(400).json({
        mensagem: 'Erro na validação dos dados',
        issues: error.issues
      });
      return
    }
    res.status(500).json({ mensagem: 'Erro interno no servidor' });
  }
});

export default router
