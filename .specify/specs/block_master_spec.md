Block Master — Tetris para Android

Título
- Block Master — Tetris para Android
- Package: com.blockmaster.game
- Plataforma: Android (Kotlin puro, MVVM)

Visão e objetivos
- Jogo estilo Tetris, foco em código limpo, testável e modular.
- Jogabilidade clássica com 10x20, 7 tetrominós, rotação, movimento lateral, soft/hard drop, limpeza de linhas, pontuação, níveis, game over e reiniciar.
- Interface touch: botões esquerda, direita, girar, soft drop, hard drop, pausa; mostrador de score, nível, linhas e próxima peça.

Requisitos funcionais
1. Tabuleiro
   - Grade 10 colunas x 20 linhas (visíveis).
   - Sistema de células discretas (ocupada/vazia, cor/tipo).
2. Peças
   - Set de 7 tetrominós: I, O, T, S, Z, J, L.
   - Rotação implementada (preferir Super Rotation System/SRS ou sistema documentado similar).
   - Spawn aleatório consistente (bag de 7 sugerida) para evitar sequências ruins.
3. Movimentação
   - Movimentos: esquerda, direita, soft drop (acelera descida), hard drop (queda instantânea).
   - Rotação de 90° (horária e/ou anti-horária — mínimo 1 sentido exigido).
   - Colisões e wall-kicks (implementar SRS wall-kicks ou uma versão simplificada).
4. Linhas e pontuação
   - Remoção de linhas completas com compactação acima.
   - Sistema de pontuação:
     - Soft drop: +1 por célula (ou configurável).
     - Hard drop: +4 por linha de queda (ou regra similar).
     - Clear simples/multi: base de pontos por número de linhas cleared (ex.: single=100, double=300, triple=500, tetris=800) multiplicado por nível.
   - Track de linhas acumuladas para avanço de nível.
5. Níveis e velocidade
   - Nível inicial 0 (ou 1) com velocidade base.
   - A cada N linhas (ex.: 10) aumenta o nível e acelera a gravidade (intervalo de queda menor).
   - Tabela de gravidade/intervalos documentada (ex.: nível->ms por step).
6. Game over / Reiniciar
   - Game over detectado quando peça spawn colide imediatamente.
   - Reiniciar reseta estado, score, nível, linhas e sorteio de peças.
7. UI e controles
   - Layout principal com: tabuleiro, painel lateral inferior com botões touch (esquerda, direita, girar, soft, hard, pausa), HUD com score, nível, linhas e preview da próxima peça.
   - Botões devem ser grandes o suficiente para uso em dispositivos móveis.
   - Pausa congela game loop e animações; mostra overlay com opções (continuar, reiniciar, sair).
8. Áudio/Assets
   - Sem assets protegidos por direitos autorais; usar cores/figuras simples ou assets próprios.
9. Persistência (opcional)
   - Salvar high score localmente (SharedPreferences/Room).

Arquitetura (MVVM)
- Pacote raiz: com.blockmaster.game
- Módulos/Packages:
  - model (dados puros): Tetromino, Block, Board, GameState, Score
  - ui (views & fragments/activities): GameActivity/GameFragment, ControlsView, HUDView
  - viewmodel: GameViewModel
  - domain (regras de jogo): GameEngine, CollisionDetector, RotationSystem, ScoringSystem, LevelSystem, PieceGenerator
  - data (persistência): HighScoreRepository
  - util (coroutines, timers)
- Responsabilidades:
  - GameEngine: lógica pura — aplica entrada (move/rotate/drop), atualiza board, detecta line clears, aplica pontuação e nível; não conhece Android UI.
  - GameViewModel: expõe StateFlow/LiveData de GameState para UI; recebe comandos da UI e repassa ao GameEngine; gerencia coroutines e lifecycle.
  - UI: renderiza GameState, envia comandos de toque para ViewModel.
