# Portal Crafting
This mod adds a recipe type which enables crafting using portals.

![Available on Modrinth](https://raw.githubusercontent.com/intergrav/devins-badges/1aec26abb75544baec37249f42008b2fcc0e731f/assets/cozy/available/modrinth_vector.svg)
![Requires Architectury](https://raw.githubusercontent.com/intergrav/devins-badges/1aec26abb75544baec37249f42008b2fcc0e731f/assets/cozy/requires/architectury-api_vector.svg)
![Available on Quilt](https://raw.githubusercontent.com/intergrav/devins-badges/1aec26abb75544baec37249f42008b2fcc0e731f/assets/cozy-minimal/supported/quilt_vector.svg)
![Available on Fabric](https://raw.githubusercontent.com/intergrav/devins-badges/1aec26abb75544baec37249f42008b2fcc0e731f/assets/cozy-minimal/supported/fabric_vector.svg)
<img height="57px" src="https://github.com/sleepy-evelyn/Portal-Crafting/assets/46009144/3d4776d7-225f-4db4-a576-bf6b04efc028">

## Features
- Support for all kinds of portals both vanilla and modded
- Support for up to 4 unique items as ingredient inputs
- A bunch of different ways to return items such as merging into the portal frame
- Integration with EMI and REI showing recipes

## Example

A recipe that accepts 4 obsidian and 1 ghast tear which when thrown into an end portal returns 4 crying obsidian to the player
```json
{
  "type": "portalcrafting:portal_crafting",
  "input": [
    { 
      "item": "minecraft:obsidian",
      "count": 4 
    },
    { 
      "item": "minecraft:ghast_tear" 
    }
  ],
  "portal_block": "minecraft:end_portal",
  "action": "return_to_player",
  "result": {
    "item": "minecraft:crying_obsidian",
    "count": 4
  }
}
```
