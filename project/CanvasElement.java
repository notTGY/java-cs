import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class CanvasElement extends Canvas {


  public int[][] grid;
  private BufferedImage image;
  private Graphics2D graphics;
  private int[] hoverX = new int[]{-1};
  private int[] hoverY = new int[]{-1};

//   private int MW;
//   private int MH;

  private Tile[] tiles;

  private int cellSize;

  private List<GridChangeListener> listeners = new ArrayList<>();

  private int currentColorIndex = Application.defaultSelectedColor;

  public void setCurrentColorIndex(int index) {
      currentColorIndex = index;
  }

  public void addGridChangeListener(GridChangeListener listener) {
      listeners.add(listener);
  }

  public void removeGridChangeListener(GridChangeListener listener) {
      listeners.remove(listener);
  }

  private void fireGridChangedEvent(int[][] newGrid) {
      for (GridChangeListener listener : listeners) {
          listener.gridChanged(newGrid);
      }
  }

  public void fullRepaint() {
      updateGraphics(graphics, grid, tiles, cellSize, hoverX, hoverY);
      repaint();
  }

  public void setGrid(int[][] newGrid) {
      this.grid = newGrid.clone();
      fullRepaint();
  }

  public CanvasElement(int MW, int MH, int cellSize, Tile[] tiles, int[][] _grid) {
    //   this.MW = MW;
    //   this.MH = MH;
      this.tiles = tiles;
      this.cellSize = cellSize;
      this.grid = _grid.clone();
      this.image = new BufferedImage(MW * cellSize, MH * cellSize, BufferedImage.TYPE_INT_ARGB);
      this.graphics = image.createGraphics();

      setSize(MW * cellSize, MH * cellSize);
      updateGraphics(graphics, grid, tiles, cellSize, hoverX, hoverY);
      repaint();

      addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
              int x = e.getX() / cellSize;
              int y = e.getY() / cellSize;
              grid[x][y] = currentColorIndex;
              fireGridChangedEvent(grid);
              updateGraphics(graphics, grid, tiles, cellSize, hoverX, hoverY);
              repaint();
          }
      });

      addMouseMotionListener(new MouseAdapter() {
          @Override
          public void mouseMoved(MouseEvent e) {
              int x = e.getX() / cellSize;
              int y = e.getY() / cellSize;
              if ((x!= hoverX[0] || y!= hoverY[0]) && (x < MW) && (y < MH)) {
                  hoverX[0] = x;
                  hoverY[0] = y;
                  updateGraphics(graphics, grid, tiles, cellSize, hoverX, hoverY);
                  repaint();
              }
          }
      });
  }

  @Override
  public void paint(Graphics g) {
      g.drawImage(image, 0, 0, null);
  }

  private void updateGraphics(Graphics2D graphics, int[][] grid, Tile[] tiles, int cellSize, int[] hoverX, int[] hoverY) {
    graphics.setBackground(new Color(255, 0, 0)); // RGB value for red
    graphics.clearRect(0, 0, grid.length * cellSize, grid[0].length * cellSize);    
      for (int i = 0; i < grid.length; i++) {
          for (int j = 0; j < grid[0].length; j++) {
              int index = grid[i][j];
              drawTile(graphics, i, j, tiles[index], cellSize);
          }
      }

      if (hoverX[0]!= -1 && hoverY[0]!= -1) {
          graphics.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
          graphics.fillRect(hoverX[0] * cellSize, hoverY[0] * cellSize, cellSize, cellSize);
      }
  }

  private static void drawPixel(Graphics2D graphics, int x, int y, int color) {
      switch (color) {
          case 0:
              graphics.setColor(new Color(0f, 0f, 0f, 0f));
              break;
          case 1:
              graphics.setColor(new Color(0f, 0f, 0f, 1f));
              break;
          case 2:
              graphics.setColor(new Color(0.66f, 0.66f, 0.66f, 1f));
              break;
          case 3:
              graphics.setColor(new Color(1f, 1f, 1f, 1f));
              break;
          default:
              throw new IllegalArgumentException("Invalid tile color: " + color);
      }
      graphics.fillRect(x, y, 1, 1);
  }

  public void drawTile(Graphics2D graphics, int x, int y, Tile tile, int cellSize) {
      for (int i = 0; i < tile.size; i++) {
          for (int j = 0; j < tile.size; j++) {
              int color = tile.data[i][j];
              drawPixel(graphics, (x * cellSize) + j, (y * cellSize) + i, color);
          }
      }
  }
}
