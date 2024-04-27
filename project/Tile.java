public class Tile {
  public int size;
  public int[][] data;

  public Tile(int size) {
    this.size = size;
    this.data = new int[size][size];

  }

  public static Tile fromData(int[][] data) {
    Tile tile = new Tile(data.length);
    tile.data = data;
    return tile;
  }

  public static Tile generateTile(int size, int fill) {
      Tile tile = new Tile(size);
      for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
              tile.data[i][j] = fill;
          }
      }
      return tile;
  }

  public static Tile from1D(int[] data) {
      int size = (int)Math.sqrt(data.length);
      Tile tile = new Tile(size);
      for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
              tile.data[i][j] = data[i * size + j];
          }
      }
      return tile;
  }

  public int[] to1D() {
      int[] data = new int[size * size];
      for (int i = 0; i < size; i++) {
          for (int j = 0; j < size; j++) {
              data[i * size + j] = this.data[i][j];
          }
      }
      return data;
  }
}