- Comunicação:
  - GameState (data class imutável) contendo board matrix, currentPiece (posição/rotação/tipo), nextPiece, score, level, lines, isPaused, isGameOver.
  - Observers reagem a mudanças e fazem animações presentes no layer UI.

Detalhes de implementação
- Representação do tabuleiro: Array<Int> ou Array<Array<Cell>> (0=vazio, >0=tipo).
- Tetromino: enum class TetrominoType { I,O,T,S,Z,J,L } + matriz de 4x4 por rotação ou lista de offsets.
- Rotação: implementar SRS kick tests (documentar tabela) ou uma variante testada.
- Bag generator: gerar sequência de 7 com shuffle, reembaralhar quando esgotada.
- Game loop: coroutine que aplica “gravidade” com delay baseado no nível; pausar cancela/pausa o loop.
- Concurrency: usar kotlin.coroutines + Dispatchers.Default para GameEngine, atualizar StateFlow no Main dispatcher.
- Input debouncing e auto-repeat: toques longos para movimento lateral com DAS/ARR configurável (opcional).

APIs públicas (exemplo)
- GameViewModel:
  - fun start()
  - fun pauseToggle()
  - fun restart()
  - fun moveLeft()
  - fun moveRight()
  - fun rotateClockwise()
  - fun softDropStart() / softDropStop() (opcional)
  - fun hardDrop()
  - val uiState: StateFlow<GameState>
- GameEngine (testável sem Android):
  - fun stepGravity(): GameStateChange
  - fun applyCommand(cmd: GameCommand): GameStateChange
  - fun reset()
- Data classes principais:
  - data class GameState(board: Array<IntArray>, current: PieceInstance?, next: TetrominoType, score:Int, level:Int, lines:Int, isPaused:Boolean, isGameOver:Boolean)

Testes
- Unitários (Kotlin/JUnit):
  - Tetromino rotation correctness (incl. wall kicks).
  - Collision detection and locking.
  - Line clear logic (single, multi).
  - Scoring calculation across scenarios.
  - Level progression and gravity timing.
  - Bag generator distribution tests.
  - Game over detection and restart resets.
- Instrumentation/UI:
  - Basic flows: play until line clear, level up, pause/resume, restart.
- Mocks: GameEngine test doubles for ViewModel tests.
- CI: rodar testes unitários em cada PR.

Critérios de aceitação
- Jogável em dispositivo Android físico/emulador.
- Movimentos responsivos: lateral, rotate, soft/hard drop funcionais.
- Linhas removidas corretamente e pontuação atualizada.
- Níveis mudam velocidade e afetam gravidade.
- Game over e reiniciar funcionam.
- UI mostra score, level, lines, next piece.
- Cobertura de testes unitários para regras core (meta: ≥ 80%).

UI/UX mínimos
- Touch-friendly controls com feedback visual ao pressionar.
- Preview claro da próxima peça.
- Overlay de pausa com opções.
- Indicação visual de linhas limpadas (animação simples).

Dados de configuração (valores iniciais sugeridos)
- Linhas por nível: 10
- Pontuação base: single=100, double=300, triple=500, tetris=800
- Gravidade (ms por linha) exemplo: level0=1000ms, level1=830ms, level2=716ms ... (tabela configurável e documentada)
- Soft drop: acelera para 50ms/step (configurável)
- Hard drop: aplica pontuação adicional por distância

Considerações legais
- Não incluir músicas, imagens ou sprites com copyright; usar formas e cores próprias ou assets permissivos (MIT/public domain).

Entrega e estrutura de arquivos sugerida
- /app/src/main/kotlin/com/blockmaster/game/...
  - model/
  - domain/
  - viewmodel/
  - ui/
  - data/
  - util/
- Tests em /app/src/test/kotlin/...

Observações finais
- Priorizar código testável: lógica em domain, sem dependências Android.
- Começar implementando: Piece model, RotationSystem, Board & Collision, then GameEngine, then ViewModel, then UI.
- Documentar SRS/rotations e decisões no repo.
