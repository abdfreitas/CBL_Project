# Bloom or Doom

Java game for 2IP90. You move around, fight ghosts, protect a flower and chickens, pick up drops, and try to survive waves! The code is built with plain Java (Swing + AWT).

---

## Features

- **Player movement & combat**
  - WASD to move, mouse to aim, **Left Click** to attack, **Right Click** to interact
  - **Shift** to sprint, **Space** to dodge, **B** boost (debug/cheat)
  - **F12** toggles debug overlays

- **Waves & spawning**
  - Waves spawn groups of monsters that scale in difficulty
  - Weighted random spawns keep pacing interesting

- **Pickups & inventory**
  - Coins, hearts, water bottles, etc. (see `src/drops/`)

- **Win / Lose conditions**
  - **Lose:** Player HP <= 0
  - **Lose:** The main flower (Flower2) is destroyed
  - **Win:** Open the chest
  - **Secret Win:** Reach **wave 11** (“Ghost Hunter” ending)

- **Audio**
  - Background music + SFX with volume controls (`SoundManager`)

- **Config**
  - Simple key value configuration from `res/user/config.txt`

---

## 🕹 Controls

| Action           | Key / Mouse |
|------------------|-------------|
| Move             | **W A S D** |
| Dodge            | **Space**   |
| Sprint           | **Shift**   |
| Attack           | **Left Click** |
| Interact / Use   | **Right Click** |
| Boost (debug)    | **B** |
| Toggle Debug UI  | **F12** |

---

## Project Structure (key parts)

```
src/
  main/
    GamePanel.java         # main game loop, rendering, update()
    Main.java              # creates the JFrame and starts the game
    KeyHandler.java        # keyboard input
    MouseInput.java        # mouse move/drag tracking
    MouseClickInput.java   # mouse button actions
    SoundManager.java      # music & SFX
    CollisionDetection.java# collisions (player/entities/drops/tiles)
  entity/
    Entity.java            # base class (HP, invincibility, draw/update)
    Player.java            # the player logic
    Monster.java           # ghosts (AI chase + hit reactions)
    Flower2.java           # main objective plant (protect it!)
    ... (Chicken, Snail1, ThroneBearer, Wizard, etc.)
  drops/
    DropSupercClass.java   # base class for drops (magnet behavior)
    Coin.java, Heart.java, WaterBottle.java, ...
  tile/
    TileManager.java       # loads map from txt and draws tiles
    Tile.java              # tile data (image + collision)
  GUI/
    GUI.java               # HUD: controls hints, coins, wave, water can
  user/
    ConfigManager.java     # loads res/user/config.txt
```

Assets (images, sounds, maps) are in `res/…` (e.g., `res/entities/...`, `res/sound/...`, `res/maps/world01.txt`).

---

## Running the Game

### Requirements
- Java 17+ 
- Any IDE (IntelliJ / VS Code / Eclipse)

### From an IDE
1. Open the project folder.
2. Make sure **resources** (`res/`) are on the classpath.
3. Run `src/main/Main.java`.

### From the command line
```bash
# From project root
javac -d out $(find src -name "*.java")
java -cp out src.main.Main
```

---

## Configuration

Create or edit: `res/user/config.txt`

Example:
```
# Monster tuning
monster.hpMax=50
monster.invincibleCounterMax=20
monster.stunCounterMax=20
monster.speed=0.12
```

- Use `ConfigManager.getInt/getDouble/getBoolean` in code.
- Lines starting with `#` are comments.

---

## Game Rules (Win / Lose)

- **You lose when:**
  - `Player.HP <= 0` → the window closes (optional “Game Over” popup)
  - `Flower2.hp <= 0` → *“Your plant has been attacked by the ghosts :( You lose!”*

- **You win when:**
  - You open the chest (`Player.pickUpObject("Chest")` triggers `winGame()`)
  - **Secret ending:** `wave >= 11` → `winGameSecret()` → *“Ghost Hunter 👻”*

All endings are handled inside `GamePanel` to keep everything consistent and avoid double triggers.

---

## Audio

`SoundManager` loads music and SFX with simple methods:

```java
sound.playMusic(index, loop);
sound.stopMusic();
sound.setMusicGainDb(-10f); // volume trim dB

sound.playSfx(index);
sound.setSfxGainDb(-8f);
sound.stopAllSfx();
```

---

## Debugging

- Press **F12** to toggle an overlay with useful values (e.g., spawn counters, entity counts).
- `KeyHandler.debugEnabled` is a `static` flag read by drawers.
- Collision boxes can be drawn for entities and tiles in debug mode.

---

## Coding Style & Docs

- Most Checkstyle warnings are addressed (no star imports, consistent braces/spaces, simplified boolean returns, etc.).
- Javadoc added to public classes/methods (simple, human‑readable comments).
- Prefer early returns and small helpers to avoid deep nesting.
- Switches have `default` branches to satisfy `MissingSwitchDefaultCheck`.


---

