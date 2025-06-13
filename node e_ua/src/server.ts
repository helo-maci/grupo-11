import express, { Request, Response, NextFunction } from 'express'
import cors from 'cors'
import routes from './routes'

import { ZodError } from 'zod'

const app = express()
app.use(cors())

app.use(express.json())

const PORT = 3001

app.use(routes)

app.use((
  error: Error,
  req: Request,
  res: Response,
  next: NextFunction
) => {
  if (res.headersSent) {
    return next(error);
  }

  if (error instanceof ZodError) {
    const errosFormatados = error.errors.map((issue) => ({
      field: issue.path.join('.'),
      message: issue.message,
    }));
      res.status(400).json({
      status: "erro",
      message: "Validação falhou",
      errors: errosFormatados
    });
    return;
  }
  

  console.log(error)
  res.status(500).json({
    status: "erro",
    message: "Erro no servidor"
  })
  return
})

app.listen(PORT, () => {
  console.log('Express iniciou na porta:' + PORT)
})
