[![Github](https://img.shields.io/badge/github-434956?style=for-the-badge&logo=github&logoColor=8994ce)](https://github.com/Sindercube/Server-Unpacker)
[![Modrinth](https://img.shields.io/badge/modrinth-434956?style=for-the-badge&logo=modrinth&logoColor=8994ce)](https://modrinth.com/mod/Server-Unpacker)
[![Curseforge](https://img.shields.io/badge/curseforge-434956?style=for-the-badge&logo=curseforge&logoColor=8994ce)](https://www.curseforge.com/minecraft/mc-mods/Server-Unpacker)
![Game Versions](https://img.shields.io/modrinth/game-versions/B3fgRqfr?style=for-the-badge&labelColor=434956&color=8994ce)

# About

**Server Unpacker** is a **Fabric client-side mod** that extracts any resource pack files that can be loaded by Minecraft.

*Made for those pesky servers who like to hide their assets using corrupt ZIP file headers.*

# Mod Usage

Drag and drop the mod into your `mods/` folder and join any server that has a required resource pack.

The extracted files will be in your [`.minecraft`](https://minecraft.wiki/.minecraft)`/extracted-packs/` folder.

# GUI Usage

Run the .jar file to open the GUI.

You can select or drag and drop file(s) onto the GUI to extract them in the same directory.

# CLI Usage

You can extract multiple files by using the following command:

`java -jar server-unpacker-x.jar [files]`

Where `x` is your downloaded version.

# Credits

- Thank you to [Zardium](https://gitlab.com/Zardium) for writing the original code and making the GUI part of the mod.
