Constituição — Projeto de teste simples: Código limpo e testável

Propósito
- Entregar software simples, legível e confiável que seja fácil de entender, manter e testar.

Princípios
- Clareza: código que explique sua intenção.
- Simplicidade: evitar complexidade prematura.
- Responsabilidade única: funções/objetos com uma única responsabilidade.
- Testabilidade: tudo testável via unidades; dependências injetáveis.

Padrões de Código
- Nomes descritivos; evitar abreviações obscuras.
- Funções curtas, < 50 linhas quando possível.
- Sem duplicação (DRY).
- Formatação e lint automáticos (ex.: prettier/eslint, black/flake8).

Testes
- Cobertura mínima alvo: 80%.
- Testes unitários para lógica, testes de integração para fluxos essenciais.
- Testes determinísticos e rápidos; usar doubles/mocks quando necessário.
- Test-driven development (TDD) encorajado para novas features.

Controle de Mudanças
- Pull Request: descrever intenção, link para issue, incluir testes e cambio de comportamento.
- Revisão obrigatória por pelo menos 1 outro desenvolvedor.
- CI verde (lint + testes) exigido antes do merge.

Integração Contínua
- Pipeline executa lint, formatação checagem, testes e relatório de cobertura.
- Falhas bloqueiam merge.

Documentação e Observabilidade
- README com propósito, setup e comandos testáveis.
- Comentários apenas para intenção ou decisões não óbvias.
- Exemplos mínimos de uso e como rodar testes localmente.

Governança
- Mudanças na constituição por consenso de mantenedores; pequenas ajustes por PR com justificativa.
- Exceções documentadas no PR e aprovadas por mantenedor.

Princípio final: priorizar código que um colega consiga entender e testar em menos de 10 minutos.
