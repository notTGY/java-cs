import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Application {
    static int defaultSelectedColor = 3;
    static int cellSize = 16;
    static int MW = 10;
    static int MH = 9;

    public static void main(String[] args) {
        Frame frame = new Frame("Grid Canvas");
        frame.setSize(400, 400);

        final int[] currentTileIndex = new int[1];
        currentTileIndex[0] = 0;

        MapStorage ms = new MapStorage();

        TileStorage ts = new TileStorage();


// 0 - transparent, 1 - black, 2 - gray, 3 - white
        Tile[] tilesForTile = {
            Tile.generateTile(cellSize, 0),
            Tile.generateTile(cellSize, 1),
            Tile.generateTile(cellSize, 2),
            Tile.generateTile(cellSize, 3),
        };

        CanvasElement gameCanvas = new CanvasElement(
            MW,
            MH,
            cellSize,
            ts.tiles,
            ms.grid
        );

        gameCanvas.addGridChangeListener(new GridChangeListener() {
            @Override
            public void gridChanged(int[][] newGrid) {
                // System.out.println("Grid changed!");
                // You can also update other components that depend on the grid here
            }
        });

        CanvasElement tileCanvas = new CanvasElement(
            cellSize,
            cellSize,
            cellSize,
            tilesForTile,
            transpose(ts.tiles[currentTileIndex[0]]).data
        ) {
            @Override
            public void drawTile(Graphics2D graphics, int x, int y, Tile tile, int cellSize) {
                int color = tile.data[0][0];
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
                graphics.fillRect(x*cellSize, y*cellSize, cellSize, cellSize);
            }
        };

        tileCanvas.addGridChangeListener(new GridChangeListener() {
            @Override
            public void gridChanged(int[][] newTile) {
                ts.set(currentTileIndex[0], transpose(Tile.fromData(newTile)));
            }
        });

        Panel canvasPanel = new Panel(new CardLayout());
        canvasPanel.add(gameCanvas, "game");
        canvasPanel.add(tileCanvas, "tile");

        frame.add(canvasPanel, BorderLayout.CENTER);
        
        // Add a panel to hold the switch buttons
        Panel switchPanel = new Panel();
        switchPanel.setLayout(new FlowLayout());

        Button gameButton = new Button("Game");
        Button tileButton = new Button("Tile");

        final Choice tileIndexSelector = new Choice();
        for (int i = 0; i < ts.tiles.length; i++) {
            tileIndexSelector.add(String.valueOf(i));
        }

        tileIndexSelector.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int selectedIndex = tileIndexSelector.getSelectedIndex();
                currentTileIndex[0] = selectedIndex;
                tileCanvas.setGrid(transpose(ts.tiles[currentTileIndex[0]]).data);
            }
        });

        switchPanel.add(gameButton);
        switchPanel.add(tileButton);
        switchPanel.add(new Label("Tile Index:"));
        switchPanel.add(tileIndexSelector);


        frame.add(switchPanel, BorderLayout.NORTH);

        gameButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) canvasPanel.getLayout();
            cl.show(canvasPanel, "game");
            gameCanvas.fullRepaint();
        });

        tileButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) canvasPanel.getLayout();
            cl.show(canvasPanel, "tile");
            tileCanvas.fullRepaint();
        });

        // Add sidebar with tile selector
        Panel sidebarPanel = new Panel();
        sidebarPanel.setLayout(new BorderLayout());

        List<Integer> tileIndices = new ArrayList<>();
        for (int i = 0; i < ts.tiles.length; i++) {
            tileIndices.add(i);
        }

        final Choice colorSelector = new Choice();
        for (Integer index : tileIndices) {
            colorSelector.add(String.valueOf(index));
        }

        colorSelector.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int selectedIndex = colorSelector.getSelectedIndex();
                gameCanvas.setCurrentColorIndex(selectedIndex);
                gameCanvas.fullRepaint();
                tileCanvas.setCurrentColorIndex(selectedIndex);
                tileCanvas.fullRepaint();
            }
        });
        colorSelector.select(defaultSelectedColor);
        Panel colorSelectorPanel = new Panel();
        colorSelectorPanel.setLayout(new FlowLayout());

        Label colorLabel = new Label("Color:");
        colorSelectorPanel.add(colorLabel);
        colorSelectorPanel.add(colorSelector);

        sidebarPanel.add(colorSelectorPanel, BorderLayout.NORTH);

        frame.add(sidebarPanel, BorderLayout.EAST);

        Button saveButton = new Button("Save");
        saveButton.addActionListener(e -> {
            ts.saveAll();
            ms.saveAll();
        });
        frame.add(saveButton, BorderLayout.SOUTH);

        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    private static Tile transpose(Tile tile) {
        Tile transposed = new Tile(tile.size);
        for (int i = 0; i < tile.size; i++) {
            for (int j = 0; j < tile.size; j++) {
                transposed.data[j][i] = tile.data[i][j];
            }
        }
        return transposed;
    }
}


interface GridChangeListener {
    void gridChanged(int[][] newGrid);
}