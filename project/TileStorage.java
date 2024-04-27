import java.io.File;

public class TileStorage {
  Tile[] tiles;

  public TileStorage() {
    // read all files from ./tiles
    File folder = new File("./tiles");
    File[] listOfFiles = folder.listFiles();

    tiles = new Tile[listOfFiles.length];

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        int[] array = FileUtils.read1DFile("./tiles/" + i + ".json");
        tiles[i] = Tile.from1D(array);
      }
    }

    // fallback();
  }

  private void fallback() {
    int cellSize = Application.cellSize;
    int[] redSprite =   {
      0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0,
      0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0,
      0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0,
      0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0,
      0, 0, 1, 1, 1, 2, 3, 3, 3, 3, 2, 1, 1, 1, 0, 0,
      0, 0, 1, 1, 3, 1, 1, 1, 1, 1, 1, 3, 1, 1, 0, 0,
      0, 1, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1, 0,
      0, 1, 3, 3, 3, 3, 1, 3, 3, 1, 3, 3, 3, 3, 1, 0,
      0, 0, 1, 1, 3, 3, 1, 3, 3, 1, 3, 3, 1, 1, 0, 0,
      0, 0, 1, 1, 1, 3, 3, 2, 2, 3, 3, 1, 1, 1, 0, 0,
      0, 1, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 1, 0,
      0, 1, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 1, 0,
      0, 0, 1, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 1, 0, 0,
      0, 0, 0, 1, 2, 1, 1, 2, 2, 1, 1, 2, 1, 0, 0, 0,
      0, 0, 0, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 0, 0, 0,
      0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0,
    };
    Tile[] _tiles = {
      Tile.generateTile(cellSize, 0),
      Tile.generateTile(cellSize, 1),
      Tile.generateTile(cellSize, 2),
      Tile.generateTile(cellSize, 3),
      Tile.from1D(redSprite),
    };
    // tiles = _tiles;
    for (int i = 0; i < _tiles.length; i++) {
      tiles[i] = _tiles[i];
    }
  }

  public void saveAll() {
    for (int i = 0; i < tiles.length; i++) {
      int[] array = tiles[i].to1D();
      FileUtils.write1DFile(array, "./tiles/" + i + ".json");
    }
  }

  public void set(int i, Tile tile) {
    System.out.println("grid changed");
    tiles[i] = tile;
    // int[] array = tile.to1D();
    // FileUtils.write1DFile(array, "./tiles/" + i + ".json");
  }
}
