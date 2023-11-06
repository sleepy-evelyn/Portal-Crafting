# Portal Crafting
A mod which adds a new recipe type which allows crafting using portals.

[![Available on Modrinth](https://raw.githubusercontent.com/intergrav/devins-badges/1aec26abb75544baec37249f42008b2fcc0e731f/assets/cozy/available/modrinth_vector.svg)](https://modrinth.com/mod/portal-crafting)
![Available on Quilt](https://raw.githubusercontent.com/intergrav/devins-badges/1aec26abb75544baec37249f42008b2fcc0e731f/assets/cozy/supported/quilt_vector.svg)
![Available on Fabric](https://raw.githubusercontent.com/intergrav/devins-badges/1aec26abb75544baec37249f42008b2fcc0e731f/assets/cozy/supported/fabric_vector.svg)
<img src="https://github.com/sleepy-evelyn/Portal-Crafting/assets/46009144/5551f2e0-20c5-4dda-88f9-f80224b2592a" height="57px">

## Features
- Support for all kinds of portals both vanilla and ~~modded~~ (coming soon)
- A bunch of different ways to return items such as merging into the portal frame or throwing items back
- Event hooks for modders who want to add a bit more flair
- Directintegration with EMI and REI

## Example

A recipe that accepts 1 ghast tear which will turn 3 obsidian blocks within a nether portal frame into crying obsidian
```json
{
  "type": "portalcrafting:nether",
  "input": {
      "item": "minecraft:ghast_tear",
  },
  "action": "merge_into_frame",
  "result": {
    "item": "minecraft:crying_obsidian",
    "count": 3
  }
}
```
