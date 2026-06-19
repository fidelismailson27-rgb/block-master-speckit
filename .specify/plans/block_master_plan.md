Plano de Implementação — Block Master (Tetris, Android, Kotlin, MVVM)

Resumo rápido
- Objetivo: construir o núcleo jogável (domínio testável) antes da UI. Priorizar código limpo e testável.
- Entregáveis por fases: domínio, engine, viewmodel, UI básica, polimento e testes.

Fase 0 — Preparação (1 dia)
- Configurar projeto Android (Kotlin), estrutura de pacotes conforme spec.
- Configurar ferramentas: Kotlin, coroutines, JUnit, mockk, detekt/ktlint, CI (GitHub Actions).

Fase 1 — Modelagem e utilitários (2 dias)
- Implementar modelos imutáveis: TetrominoType, PieceInstance, Cell, GameState.
- Bag generator (teste de distribuição).
- Representação do board (matriz 10x20) e helpers.

Fase 2 — Rotação e colisão (3 dias)
- Implementar RotationSystem (preferir SRS, documentar simplificações).
- CollisionDetector e wall-kicks.
- Testes unitários: rotações, kicks, colisões.

Fase 3 — GameEngine básico (4 dias)
- GameEngine: stepGravity(), applyCommand(), lockPiece(), clearLines().
- ScoringSystem e LevelSystem (linhas por nível, tabela de gravidade).
- Testes: locking, clears, pontuação, nível.

Fase 4 — ViewModel e integração (2 dias)
- GameViewModel expondo StateFlow<GameState> e API de comando.
- Integração Engine <-> ViewModel com coroutines e Dispatcher separação.
- Testes de ViewModel com doubles para Engine.

Fase 5 — UI mínima jogável (4 dias)
- GameActivity/GameFragment: render board, HUD (score/level/lines/next).
- ControlsView com botões touch (esq, dir, girar, soft, hard, pausa).
- Mapear entradas para ViewModel; testar em emulador.

Fase 6 — Polimento e testes de jogabilidade (3 dias)
- Ajustes de DAS/ARR, feedback visual, animações de linha clear.
- Salvar high score local.
- Instrumented UI tests: fluxo básico, pause/restart.

Fase 7 — CI e qualidade (1 dia)
- Pipeline: detekt/ktlint, unit tests, cobertura.
- Documentação README e decisões (SRS/rotations).

Dependências e ordens
- Fase 1 -> Fase 2 -> Fase 3 -> Fase 4 -> Fase 5 -> Fase 6 -> Fase 7
- Testes de cada módulo devem ser escritos antes de completar a fase (TDD recomendado).

Critérios de aceitação por fase
- Fase 2: rotações e colisões com 100% de casos SRS implementados ou documentados.
- Fase 3: engine passa cenários unitários (linhas, pontuação, nível).
- Fase 5: experiência jogável em emulador com controles touch.

Estimativa total: ~16 dias úteis (timebox 3 semanas com buffer).

Próximos passos
- Confirmar estimativa e prioridades (priorizar domínio e engine).
- Gerar tasks/todos a partir deste plano (opcional).
