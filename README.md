# WorldCupBestTeamSimulator

![Kotlin](https://img.shields.io/badge/Kotlin-2.4.0-purple?logo=kotlin)
![Compose Multiplatform](https://img.shields.io/badge/Compose_Multiplatform-1.11.1-blue)
![Android](https://img.shields.io/badge/Android-API_24+-brightgreen?logo=android)
![iOS](https://img.shields.io/badge/iOS-16+-black?logo=apple)
![JujubaSVG](https://img.shields.io/badge/JujubaSVG-1.4.2-orange)

> A Compose Multiplatform sample app demonstrating **[JujubaSVG](https://github.com/CodandoTV/jujubaSVG)** — the SVG manipulation library for Kotlin Multiplatform.

Build your 2026 World Cup dream team by searching real players, selecting them, and tapping their avatars onto an interactive SVG soccer field.

---

## Screenshots

<video src="img/Screen_recording_20260718_192658.mp4" controls width="720"></video>

---

## JujubaSVG features demonstrated

| Feature | Usage |
|---------|-------|
| **Render SVG** | `JujubaSVG(svgText, commander, onElementClick)` renders `soccer-field.svg` inside a WebView |
| **Click handling** | `onElementClick` callback returns the tapped element's `id`, `coordinate`, and `rootCoordinate` |
| **Add rounded image** | `Command.AddRoundedImage` places a player avatar at the tapped position |
| **Remove node** | `Command.RemoveNode(id)` removes a player image from the field |
| **Commander pattern** | `rememberJujubaCommander()` + `commander.execute(command)` queues and sends commands to the WebView |

```kotlin
// Render the SVG field
val commander = rememberJujubaCommander()

JujubaSVG(
    svgText = svgText,
    commander = commander,
    modifier = Modifier.fillMaxSize(),
    onElementClick = { nodeInfo ->
        // Add player avatar where the user tapped
        commander.execute(
            Command.AddRoundedImage(
                imageId = player.svgImageId,
                elementId = "soccer_field",
                heightInPx = 60,
                widthInPx = 60,
                coordinate = nodeInfo.rootCoordinate.copy(
                    x = nodeInfo.rootCoordinate.x - 30,
                    y = nodeInfo.rootCoordinate.y - 30
                ),
                imageUrl = player.avatarUrl
            )
        )
    }
)

// Remove a player image
commander.execute(Command.RemoveNode(existingImageId))
```

---

## Build & run

**Android:**
```bash
./gradlew :androidApp:assembleDebug
```

**iOS:**
Open `iosApp/` in Xcode and run on a simulator or device.

---

## Project structure

```
shared/src/commonMain/
├── kotlin/…/ui/home/
│   ├── HomeScreen.kt        # JujubaSVG rendering + command execution
│   ├── HomeViewModel.kt     # Tracks which player images are on the field
│   └── HomeScreenUiState.kt # UI state (svgImageId per player)
├── composeResources/files/
│   ├── soccer-field.svg     # SVG soccer field rendered by JujubaSVG
│   └── world_cup_2026_teams.json  # Player data
```

---

## Links

- [JujubaSVG repository](https://github.com/CodandoTV/jujubaSVG) — full API docs, all command types, and integration guide
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) — official documentation
