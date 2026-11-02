# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.1.0] - 2026-11-01

### Added

- Added the `updraft_disabled_dimensions` config option to disable updrafts in specific dimensions.
    - Updrafts are disabled in the Nether by default.
- Added the `disable_in_creative` config option to stop the glider deploying in Creative mode.

### Fixed

- Fixed Gliders deploying in Spectator mode and while flying in Creative mode.
- Fixed being unable to steer when the horizontal speed config option is above 1.0.

## [2.0.0] - 2026-10-16

### Added

- Added support for Minecraft 1.20.1 (Fabric and Forge).
- Added the horizontal speed config option and YACL config screen to 26.x.
- Added Trinkets Updated support to NeoForge on 26.x.

### Changed

- All versions are now built from a single codebase.

### Fixed

- Fixed Gliders not being repairable with Phantom Membranes in an anvil on 1.21.1.

## [1.4.1] - 2026-08-12

### Fixed

- Fixed dyed glider model.
- Fixed random crash.

## [1.4.0] - 2026-07-26

### Added

- Added a config option to control horizontal speed.

### Changed

- Switched from Cloth Config to YACL.

## [1.3.2] - 2026-05-26

### Added

- Added a `glider_usable_items` tag for items that are able to be used while gliding.

## [1.3.1] - 2026-05-07

### Fixed

- Fixed error when Cloth Config isn't present.
- Fixed visual bug with using Spears when gliding.
- Players can no longer break blocks or swing swords while gliding.

## [1.3.0] - 2026-05-02

### Changed

- Decreased volume of gliding sound.
- You can no longer sprint while gliding.
- Increased Glider durability.

### Fixed

- Fixed Gliders not taking damage while in a Curios slot.
- Fixed Gliders not displaying in first person while in a Curios slot.

## [1.2.1] - 2026-05-02

### Fixed

- Improved keybind handling.
- Fixed Glider activating while climbing or in a boat.

## [1.2.0] - 2026-05-02

### Added

- Added optional Curios and Trinkets integration.
- Added a config option to make the Glider equippable in the chestplate slot.
- Added an optional keybind to trigger deployment.

### Changed

- Campfires now only provide a boost if they're actually lit.

### Fixed

- Fixed small gaps in the Glider model.
- Fixed missing tag translations for `reliable_gliders:updraft_blocks`

## [1.1.1] - 2026-04-30

### Fixed

- Fixed Gliders not displaying properly in multiplayer.

## [1.1.0] - 2026-04-29

### Changed

- Gliders now only deploy when falling 2+ blocks.
- Players now play a subtle leg kicking animation while gliding.

## [1.0.0] - 2026-04-28

- Initial release.