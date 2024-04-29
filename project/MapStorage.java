import java.io.File;

public class MapStorage {
  String path = "./map.json";

  public int[][] grid;

  public MapStorage() {
    // read ./map.json
    File f = new File(path);
    if (!f.exists()) {
      fallback();
      return;
    }
    int[] array = FileUtils.read1DFile(path);
    

    int MW = Application.MW;
    int MH = Application.MH;
    grid = new int[MW][MH];

    for (int i = 0; i < MW; i++) {
      for (int j = 0; j < MH; j++) {
        grid[i][j] = array[i + j * MW];
      }
    }

    // fallback();
  }

  private void fallback() {
    int MW = Application.MW;
    int MH = Application.MH;
    int[][] _grid = new int[MW][MH];
    for (int i = 0; i < MW; i++) {
      for (int j = 0; j < MH; j++) {
        _grid[i][j] = 1;
      }
    }

    grid = _grid;
  }

  public void set(int[][] newGrid) {
    int MW = Application.MW;
    int MH = Application.MH;
    int[] array = new int[MW * MH];

    for (int i = 0; i < MW; i++) {
      for (int j = 0; j < MH; j++) {
        array[i + j * MW] = newGrid[i][j];
      }
    }
  }

  public void saveAll() {
    int MW = Application.MW;
    int MH = Application.MH;
    int[] array = new int[MW * MH];
    for (int i = 0; i < MW; i++) {
      for (int j = 0; j < MH; j++) {
        array[i + j * MW] = grid[i][j];
      }
    }
    FileUtils.write1DFile(array, path);
  }
}
