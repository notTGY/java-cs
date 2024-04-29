public class TileStorage {
  Tile[] tiles;

  public TileStorage() {
    try {
      fromFile();
    } catch (Exception e) {
      e.printStackTrace();
      fallback();
    }
  }

  public void saveAll() {
    toFile();
  }

  // private void fromFiles() {
  //   File folder = new File("./tiles");
  //   File[] listOfFiles = folder.listFiles();

  //   tiles = new Tile[listOfFiles.length];

  //   for (int i = 0; i < listOfFiles.length; i++) {
  //     if (listOfFiles[i].isFile()) {
  //       int[] array = FileUtils.read1DFile("./tiles/" + i + ".json");
  //       tiles[i] = Tile.from1D(array);
  //     }
  //   }
  // }

  // private void toFiles() {
  //   for (int i = 0; i < tiles.length; i++) {
  //     int[] array = tiles[i].to1D();
  //     FileUtils.write1DFile(array, "./tiles/" + i + ".json");
  //   }
  // }

  private void fromFile() {
    int[] array = FileUtils.read1DFile("./tiles.json");
    int len = array.length;

    int cellSize = Application.cellSize;
    int batchSize = Application.batchSize;
    
    int batches = len / (cellSize * cellSize);
    int tilesLen = batchSize * batches;
    Tile[] _tiles = new Tile[tilesLen];

    for (int i = 0; i < batches; i++) {
      int start = i * (cellSize * cellSize);
      
      for (int j = 0; j < batchSize; j++) {
        int[][] data = new int[cellSize][cellSize];
        for (int k = 0; k < cellSize; k++) {
          for (int l = 0; l < cellSize; l++) {
            int n = array[start + k * cellSize + l];
            data[k][l] = (n >> (2*j)) % 4;
          }
        }
        _tiles[i * batchSize + j] = Tile.fromData(data);
      }
    }

    tiles = new Tile[tilesLen];
    for (int i = 0; i < _tiles.length; i++) {
      tiles[i] = _tiles[i];
    }
  }

  private void toFile() {
    int cellSize = Application.cellSize;
    int batchSize = Application.batchSize;

    int batches = tiles.length / batchSize;
    if (tiles.length > batches * batchSize) {
      batches += 1;
    }


    int[] array = new int[batches * cellSize * cellSize];

    int[][] data = new int[cellSize][cellSize];
    for (int i = 0, m = -1; i < tiles.length; i++) {
      if (i % batchSize == 0) {
        m += 1;
        for (int j = 0; j < cellSize; j++) {
          for (int k = 0; k < cellSize; k++) {
            data[j][k] = 0;
          }
        }
      }

      for (int j = 0; j < cellSize; j++) {
        for (int k = 0; k < cellSize; k++) {
          data[j][k] += tiles[i].data[j][k] << 2*(i % batchSize);
        }
      }
      for (int j = 0; j < cellSize; j++) {
        for (int k = 0; k < cellSize; k++) {
          array[m * cellSize * cellSize + j * cellSize + k] = data[j][k];
        }
      }
    }

    FileUtils.write1DFile(array, "./tiles.json");
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
    tiles = new Tile[_tiles.length];
    for (int i = 0; i < _tiles.length; i++) {
      tiles[i] = _tiles[i];
    }
  }

  public void set(int i, Tile tile) {
    System.out.println("grid changed");
    tiles[i] = tile;
    // int[] array = tile.to1D();
    // FileUtils.write1DFile(array, "./tiles/" + i + ".json");
  }
}
