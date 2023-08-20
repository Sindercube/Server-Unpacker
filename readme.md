# About

**Server Unpacker** is a **Fabric client-side mod** and **utility** that extracts Resource Packs from any servers that you join (or any arbitrary files).

The mod uses the same logic that the game uses to load Resource Packs, guaranteeing that you can extract the pack if the game can load it.

*Made for those pesky servers who like to hide their assets using corrupt ZIP file headers and whatnots.*

# Mod Usage

Drag and drop the mod into your `mods` folder and join any server that has a Resource Pack.

The extracted files will be in the same folder as the Resource Pack file, in `server-resource-packs` inside of your [`.minecraft`](https://minecraft.fandom.com/wiki/.minecraft) folder.

# GUI Usage

Run the .jar file to open a file extraction GUI.

You can select or drag and drop file(s) onto the GUI to extract them in the same directory.

# CLI Usage

You can extract multiple files by using the following command:

`java -jar server-unpacker-x.jar [files]`

Where `x` is replaced with your downloaded version.

# Credits

- Thank you to [Zardium](https://gitlab.com/Zardium) for writing the original code and making the GUI.
